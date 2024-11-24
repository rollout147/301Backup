package com.oracle.oBootMbtPractice2.dao;

import java.util.List;

import com.oracle.oBootMbtPractice2.model.Book;

public interface BookDao {

	int				totalBook();
	List<Book>		listBook(Book book);
	Book			detailBook(int bookno);
	int 			updateBook(Book book);
	
	List<Book> 		listBookManager();
	int 			insertBook(Book book);
	
	int 			deleteBook(int bookno);
	
}
