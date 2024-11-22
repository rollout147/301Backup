package com.oracle.oBootMybatis01.dao;

import java.util.HashMap;
import java.util.List;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;

public interface DeptDao {

	// Emp TBL과 Dept TBL을 Join
	List<Dept> 		deptSelect();

	// PL/SQL 연동
	void 			insertDept(DeptVO deptVO);

	void 			selListDept(HashMap<String, Object> map);



}
