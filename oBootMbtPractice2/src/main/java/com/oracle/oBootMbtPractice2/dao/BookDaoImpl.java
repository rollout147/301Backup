package com.oracle.oBootMbtPractice2.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMbtPractice2.model.Book;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
	//mybatis DB 연동
	private final SqlSession session;

	// 메인
	@Override
	public int totalBook() {
		int totBookCount = 0;
		System.out.println("BookDaoImpl totalBook Start...");
		
		try {
			totBookCount = session.selectOne("com.oracle.oBootMbtPractice2.BookMapper.bookTotal");
			System.out.println("BookDaoImpl totalBook totBookCount->"+totBookCount);
			
			
		} catch (Exception e) {
			System.out.println("BookDaoImpl Exception e.getMessage()->"+e.getMessage());
		}
		
		return totBookCount;
	}
	
	// 2. 페이지 번호 이동
	@Override
	public List<Book> listBook(Book book) {
		List<Book> bookList = null;
		System.out.println("BookDaoImpl listBook Start...");
		
		try {
			System.out.println("BookDaoImpl listBook book->"+book);

			bookList = session.selectList("tkBookAllList", book);
			System.out.println("BookDaoImpl listBook bookList.size()->"+bookList.size());
			
		} catch (Exception e) {
			System.out.println("BookDaoImpl Exception e.getMessage()->"+e.getMessage());
		}

		return bookList;
	}

	// 3. 페이지 조회
	@Override
	public Book detailBook(int bookno) {
		System.out.println("BookDaoImpl detailBook Start...");

		Book book = new Book();
		try {
			book = session.selectOne("tkBookDetail", bookno);
			System.out.println("BookDaoImpl detailBook getBookname->"+book.getBookname());
			
		} catch (Exception e) {
			System.out.println("BookDaoImpl detailBook Exception->"+e.getMessage());
		}
		
		return book;
	}

	// 4-2. 페이지 수정후 반응+리스트로 돌아가기
	@Override
	public int updateBook(Book book) {
		System.out.println("BookDaoImpl updateBook Start...");
		
		int updateCount = 0;
		try {
			updateCount = session.update("tkBookUpdate", book);
			System.out.println("BookDaoImpl updateBook updateCount->"+updateCount);
			
		} catch (Exception e) {
			System.out.println("BookDaoImpl updateBook Exception->"+e.getMessage());
		}
		return updateCount;
	}

	// 5-1. writeFormBook 눌렀을 때 장르 선택창 나오게 하기
	@Override
	public List<Book> listBookManager() {
		List<Book> bookList = null;
		System.out.println("BookDaoImpl listBookManager Start...");
		
		try {
			bookList = session.selectList("tkBookManager");
			System.out.println("BookDaoImpl listBookManager bookList.size()->"+bookList.size());
			
		} catch (Exception e) {
			System.out.println("BookDaoImpl listBookManager Exception->"+e.getMessage());
		}
		return bookList;
	}

	// 5-2. submit(확인)버튼 눌렀을 때 이동될 창 만들기
	
	@Override 
	public int insertBook(Book book) { 
		int result = 0;
		System.out.println("BookDaoImpl insertBook Start...");
		 
		try { 
			System.out.println("BookDaoImpl insertBook book->"+book);
			result = session.insert("tkBookInsert", book);
			System.out.println("BookDaoImpl insertBook result->"+result);
		  
		} catch (Exception e) {
			System.out.println("BookDaoImpl insertBook Exception->"+e.getMessage()); 
		}
		return result; 
	 }

	// 7. 데이터 삭제
	@Override
	public int deleteBook(int bookno) {
		int result = 0;
		System.out.println("BookDaoImpl deleteBook Start...");
		
		try {
			result = session.delete("deleteBook", bookno);
			
		} catch (Exception e) {
			System.out.println("BookDaoImpl deleteBook Exception->"+e.getMessage());
		}
		
		return result;
	}

}
