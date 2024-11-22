package com.oracle.oBootMybatis01.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.DeptDao;
import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.dao.Member1Dao;
import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.model.Member1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
	
	private final EmpDao ed;
	private final DeptDao dd;
	private final Member1Dao md;
		// 서로 다른 Interface(DAO)를 받은 것이기 때문에 두 개 이상 가능 

	
// << 1.회원 목록 (기본 CRUD) >>

	
	// 1.메인 페이지 
	@Override
	public int totalEmp() {
		System.out.println("EmpServiceImpl totalEmp Start...");
		
		int totEmpCnt = ed.totalEmp();
		System.out.println("EmpServiceImpl totalEmp totEmpCnt->"+totEmpCnt);
		
		return totEmpCnt;
	}

	// paging 작업 끝나고 목록작업 시작
	// listEmpAjaxForm(ajax JSP 연동)
	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listManager Start...");
		
		empList = ed.listEmp(emp);
		System.out.println("EmpServiceImpl listEmp empList.size()->"+empList.size()); 
		
		return empList;
	}

	// 3. 페이지 조회
	// 현장 HW 2-3
		//1. EmpService안에 detailEmp method 선언
		//	1) parameter : empno
		//	2) Return      Emp
	@Override
	public Emp detailEmp(int empno) {
		System.out.println("EmpServiceImpl detailEmp start...");
		
		Emp emp = null;
		emp = ed.detailEmp(empno);
		System.out.println("EmpServiceImpl detailEmp emp->"+emp);
		
		return emp;

	}

	// (0809)현장 HW 3-3
	//  1. EmpService안에 updateEmp method 선언
	//  1) parameter : Emp
	//  2) Return      updateCount (int)

	@Override
	public int updateEmp(Emp emp) {
		System.out.println("EmpServiceImpl Update...");
		
		int updateCount = 0;
		updateCount = ed.updateEmp(emp);
		System.out.println("EmpServiceImpl updateEmp updateCount->"+updateCount);
		
		return updateCount;
	}
	
	// (0812)현장 1-3
	@Override
	public List<Emp> listManager() {
		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listManager start...");
		
		empList = ed.listManager();
		System.out.println("EmpServiceImpl listManager empList.size()->"+empList.size());
		
		return empList;
	}
	
	// (0812)현장 HW 2-1
	//  +++ Ajax Form Test의 EmpRestController에서 sendVO3와도 연결됨
	@Override
	public List<Dept>deptSelect() {
		List<Dept> deptList = null;
		System.out.println("EmpServiceImpl deptSelect Start...");
		
		deptList = dd.deptSelect();
		System.out.println("EmpServiceImpl deptSelect deptList.size()->"+deptList.size());
		
		return deptList;
	}

	// (0812)현장 3-2
	// Service, Dao , Mapper명[insertEmp] 까지 -> insert
	@Override
	public int insertEmp(Emp emp) {		
		int result = 0;
		System.out.println("EmpServiceImpl Insert Start...");
		
		result = ed.insertEmp(emp);
		System.out.println("EmpServiceImpl Insert result->"+result);
		return result;
	}
	
	//  7. 데이터 삭제
	// (0812)현장 4-2
	@Override
	public int deleteEmp(int empno) {
		int result = 0;
		System.out.println("EmpServiceImpl delete Start...");
		
		result = ed.deleteEmp(empno);
		
		return result;
	}

	// Validate 방법
	
	// CRUD 이름+업무 조회
	@Override
	public int condEmpTotal(Emp emp) {
		System.out.println("EmpServiceImpl total Start...");
		
		int totEmpCnt = ed.condEmpTotal(emp);
		System.out.println("EmpServiceImpl totalEmp totEmpCnt->"+totEmpCnt);
		
		return totEmpCnt;
	}
	
	
	// CRUD 이름+업무 조회
	
	// 현장 HW
	@Override
	public List<Emp> listSearchEmp(Emp emp) {
		List<Emp> empSearchList = null;
		System.out.println("EmpServiceImpl listEmp Start...");
		
		empSearchList = ed.empSearchList3(emp);
		System.out.println("EmpServiceImpl listSearchEmp empSearchList->"+empSearchList.size());
		
		return empSearchList;
	}

	
//<< 2.직원부서조회=> Emp TBL과 Dept TBL을 Join >>
	
	// (0813) 현장 HW 1-3
	@Override
	public List<EmpDept> listEmpDept() {
		List<EmpDept> empDeptList = null;
		System.out.println("EmpServiceImpl listEmpDept Start...");
		
		empDeptList = ed.listEmpDept();
		System.out.println("EmpServiceImpl listEmpDept empDeptList->"+empDeptList.size());
		
		return empDeptList;
	}

	// PL/SQL 연동 후 부서 입력
	@Override
	public void insertDept(DeptVO deptVO) {
		System.out.println("EmpServiceImpl insertDept Start...");
		dd.insertDept(deptVO);
	}

	
	// PL/SQL 연동 후 부서 조회
	@Override
	public void selListDept(HashMap<String, Object> map) {
		System.out.println("EmpServiceImpl selListDept Start...");
		dd.selListDept(map);
	}

// -------------------------------------
	// (0814) 현장 HW 1-2
	// 2. interCeptor Number2
	@Override
	public int memCount(String id) {
		System.out.println("EmpServiceImpl memCount id->"+id);
		
		return md.memCount(id);
	}
	
	// (0814) HW
	// 4. interCeptor 진행 Test
	@Override
	public List<Member1> listMem(Member1 member1) {
		List<Member1> listMember1 = null;
		System.out.println("EmpServiceImpl listMem Start...");
		
		listMember1 = md.listMem(member1);
		System.out.println("EmpServiceImpl listMem listMember1->"+listMember1);
		
		return listMember1;
	}
	
	
	// (0819) 현장 HW 1-2. Ajax Form Test- getDeptName
	@Override
	public String deptName(int deptno) {
		System.out.println("EmpServiceImpl deptName Start...");		
		// 이 다음 LogAop.class로 넘어감
		
		return ed.deptName(deptno);
	}

	// 9. Transaction Test
	@Override
	public int transactionInsertUpdate() {
		System.out.println("EmpServiceImpl transactionInsertUpdate Start...");
		
		// return md.transactionInsertUpdate();	//transaction X
		return md.transactionInsertUpdate3();	//transaction OK
	}
	

}
