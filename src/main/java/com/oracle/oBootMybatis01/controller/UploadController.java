package com.oracle.oBootMybatis01.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UploadController {
	
	// 0. UploadForm 시작화면
	@RequestMapping(value = "upLoadFormStart")
	public String upLoadFormStart(Model model) {
		System.out.println("UploadController upLoadFormStart Start...");
		
		return "upLoadFormStart";
	}
	
	// 1. 이미지 업로드
	@RequestMapping(value = "uploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		System.out.println("UploadController uploadForm GET Start...");
		System.out.println();
	
	}
	
	@RequestMapping(value = "uploadForm", method = RequestMethod.POST)
	public String uploadForm(HttpServletRequest request, Model model) 
			throws IOException, Exception {
		
		Part image = request.getPart("file1");
		InputStream inputStream = image.getInputStream();
		
		// 파일 확장자 구하기
		String fileName = image.getSubmittedFileName();
		String[] split = fileName.split("\\.");
		String originalName = split[split.length - 2];
		String suffix = split[split.length - 1];
		
		System.out.println("fileName->"+fileName);
		System.out.println("originalName->"+originalName);
		System.out.println("suffix->"+suffix);
		
		// Servlet을 상속 받지 못했을 때 realPath를 불러오는 방법
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		System.out.println("UploadController uploadForm POST Start...");
		
		String savedName = uploadFile(originalName, inputStream, uploadPath, suffix);
						// uploadFile에서 create 자동완성해서 아래 private 만들기
		// Service ==> DB CRUD
		// 여기까지 진행한 뒤, 아래 private로 가기
		
		log.info("Return savedName: " + savedName);
		model.addAttribute("savedName", savedName);
		System.out.println("UploadController uploadForm savedName->"+savedName);
		
		return "uploadResult";
	}

	private String uploadFile(String	originalName, 
							InputStream inputStream, 
							String 		uploadPath, 
							String 		suffix) throws IOException {
		// universally unique identifier (UUID).
		UUID uid = UUID.randomUUID();
		// requestPath = requestPath + "/resources/image";
		System.out.println("UploadController uploadFile uploadPath->"+uploadPath);
		
		// Directory 생성 + RealPath 사용
		File fileDirectory = new File(uploadPath);
		if (!fileDirectory.exists()) {
			// 신규 폴더(Directory) 생성
			fileDirectory.mkdirs();
			System.out.println("업로드용 폴더 생성 : " + uploadPath);
		}
		
		String savedName = uid.toString() + "_" + originalName + "." + suffix;
		log.info("savedName: " + savedName);
		System.out.println("UploadController uploadFile savedName->"+savedName);
		
		// 임시파일 생성
		File tempFile = new File(uploadPath+savedName);
		
		// --------------------------------------------
		// Backup File 생성 (안만들어도 무방함)
		File tempFile3 = new File("C:/BACKUP/"+savedName);
		FileOutputStream outputStream3 = new FileOutputStream(tempFile3);
		// --------------------------------------------
		
		
		// 생성된 임시파일에 요청으로 넘어온 file의 inputStream 복사
		try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
			System.out.println("UploadController outputStream Start...");
			int read;
			byte[] bytes = new byte[1024];
				// 더 큰 용량의 파일을 올리려면 yml에서 설정 변경하기
			while ((read = inputStream.read(bytes)) != -1) {
					// -1: 파일이 끝날때까지 요청
				// Target File의 요청으로 넘어온 file의 inputStream 복사
				outputStream.write(bytes, 0, read);
				// backup 파일의 요청으로 넘어온 file의 inputStream 복사
				outputStream.write(bytes, 0, read);
			}
			
		} finally {
			System.out.println("UpLoad The End");
		}
		outputStream3.close();
		System.out.println("UploadController outputStream3 close...");
		
		return savedName;
		// 여기까지 끝나면 다시 위로 올라가기
	}
	
	// 2. 올린 이미지 삭제
	@RequestMapping(value = "uploadFileDelete", method = RequestMethod.GET)
	public String uploadFileDelete(HttpServletRequest request, Model model) {
		
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		// Backup Folder 삭제
		String uploadPath3 = "C:/BACKUP/";
		// 올린 파일 삭제
		String delFile = request.getParameter("delFile");
								// uploadResult.jsp의 delFile과 연결됨
		System.out.println("uploadFileDelete GET Start...");
		
		String deleteFile = uploadPath + delFile;
		String deleteFile3 = uploadPath3 + delFile;
		System.out.println("uploadFileDelete deleteFile->"+deleteFile);
		System.out.println("uploadFileDelete deleteFile3->"+deleteFile3);
		// 여기까지 진행한 뒤, 아래 private로 가기
		
		int delResult = upFileDelete(deleteFile);
		System.out.println("uploadFileDelete deleteFile delResult->"+delResult);
		int delResult3 = upFileDelete(deleteFile3);
		System.out.println("uploadFileDelete deleteFile delResult3->"+delResult3);
		
		model.addAttribute("deleteFile", deleteFile);
		System.out.println("UploadController upFileDelete deleteFile->"+deleteFile);
		model.addAttribute("delResult", delResult);
		System.out.println("UploadController upFileDelete delResult->"+delResult);
		
		return "uploadResult";
	}

	private int upFileDelete(String deleteFileName) {
		int result = 0;
		log.info("upFileDelete result->" + deleteFileName);
		System.out.println("UploadController upFileDelete Start...");
		
		File file = new File(deleteFileName);
		if( file.exists()) {
			if(file.delete()) {
				System.out.println("파일삭제 성공");
				result = 1;
			} else {
				System.out.println("파일삭제 실패");
				result = 0;
			}
			
		} else {
			System.out.println("삭제할 파일이 존재하지 않습니다");
			result = -1;
		}
		return result;
	}
}
