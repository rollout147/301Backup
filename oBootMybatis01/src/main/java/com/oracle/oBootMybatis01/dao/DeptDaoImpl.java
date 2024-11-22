package com.oracle.oBootMybatis01.dao;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeptDaoImpl implements DeptDao {
	// Mybatis 연동
	private final SqlSession session;
	
	// 1.회원 목록 (기본 CRUD)
	// (0812)현장 HW 2-2 
	@Override
	public List<Dept> deptSelect() {
		List<Dept> deptList = null;
		System.out.println("DeptDaoImpl deptSelect Start...");
		
		try {
									// ID: tkSelectDept
			deptList = session.selectList("tkSelectDept");
			
		} catch (Exception e) {
			System.out.println("DeptDaoImpl Exception Exception->"+e.getMessage());
		}
		return deptList;
	}

	// 2.직원부서조회
	// PL/SQL 연동 후 부서 입력
	@Override
	public void insertDept(DeptVO deptVO) {
		System.out.println("DeptDaoImpl insertDept Start...");
		
		session.selectOne("procDeptInsert", deptVO);
		
	}

	// PL/SQL 연동 후 부서 조회
	@Override
	public void selListDept(HashMap<String, Object> map) {
		System.out.println("DeptDaoImpl selListDept Start...");
		
		// ResultMap은 DB 컬럼명과 DTO의 변수 명이 다를 때 사용
		session.selectOne("procDeptList", map);
				//xml의 dept값이 map에 들어감
	}

}
