package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.model.Member1;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmpController {
	
	private final EmpService es;
	private final JavaMailSender mailSender;
	
// << 1.회원 목록 (기본 CRUD) >>
	
	// 1.메인 페이지( 화면 + list 조회)
	@RequestMapping(value = "listEmpStart")
	public String listEmpStart(Emp emp, Model model) {
		System.out.println("EmpController Start listEmp...");
		
		int totalEmp = es.totalEmp();
		System.out.println("EmpController es.totalEmp->"+totalEmp);
		
		String currentPage = "1";
		System.out.println("EmpController currentPage->"+currentPage);
			// 여기서 Paging.class로 이동하여 작업
		
		// Paging 작업한거 가져오기
		Paging	page = new Paging(totalEmp, currentPage);
		System.out.println("EmpController listEmpStart page->"+page);
		// Parameter emp ==> Page만 추가 Setting
		emp.setStart(page.getStart());	// 시작시 1
		System.out.println("EmpController listEmpStart emp setStart->"+emp);		
		emp.setEnd(page.getEnd());	// 시작시 10
		System.out.println("EmpController listEmpStart emp setEnd->"+emp);
			// paging 작업 끝나고 다시 Service로 이동
		
		// 목록작업 끝내고 가져옴
		List<Emp> listEmp = es.listEmp(emp); 
		System.out.println("EmpController list listEmp.size()->"+listEmp.size());
		
		model.addAttribute("totalEmp", totalEmp);
		System.out.println("EmpController totalEmp model->"+totalEmp);
		model.addAttribute("listEmp", listEmp);
		System.out.println("EmpController listEmp model->"+listEmp);
		model.addAttribute("page", page);	
		System.out.println("EmpController page model->"+page);

		
		return "list";
		
	}
	
	// (0809)현장HW 1
	// 2. 페이지 번호 이동 
	@RequestMapping(value = "listEmp")
	public String listEmp(Emp emp, Model model) {
		System.out.println("EmpController Start listEmp...");
		
		// 20
		int totalEmp = es.totalEmp();		
		
		// Paging 작업
		Paging	page = new Paging(totalEmp, emp.getCurrentPage());
		
		// Parameter emp ==> Page만 추가 Setting
		emp.setStart(page.getStart());	// 시작시 1
		emp.setEnd(page.getEnd());	// 시작시 10
		
		List<Emp> listEmp = es.listEmp(emp); 
		System.out.println("EmpController list listEmp.size()->"+listEmp.size());
		
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listEmp);
		model.addAttribute("page", page);		
		
		return "list";
	}
	
	// (0809)현장HW 2-1
	// 3. 페이지 조회
	@GetMapping(value = "detailEmp")
	public String detailEmp(Emp emp1, Model model) {
		System.out.println("EmpController Start detailEmp...");
		System.out.println("emp->"+emp1);
		
		Emp emp = es.detailEmp(emp1.getEmpno());
		
		model.addAttribute("emp", emp);
		
		return "detailEmp";
	}

	// 4. 게시판 내용 수정
	@GetMapping(value = "updateFormEmp")
	public String updateFormEmp(Emp emp1, Model model) {
		System.out.println("EmpController Start updateFormEmp...");
		
		Emp emp = es.detailEmp(emp1.getEmpno());
		System.out.println("EmpController updateFormEmp emp->"+emp);
		
		// 문제 
		// 1. DTO  String hiredate
		// 2.View : 단순조회 OK ,JSP에서 input type="date" 문제 발생
		// 3.해결책  : 년월일만 짤라 넣어 주어야 함
		String hiredate = "";
		if (emp.getHiredate() != null) {
			hiredate = emp.getHiredate().substring(0,10);
			emp.setHiredate(hiredate);
		}
		System.out.println("hiredate->"+hiredate);
		
		model.addAttribute("emp", emp);
		
		return "updateFormEmp";
	}

	// (0809)현장HW 3-1
	// 5. 게시판 내용 수정
	//  1. EmpService안에 updateEmp method 선언
	//  1) parameter : Emp
	//  2) Return      updateCount (int)
	//
	//2. EmpDao updateEmp method 선언
	////                          mapper ID   ,    Parameter
	//updateCount = session.update("tkEmpUpdate",   emp);
	@PostMapping(value = "updateEmp")
	public String updateEmp(Emp emp, Model model) {
		log.info("updateEmp start...");
		System.out.println("EmpController updateEmp emp->"+emp);
		
		int updateCount = es.updateEmp(emp);
		System.out.println("EmpController es.updateEmp updateCount->"+updateCount);
		
		model.addAttribute("upCnt",updateCount);	// Test Controller간 Data 전달
		model.addAttribute("kk3","Message Test");	// Test Controller간 Data 전달
		
		return "forward:listEmp";
		// return "redirect:listEmp";
	
	}
	
	// 6-1. writeFormEmp 눌렀을 때, 관리자사번,부서코드 선택창 나오게 하기
	@RequestMapping(value = "writeFormEmp")
	public String writeFormEmp(Model model) {
		System.out.println("empController writeFormEmp Start...");
		
		// 관리자사번만 Get
		List<Emp> empList = es.listManager();
		
		// (0812) 현장 HW 1-1
		// 1. service -> listManager
		// 2. Dao     -> listManager
		// 3. mapper  -> tkSelectManager
		System.out.println("EmpController writeForm empList.size()->"+empList.size());
		
		model.addAttribute("empMngList",empList);	// emp Manager List
		// 부서(코드,부서명)
		List<Dept> deptList = es.deptSelect(); 
		model.addAttribute("deptList", deptList); // dept
		System.out.println("EmpController writeForm deptList.size->"+deptList.size());
		
		return "writeFormEmp";
	}

	// 6-2. writeFormEmp 누른 후 수정완료 로직
	@PostMapping(value = "writeEmp")
	public String writeEmp(Emp emp, Model model) {
		System.out.println("EmpController writeEmp Start...");
		
		// Service, Dao , Mapper명[insertEmp] 까지 -> insert
		int insertResult = es.insertEmp(emp);
		
		if (insertResult > 0)
			return "redirect:listEmp";
			// insertResult 1일때 입력 성공
		else {
			model.addAttribute("msg", "입력 실패. 확인해 보세요.");
			
			return "forward:writeFormEmp";
			// 입력 실패시 위의 writeFormEmp로 돌아가되 msg의 값을 가져가므로,
			// writeFormEmp의 <c:if test="${msg!=null}">${msg}</c:if> 값이 출력되어
			// 메시지가 나타남
		}
	}
	
	// 6-3. Submit(확인)버튼 눌렀을 때 이동될 창 만들기
	@GetMapping(value = "confirm")
	public String confirm(Emp emp1, Model model) {
		Emp emp = es.detailEmp(emp1.getEmpno());
		model.addAttribute("empno", emp1.getEmpno());
		
		if (emp != null) {
			System.out.println("EmpController confirm 중복된 사번...");
			model.addAttribute("msg", "중복된 사번입니다");
			//return "forward:writeFormEmp";
			
		} else {
			System.out.println("EmpController confirm 사용 가능한 사번...");
			model.addAttribute("msg", "사용 가능한 사번입니다");
			//return "forward:writeFormEmp";
		}
		return "forward:writeFormEmp";
			// return을 최소화하여 효율성 높이기
	}
	
	//  7. 데이터 삭제
	// Controller -->  deleteEmp    1.parameter : empno
		// name -> Service, dao , mapper
		// return -> listEmp
	@RequestMapping(value = "deleteEmp")
	public String deleteEmp(Emp emp, Model model) {
		System.out.println("EmpController delete Start...");
		
		int result = es.deleteEmp(emp.getEmpno());
		System.out.println("EmpController es.deleteEmp result->"+result);
		
		return "redirect:listEmp";
	}
	
	
	// Validate 방법
	// writeFormEmp3와 연결
	@RequestMapping(value = "writeFormEmp3")
	public String writeFormEmp3(Model model) {
		System.out.println("empController writeFormEmp3 Start...");
		
		// 관리자사번만 Get
		List<Emp> empList = es.listManager();
		System.out.println("EmpController writeForm3 empList.size()->"+empList.size());
		
		model.addAttribute("empMngList",empList);	// emp Manager List
		// 부서(코드,부서명)
		List<Dept> deptList = es.deptSelect(); 
		model.addAttribute("deptList", deptList); // dept
		System.out.println("EmpController writeForm3 deptList.size->"+deptList.size());
		
		return "writeFormEmp3";
	}
	// Validation시 참조
	@PostMapping(value = "writeEmp3")
	public String writeEmp3(@ModelAttribute("emp") @Valid Emp emp
			// <form:form action="writeEmp3" modelAttribute="emp"> 와 이름 같게하기
							, BindingResult result
							, Model model) {
		System.out.println("EmpController writeEmp3 Start...");
		
		// Validation 오류시 Result
		if (result.hasErrors()) {
			System.out.println("EmpController writeEmp3 hasErrors...");
			model.addAttribute("msg", "BindingResult 입력 실패. 확인해 보세요.");
			return "forward:writeFormEmp3";
		}
		
		int insertResult = es.insertEmp(emp);
		
		if (insertResult > 0)
			return "redirect:listEmp";
			// insertResult 1일때 입력 성공
		else {
			model.addAttribute("msg", "입력 실패. 확인해 보세요.");
			
			return "forward:writeFormEmp3";
			// 입력 실패시 위의 writeFormEmp3로 돌아가되 msg의 값을 가져가므로,
			// writeFormEmp3의 <c:if test="${msg!=null}">${msg}</c:if> 값이 출력되어
			// 메시지가 나타남
		}
	}
	


	// CRUD 이름+업무 조회
	@RequestMapping(value = "listSearch3")
	public String listSearch3(Emp emp, Model model) {
		System.out.println("EmpController listEmp Start...");
		System.out.println("EmpController listSearch3 emp->"+emp);
		
		// Emp 전체 Count
		int totalEmp = es.condEmpTotal(emp);
		System.out.println("EmpController listSearch3 totalEmp->"+totalEmp);
		 
		// Paging 작업
		Paging page = new Paging(totalEmp, emp.getCurrentPage());
		
		// Parameter emp ==> Page만 추가 Setting
		emp.setStart(page.getStart());	// 시작시 1
		emp.setEnd(page.getEnd());		// 시작시 10
		System.out.println("EmpController listSearch3 page->"+page);
		
		// 현장 HW
		List<Emp> listSearchEmp = es.listSearchEmp(emp);
		// 1. DAO  ed.empSearchList3(emp);
        // 2. Mapper selectList("tkEmpSearchList3", emp);
		
		System.out.println("EmpController listSearch3 listSearchEmp.size()->"+listSearchEmp.size());
		
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp" , listSearchEmp);
		model.addAttribute("page"    , page);
		
		return "list";
	}
	
	// 2.직원부서조회
	// Emp TBL과 Dept TBL을 Join
	
	// (0813) 현장 HW 1-1
	// Service ,DAO -> listEmpDept
	//  Mapper만 ->EmpDept.xml(tkListEmpDept)
	
	@GetMapping(value = "listEmpDept")
	public String listEmpDept(Model model) {
		System.out.println("EmpController listEmpDept Start...");
		
		
		List<EmpDept> listEmpDept = es.listEmpDept();
		model.addAttribute("listEmpDept", listEmpDept);
		
		return "listEmpDept";
	}

	// Mail 보내기
	@RequestMapping(value="mailTransport")
	public String mailTransport(HttpServletRequest request, Model model) {
		System.out.println("mailSending...");
		
		String tomail = "rollout147@naver.com";		// 받는 사람 이메일
		System.out.println(tomail);
		
		String setfrom = "rollout147@gmail.com";	// 보내는 사람 이메일
		String title = "mailTransport 입니다";		// 메일 제목
		
		try {
			// Mime: 전자우편 Internet 표준 Format
			MimeMessage message = mailSender.createMimeMessage();
			
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom);		// 보내는사람을 생략하면 정상작동 안함
			messageHelper.setTo(tomail);		// 받는사람 이메일 주소
			messageHelper.setSubject(title);	// 메일 제목은 생략이 가능함
			String tempPassword = (int) (Math.random() * 999999) + 1 + "";
			messageHelper.setText("임시 비밀번호입니다 : " + tempPassword); // 메일내용
			System.out.println("임시 비밀번호입니다 : " + tempPassword);
			
			mailSender.send(message);
			model.addAttribute("check",1);	//정상 전달
			
			// DB 로직
			
		} catch (Exception e) {
			System.out.println("mailTransport e.getMessage()->"+e.getMessage());
			
			model.addAttribute("check", 2);	// 메일 전달 실패
		}		
		return "mailResult";
	}
	
	// 3. PL/SQL 연동 후 부서 입력
	// 1. Procedure Test 입력화면
	@RequestMapping(value = "writeDeptIn")
	public String writeDeptIn(Model model) {
		System.out.println("writeDeptIn Start...");
	
		return "writeDept3";
	}
	
	// 2. Procedure를 통해 Dept 입력 후 VO 전달
	@RequestMapping(value = "writeDept")
	public String writeDept(DeptVO deptVO, Model model) {
				// deptVO는 call by Reference 덕분에 값을 받을 수 있음
		es.insertDept(deptVO);
		
		if (deptVO == null) {
			System.out.println("deptVO NULL");
		
		} else {
			System.out.println("deptVO.getOdeptno()->"+deptVO.getOdeptno());
			System.out.println("deptVO.getOdname()->"+deptVO.getOdname());
			System.out.println("deptVO.getOloc()->"+deptVO.getOloc());
			
			model.addAttribute("msg", "정상 입력 되었습니다");
			model.addAttribute("dept", deptVO);
		}
		return "writeDept3";
	}

	// 4. PL/SQL 연동 후 부서 조회
	// DTO방식이 아닌, Map 방식 적용
	@GetMapping(value = "writeDeptCursor")
	public String writeDeptCursor(Model model) {
		System.out.println("EmpController writeDeptCursor Start...");
		
		// 부서범위 조회
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("sDeptno", 10);
		map.put("eDeptno", 55);
	
		es.selListDept(map);
					//DAO의 map이 이 map로 넘어감
		
		List<Dept> deptLists = (List<Dept>) map.get("dept");
			// Dept.xml의 #{dept 임
		for(Dept dept : deptLists) {
			System.out.println("dept.getDname()->"+dept.getDname());
			System.out.println("dept.getLoc()->"+dept.getLoc());
		}
		System.out.println("deptList Size->"+deptLists.size());
		
		model.addAttribute("deptList", deptLists);
		
		return "writeDeptCursor";	
	}
