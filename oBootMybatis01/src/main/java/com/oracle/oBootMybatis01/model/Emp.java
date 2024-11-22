package com.oracle.oBootMybatis01.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Emp {
	private int		empno;
	
	@NotEmpty(message = "이름은 필수입니다 흑유흑유")
	private String	ename;
	private String	job;
	private int		mgr;
	private String	hiredate;
	private int		sal;
	private int		comm;
	private int		deptno;
	
	// 조회용
	private String	search;
	private String	keyword;
	private String	pageNum;
	private int		start;
	private int		end;
	
	// Page 정보
	private String	currentPage;
	
	// 프로젝트시 DTO파일을 여러개 만드는 것보다,
	// 아래에 @Transient 어노테이션을 걸고 더 추가하는 것이 유지보수에 좋음
	
}
