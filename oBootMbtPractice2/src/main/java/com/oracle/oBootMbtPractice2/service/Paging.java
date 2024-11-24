package com.oracle.oBootMbtPractice2.service;

import lombok.Data;

@Data
public class Paging {

	private int currentPage	= 1;
	private int rowPage		= 10;
	private int pageBlock	= 10;
	private int start;			private int end;
	private int startPage;		private int endPage;
	private int total;			private int totalPage;
	
	public Paging(int total, String currentPage1) {
		this.total = total;		//20
		System.out.println("Paging total Start...");
		
		if (currentPage1 != null) {
			this.currentPage = Integer.parseInt(currentPage1);	//2
			System.out.println("Paging if currentPage1->"+currentPage1);
		}
		//				1				10
		start	= (currentPage - 1) * rowPage + 1;	// 시작시 1		11
		end		= start + rowPage - 1;				// 시작시 10	20
		//									20		10
		totalPage	= (int)Math.ceil((double)total / rowPage); // 시작시 3
		//					1				1			
		startPage	= currentPage - (currentPage - 1) % pageBlock; // 시작시 1
		endPage		= startPage + pageBlock - 1;	//10
		
	// ex:		10		14	
		if (endPage > totalPage) {
			endPage = totalPage;
			System.out.println("Paging if endPage->"+endPage);
		}

	}

}