// ----------------------------------
  // 6. interCeptor(가로채기)
	// 1. ` 시작 화면
	@RequestMapping(value = "interCeptorForm")
	public String interCeptorForm() {
		System.out.println("interCeptorForm Start...");
		
		return "interCeptorForm";
	}
	// (0814) 현장 HW 1-1
	// 2. interCeptor Number2
	@RequestMapping(value = "interCeptor")
	public String interCeptor(Member1 member1, Model model) {
		System.out.println("EmpController interCeptor Test Start...");
		
		System.out.println("EmpController interCeptor id->"+member1.getId());
		
		// 존재하면 1, 존재하지 않으면 0
		int memCnt = es.memCount(member1.getId());
		System.out.println("EmpController interCeptor memCnt->"+memCnt);
	
		model.addAttribute("id", member1.getId());
		model.addAttribute("memCnt", memCnt);
		System.out.println("interCeptor Test End");
	
		return "interCeptor";
			// 의미 없는 더미 View Resolver
			// User 존재하면 User 이용 조회 Page
	}
	
	// 3. SampleInterceptor 내용을 받아 처리
	@RequestMapping(value = "doMemberWrite")
	public String doMemberWrite(Model model, HttpServletRequest request) {
		String ID = (String) request.getSession().getAttribute("ID");
		System.out.println("doMemberWrite부터 입력하세요.");
		
		model.addAttribute("id",ID);
		
		return "doMemberWrite";
	}
	
	// (0814) HW
	// Member1 List Get Service
	// Service, DAO --> listMem
	// Mapper --> listMember1
	// Member1 모든 Row Get
	// 4. interCeptor 진행 Test
	@RequestMapping(value = "doMemberList")
	public String doMemberList(Model model, HttpServletRequest request) {
		String ID = (String) request.getSession().getAttribute("ID");
		System.out.println("doMemberList Test Start ID->"+ID);
		
		Member1 member1 = null;
		// Member1 List Get Service
		List<Member1> listMem = es.listMem(member1);
		
		model.addAttribute("ID",ID);
		model.addAttribute("listMem",listMem);
		
		return "doMemberList";	// User 존재하면 User 이용 조회 Page

	}
	
