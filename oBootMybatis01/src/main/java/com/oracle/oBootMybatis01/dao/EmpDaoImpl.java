package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {
	// Mybatis DB 연동 
	private final SqlSession session;
	
// << 1.회원 목록 (기본 CRUD) >>	

	// 1.메인 페이지 
	@Override
	public int totalEmp() {
		int totEmpCount = 0;
		System.out.println("EmpDaoImpl Start totalEmp...");
		
		try {
			totEmpCount = session.selectOne("com.oracle.oBootMybatis01.EmpMapper.empTotal");
			System.out.println("EmpDaoImpl totalEmp totEmpCount->"+totEmpCount);
			
		} catch (Exception e) {
			System.out.println("EmpDaoImpl totalEmp e.getMessage()->"+e.getMessage());
		}

		return totEmpCount;
	}

	// listEmpAjaxForm(ajax JSP 연동)
	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listEmp Start...");
		
		try {
			// 					Map ID(Emp tag의 List) , parameter
			//				ID 이름은 유일해야함(unique)
			empList = session.selectList("tkEmpAllList", emp);
			System.out.println("EmpDaoImpl listEmp empList.size()->"+empList.size());
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listEmp e.getMessage()->"+e.getMessage());
		}
		return empList;
	}

	// 3. 페이지 조회
	// (0809)현장 HW 2-5
		//	2. EmpDao   detailEmp method 선언 
		//						mapper ID   ,    Parameter
		// emp = session.selectOne("tkEmpSelOne",    empno);

	@Override
	public Emp detailEmp(int empno) {
		System.out.println("EmpDaoImpl detailEmp Start...");
		
		Emp emp = new Emp();
		try {
			//					mapper ID   ,    Parameter		
			emp = session.selectOne("tkEmpSelOne",empno);
			System.out.println("EmpDaoImpl detail getEname->"+emp.getEname());
		
		} catch (Exception e) {
			System.out.println("EmpDaoImpl detail Exception->"+e.getMessage());
		}
		return emp;
	}

	// (0809)현장 HW 3-5
		//  2. EmpDao updateEmp method 선언
		//							mapper ID   ,    Parameter
		// updateCount = session.update("tkEmpUpdate",   emp);
		// model.addAttribute("upCnt",updateCount);	// Test Controller간 Data 전달됨
	@Override
	public int updateEmp(Emp emp) {
		System.out.println("EmpDaoImpl update Start...");
		
		int updateCount = 0;
		try {
			//						mapper ID   ,    Parameter		
			updateCount = session.update("tkEmpUpdate", emp);
			System.out.println("EmpDaoImpl updateEmp updateCount->"+updateCount);
		
		} catch (Exception e) {
			System.out.println("EmpDaoImpl updateEmp Exception->"+e.getMessage());
		}
		return updateCount;
	}
	
	// (0812)현장 1-5
	@Override
	public List<Emp> listManager() {
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listManager Start...");
		
		try {
			//	emp 관리자만 Select			Naming Rule
			empList = session.selectList("tkSelectManager");
			System.out.println("EmpDaoImpl listManager empList.size()->"+empList.size());
		
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listManager Exception->"+e.getMessage());
		}
		
		return empList;
	}

	// (0812)현장 3-4
	@Override
	public int insertEmp(Emp emp) {
		int result = 0;
		System.out.println("EmpDaoImpl insertEmp Start...");
		
		try {
			result = session.insert("insertEmp", emp);
			System.out.println("EmpDaoImpl insertEmp result->"+result);
			
		} catch (Exception e) {
			System.out.println("EmpDaoImpl insertEmp Exception->"+e.getMessage());
		}
		
		return result;
	}

	//  7. 데이터 삭제
	// (0812)현장 4-4
	@Override
	public int deleteEmp(int empno) {
		System.out.println("EmpDaoImpl delete Start...");
		
		int result = 0;
		try {
			result = session.delete("deleteEmp", empno);
			System.out.println("EmpDaoImpl delete emp.->"+result);
			
		} catch (Exception e) {
			System.out.println("EmpDaoImpl delete Exception->"+e.getMessage());
		}
		
		return result;
	}
	
	
	// Validate 방법
	
	// CRUD 이름+업무 조회 
	@Override
	public int condEmpTotal(Emp emp) {
		int totEmpCount = 0;
		System.out.println("EmpDaoImpl total Start...");
		
		try {
			totEmpCount = session.selectOne("condEmpTotal", emp);
			System.out.println("EmpDaoImpl totalEmp totEmpCount->"+totEmpCount);
		
		} catch (Exception e) {
			System.out.println("EmpDaoImpl totalEmp Exception->"+e.getMessage());
		}
		
		return totEmpCount;
	}

	// CRUD 이름+업무 조회
	// 현장 HW
	@Override
	public List<Emp> empSearchList3(Emp emp) {
		List<Emp> empSearchList3 = null;
		System.out.println("EmpDaoImpl empSearchList3 Start...");
		
		try {
			empSearchList3 = session.selectList("tkEmpSearchList3",emp);
			System.out.println("EmpDaoImpl listEmp empSearchList3->"+empSearchList3);
			
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listEmp Exception->"+e.getMessage());
		}
		
		return empSearchList3;
	}

// << 2.직원부서조회=> Emp TBL과 Dept TBL을 Join >>
	// (0813) 현장 HW 1-5
		
	@Override
	public List<EmpDept> listEmpDept() {
		System.out.println("EmpDaoImpl listEmpDept Start...");
		
		List<EmpDept> empDept = null;
		try {
			empDept = session.selectList("tkListEmpDept");
			System.out.println("EmpDaoImpl listEmpDept empDept.size()->"+empDept.size());
		
		} catch (Exception e) {
			System.out.println("EmpDaoImpl delete Exception->"+e.getMessage());
		}
		return empDept;
	}

	// (0819) 현장 HW 1-2. Ajax Form Test- getDeptName
	// mapper --> EmpDept(tkDeptName)
	@Override
	public String deptName(int deptno) {
		System.out.println("EmpDaoImpl deptName Start...");
		
		String resultStr = "";
		try {
			System.out.println("EmpDaoImpl deptName deptno->"+deptno);
			resultStr = session.selectOne("tkDeptName", deptno);
			System.out.println("EmpDaoImpl deptName resultStr->"+resultStr);
			
		} catch (Exception e) {
			System.out.println("EmpDaoImpl deptName Exception->"+e.getMessage());
		}
		
		return resultStr;
	}

}
