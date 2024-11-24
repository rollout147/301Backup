package com.oracle.oBootMbtPractice2.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Book {
	private int		bookno;
	
	@NotEmpty(message = "책 이름은 필수입니다")
	private String	bookname;
	private String	writer;
	private String	genre;
	private int		price;
	private int		artno;
	
	
	// 조회용
	private String	search;
	private String	keyword;
	private String	pageNum;
	private int		start;
	private int		end;
	
	// page 정보
	private String	currentPage;
}
