package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;

public interface EmpDao {

	int				totalEmp();

	List<Emp> 		listEmp(Emp emp);
	
	// (0809)현장 HW 2-4
	Emp 			detailEmp(int empno);
	// (0809)현장 HW 3-4
	int 			updateEmp(Emp emp);
	// (0812)현장 1-4
	List<Emp> 		listManager();
	
	// (0812)현장 3-3
	int 			insertEmp(Emp emp);

	// (0812)현장 4-3
	int 			deleteEmp(int empno);

	// Validate 방법
	int 			condEmpTotal(Emp emp);

	// 현장 HW
	List<Emp> 		empSearchList3(Emp emp);


	// (0813) 현장 HW 1-4
	List<EmpDept> 	listEmpDept();

	// (0819) 현장 HW 1-1. Ajax Form Test- getDeptName
	String deptName(int deptno);	
	
	
}
