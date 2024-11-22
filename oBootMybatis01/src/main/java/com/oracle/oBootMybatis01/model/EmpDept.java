package com.oracle.oBootMybatis01.model;

import lombok.Data;

// Emp TBL과 Dept TBL을 Join하는 목적의 DTO
@Data
public class EmpDept {
	// Emp 용
	private int		empno;
	private String	ename;
	private String	job;
	private int		mgr;
	private String	hiredate;
	private int		sal;
	private int		comm;
	private int		deptno;
	
	// Dept용 (많다는 가정)
	private String dname;
	private String loc;

}
