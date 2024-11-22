package com.oracle.oBootMybatis01.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Dept {
	private int		deptno;
	private	String	dname;
	private String	loc;
	
	// 프로젝트시 DTO파일을 여러개 만드는 것보다,
	// 아래에 @Transient 어노테이션을 걸고 더 추가하는 것이 유지보수에 좋음
	
 }