// ----------------------------------
  // 8. Ajax Form Test 
	// ajaxForm Test 입력화면
	@RequestMapping(value = "ajaxForm")
	public String ajaxForm(Model model) {
		System.out.println("EmpController ajaxForm Start...");
		
		return "ajaxForm";
	}
	
	// 8-4. getDeptName
	// (0819) 현장 HW 1-1
	@ResponseBody
	// 객체를 돌려주기 위해
	@RequestMapping(value = "getDeptName")
	public String getDeptName(Dept dept, Model model) {
		System.out.println("EmpController getDeptName deptno->"+dept.getDeptno());
		
		String deptName = es.deptName(dept.getDeptno());
		System.out.println("EmpController getDeptName deptName->"+deptName);
		
		return deptName;
	}
	
	// 8-5. listEmpAjaxForm(ajax JSP 연동)
	@RequestMapping(value = "listEmpAjaxForm")
	public String listEmpAjaxForm(Model model) {
		Emp emp = new Emp();
		System.out.println("EmpController listEmpAjaxForm Start...");
		
		// Parameter emp ==> page만 추가 Setting
		emp.setStart(1);	// 시작시 1
		emp.setEnd(10);		// 시작시 10
		
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController listEmpAjaxForm listEmp.size()->"+listEmp.size());
		
		model.addAttribute("result", "kkk");
		model.addAttribute("listEmp", listEmp);
		
		return "listEmpAjaxForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "empSerializeWrite")
	public Map<String, Object> empSerializeWrite(@RequestBody @Valid Emp emp) {
		// Map 방식으로 해두면 유지보수가 편리해짐
		//jsp에서 stringify로 보내면 Controller에서 @RequestBody를 입력하여 풀어서 받아줘야 함
		System.out.println("EmpController empSerializeWrite Start...");
		System.out.println("EmpController empSerializeWrite emp->"+emp);
		
		int writeResult = 1;
		// int writeResult = kkk.writeEmp(emp);
		// String followingProStr = Interger.toString(followingPro);
		
		Map<String, Object> resultMap = new HashMap<>();
		System.out.println("EmpController empSerializeWrite writeResult->"+writeResult);
		
		resultMap.put("writeResult", writeResult);
		resultMap.put("anyResult", "anyR");
		
		return resultMap;
		
	}
	
	// 8-6. listEmpAjaxForm2(ajax JSP 객체리스트 Get)
	@RequestMapping(value = "listEmpAjaxForm2")
	public String listEmpAjaxForm2(Model model) {
		System.out.println("EmpController listEmpAjaxForm2 Start...");
	
		Emp emp = new Emp();		
		// Paramemter emp ==> Page만 추가 Setting
		emp.setStart(1);	// 시작시 1
		emp.setEnd(15);		// 시작시 15
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController listEmpAjaxForm2 listEmp.size()->"+listEmp.size());
		
		model.addAttribute("listEmp", listEmp);
		
		return "listEmpAjaxForm2";
	}
	
	// 8-7. listEmpAjaxForm3(ajax List를 Controller로 전송)
	// 1) empLISTTest 전송 버튼
	@RequestMapping(value = "listEmpAjaxForm3")
	public String listEmpAjaxForm3(Model model) {
		System.out.println("EmpController listEmpAjaxForm3 Start...");
		
		Emp emp = new Emp();		
		// Paramemter emp ==> Page만 추가 Setting
		emp.setStart(1);	// 시작시 1
		emp.setEnd(15);		// 시작시 15
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController listEmpAjaxForm3 listEmp.size()->"+listEmp.size());
		
		model.addAttribute("listEmp", listEmp);
		
		return "listEmpAjaxForm3";
	}
	
	// 2) empLIST 전송 버튼으로 업데이트
	@ResponseBody
	@RequestMapping(value = "empListUpdate")
	public Map<String, Object> empListUpdate(@RequestBody @Valid List<Emp> listEmp) {
		System.out.println("EmpController empListUpdate Start...");
		
		int updateResult = 1;
		for(Emp emp : listEmp) {
			System.out.println("EmpController empListUpdate emp->"+emp);
			// int writeResult = kkk.listUpdateEmp(emp);
		}
		
		
		Map<String, Object> resultMap = new HashMap<>();
		System.out.println("EmpController empListUpdate updateResult->"+updateResult);
		resultMap.put("updateResult", updateResult);
		
		return resultMap;
		
	}
	
  //---------------------------------- 
	// 9. Transaction Test
	@ResponseBody
	@RequestMapping(value = "transactionInsertUpdate")
	public String transactionInsertUpdate(Emp emp, Model model) {
		System.out.println("EmpController transactionInsertUpdate Start...");
		
		// member Insert 성공과 실패
		int returnMember = es.transactionInsertUpdate();
		System.out.println("EmpController transactionInsertUpdate returnMember->"+returnMember);
		
		String returnMemberString = String.valueOf(returnMember);
		
		return returnMemberString;
	
	}
	
}

