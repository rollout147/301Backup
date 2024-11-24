package com.oracle.oBootMbtPractice2.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMbtPractice2.model.Art;
import com.oracle.oBootMbtPractice2.model.Book;
import com.oracle.oBootMbtPractice2.service.BookService;
import com.oracle.oBootMbtPractice2.service.Paging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookController {
	private final BookService bs; 
	
	// 1. 메인
	@RequestMapping(value = "listBookStart")
	public String listBookStart(Book book, Model model) {
	System.out.println("BookController listBookStart Start...");
	
	int totalBook = bs.totalBook();
	System.out.println("BookController bs.totalBook()->"+bs.totalBook());
	
	String currentPage = "1";
	System.out.println("BookController currentPage->"+currentPage);
	// 페이지 작업으로 이동
	
	// 페이지 작업한거 가져오기
	Paging page = new Paging(totalBook, currentPage);
	System.out.println("BookController listBookStart page Start...");
	book.setStart(page.getStart());
	book.setEnd(page.getEnd());
	System.out.println("BookController listBookStart book->"+book);
	// Service의 List<Book>로 이동
	
	List<Book> listBook = bs.listBook(book);
	System.out.println("BookController listBookStart listBook.size()->"+listBook.size());
	
	model.addAttribute("totalBook",totalBook);
	model.addAttribute("listBook", listBook);
	model.addAttribute("page",page);
		
	return "list";
	}
	
	// 2. 페이지 번호 이동
	@RequestMapping(value = "listBook")
	public String listBook(Book book, Model model) {
		System.out.println("listBookStart listBook Start...");
		
		int totalBook = bs.totalBook();
		System.out.println("BookController bs.totalBook()->"+bs.totalBook());
	
		// 페이지 작업한거 가져오기
		Paging page = new Paging(totalBook, book.getCurrentPage());
		System.out.println("BookController listBookStart page Start...");
		book.setStart(page.getStart());
		book.setEnd(page.getEnd());
		System.out.println("BookController listBookStart book->"+book);

		List<Book> listBook = bs.listBook(book);
		System.out.println("BookController listBook.size()->"+listBook.size());
		
		model.addAttribute("totalBook", totalBook);
		model.addAttribute("listBook", listBook);
		model.addAttribute("page", page);
		
		return "list";
	}
	// 3. 페이지 조회
	@GetMapping(value = "detailBook")
	public String detailBook(Book book1, Model model) {
		System.out.println("BookController detailBook Start...");
		System.out.println("BookController detailBook book->"+book1);
		
		Book book = bs.detailBook(book1.getBookno());
		System.out.println("BookController detailBook book->"+book);
		
		model.addAttribute("book", book);
	
		return "detailBook";
	}	
	
	// 4-1. 페이지 수정 버튼 누를때 로직
	@GetMapping(value = "updateFormBook")
	public String updateFormBook(Book book1, Model model) {
		System.out.println("BookController updateFormBook Start...");
		
		Book book = bs.detailBook(book1.getBookno());
		System.out.println("BookController updateFormBook book->"+book);
		
		model.addAttribute("book", book);
		
		return "updateFormBook";
		
	}
	
	// 4-2. 페이지 수정후 반응+리스트로 돌아가기
	@PostMapping(value = "updateBook")
	public String updateBook(Book book, Model model) {
		System.out.println("BookController updateBook Start...");
		System.out.println("BookController updateBook book->"+book);
		
		int updateCount = bs.updateBook(book);
		System.out.println("BookController updateFormBook updateCount->"+updateCount);
		
		model.addAttribute("book", book);
		
		return "forward:listBook";	
	}
	
	// 5-1. writeFormBook 눌렀을 때 장르,아트이름 선택창 나오게 하기
	@RequestMapping(value = "writeFormBook")
	public String writeFormBook(Model model) {
		System.out.println("BookController writeFormBook Start...");
		
		// 장르만 Get
		List<Book> bookList = bs.listBookManager();
		System.out.println("BookController writeFormBook bookList.size()->"+bookList.size());
		model.addAttribute("bookMngList", bookList);
		
		// 아트이름 Get
		List<Art> artList = bs.listArtManager();
		System.out.println("BookController writeFormBook artList.size()->"+artList.size()); 
		model.addAttribute("artMngList", artList);
		
		return "writeFormBook";
	}
	
	
	 // 5-2. writeFormEmp 누른 후 수정완료 로직
	  
	@PostMapping(value = "writeBook") 
	public String writeBook(Book book, Model model) { 
		System.out.println("BookController writeBook Start...");
		System.out.println("BookController writeBook book->"+book);
		
		int insertResult = bs.insertBook(book);
		System.out.println("BookController writeBook insertResult->"+insertResult);
		
		if (insertResult > 0)
			return "redirect:listBook";
		
		else {
			model.addAttribute("msg", "입력 실패. 확인 바랍니다.");
			System.out.println("BookController writeBook insertResult failure->"+insertResult);
			return "forward:writeFormBook";
		}	
	}
	
	// 6-3. Submit(확인)버튼 눌렀을 때 이동될 창 만들기
	@GetMapping(value = "confirm")
	public String confirmBook(Book book1, Model model) {
		System.out.println("BookController confirmBook Start...");
		
		Book book = bs.detailBook(book1.getBookno());
		System.out.println("BookController confirmBook book->"+book);
		
		model.addAttribute("bookno",book1.getBookno());
		
		if(book != null) {
			System.out.println("BookController confirmBook 중복된사번");
			model.addAttribute("msg", "중복된 사번입니다");
		
		} else {
			System.out.println("BookController confirmBook 사용가능한사번");
			model.addAttribute("msg","사용가능한 사번입니다");
		}
		return "forward:writeFormBook";	
	}
	
	// 7. 데이터 삭제
	@RequestMapping(value = "deleteBook")
	public String deleteBook(Book book, Model model) {
		System.out.println("BookController deleteBook Start...");
		
		int result = bs.deleteBook(book.getBookno());
		System.out.println("BookController deleteBook result->"+result);
		
		return "redirect:listBook";
		
	}

}
