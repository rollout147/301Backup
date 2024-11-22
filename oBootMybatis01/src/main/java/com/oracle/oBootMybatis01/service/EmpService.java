package com.oracle.oBootMybatis01.service;

import java.util.HashMap;
import java.util.List;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.model.Member1;

public interface EmpService {
	int				totalEmp();
	List<Emp> 		listEmp(Emp emp);
	
	// (0809)현장HW 2-2
	Emp 			detailEmp(int empno);
	// (0809)현장HW 3-2
	int 			updateEmp(Emp emp);
	// (0812)현장 1-2
	List<Emp> 		listManager();
	
	List<Dept>		deptSelect();
	// (0812)현장 3-1
	int 			insertEmp(Emp emp);
	// (0812)현장 4-1
	int 			deleteEmp(int empno);
	
	
	// Validate 방법
	int 			condEmpTotal(Emp emp);
	
	// 현장 HW
	List<Emp>		listSearchEmp(Emp emp);
	
	// (0813) 현장 HW 1-2. Emp TBL과 Dept TBL을 Join
	List<EmpDept> 	listEmpDept();
	// PL/SQL 연동
	void 			insertDept(DeptVO deptVO);
	void 			selListDept(HashMap<String, Object> map);
	// 2. interCeptor Number2
	int 			memCount(String id);
	// (0814) HW 4. interCeptor 진행 Test
	List<Member1> 	listMem(Member1 member1);
	
	// (0819) 현장 HW 1-1. Ajax Form Test- getDeptName
	String 			deptName(int deptno);
	// 9. Transaction Test
	int 			transactionInsertUpdate();
}
