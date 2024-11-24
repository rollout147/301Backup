package com.oracle.oBootMbtPractice2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMbtPractice2.dao.ArtDao;
import com.oracle.oBootMbtPractice2.dao.BookDao;
import com.oracle.oBootMbtPractice2.model.Art;
import com.oracle.oBootMbtPractice2.model.Book;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookDao bd;
	private final ArtDao ad;

	// 1. 메인
	@Override
	public int totalBook() {
		System.out.println("BookServiceImpl totalBook Start...");
		
		int totBookCnt = bd.totalBook();
		System.out.println("BookServiceImpl totalBook totBookCnt->"+totBookCnt);
		
		return totBookCnt;
	}
	
	// 2. 페이지 번호 이동
	@Override
	public List<Book> listBook(Book book) {
		List<Book> bookList = null;
		System.out.println("BookServiceImpl listBook Start...");
		
		bookList = bd.listBook(book);
		System.out.println("BookServiceImpl listBook bookList.size()->"+bookList.size());
				
		return bookList;
	}

	// 3. 페이지 조회
	@Override
	public Book detailBook(int bookno) {
		System.out.println("BookServiceImpl detailBook Start...");
		
		Book book = null;
		book = bd.detailBook(bookno);
		System.out.println("BookServiceImpl detailBook book->"+book);	
		
		return book;
	}

	// 4-2. 페이지 수정후 반응+리스트로 돌아가기
	@Override
	public int updateBook(Book book) {
		System.out.println("BookServiceImpl updateBook Start...");
		
		int updateCount = 0;
		updateCount = bd.updateBook(book);
		System.out.println("BookServiceImpl updateBook updateCount->"+updateCount);
		
		return updateCount;
	}

	// 5-1. writeFormBook 눌렀을 때 장르,아트이름 선택창 나오게 하기
	@Override
	public List<Book> listBookManager() {
		List<Book> bookList = null;
		System.out.println("BookServiceImpl listManager Start...");
		
		bookList = bd.listBookManager();
		System.out.println("BookServiceImpl listManager bookList.size()->"+bookList.size());
		
		return bookList;
	}
	@Override
	public List<Art> listArtManager() {
		List<Art> artList = null;
		System.out.println("BookServiceImpl listArtManager Start...");
		
		artList = ad.listArtManager();
		System.out.println("BookServiceImpl listArtManager artList.size()->"+artList.size());
		
		return artList;
	}
	
	// 5-2. submit(확인)버튼 눌렀을 때 이동될 창 만들기
	
	@Override 
	public int insertBook(Book book) { 
		int result = 0;
		System.out.println("BookServiceImpl insertBook Start...");
		  
		result = bd.insertBook(book);
		System.out.println("BookServiceImpl insertBook result->"+result);
		  
		return result; 
	}

	// 7. 데이터 삭제
	@Override
	public int deleteBook(int bookno) {
		int result = 0;
		System.out.println("BookServiceImpl deleteBook Start...");
		
		result = bd.deleteBook(bookno);
		System.out.println("BookServiceImpl deleteBook result->"+result);

		return result;
	}
	 

}
