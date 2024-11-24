package com.oracle.oBootMbtPractice2.service;

import java.util.List;

import com.oracle.oBootMbtPractice2.model.Art;
import com.oracle.oBootMbtPractice2.model.Book;

public interface BookService {
	int				totalBook();
	List<Book>		listBook(Book book);
	Book 			detailBook(int bookno);
	int 			updateBook(Book book);
	
	List<Book> 		listBookManager();
	List<Art> 		listArtManager();
	int 			insertBook(Book book);
	
	int 			deleteBook(int bookno);
	

	

}
