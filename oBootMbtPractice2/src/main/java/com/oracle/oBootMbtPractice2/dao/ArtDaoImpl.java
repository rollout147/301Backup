package com.oracle.oBootMbtPractice2.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMbtPractice2.model.Art;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ArtDaoImpl implements ArtDao {
	private final SqlSession session;

	// 5-1. writeFormBook 눌렀을 때 아트이름 선택창 나오게 하기
	@Override
	public List<Art> listArtManager() {
		List<Art> artList = null;
		System.out.println("ArtDaoImpl listArtManager Start...");
		
		try {
			artList = session.selectList("tkArtManager");
			System.out.println("ArtDaoImpl listArtManager artList.size()->"+artList.size());
			
		} catch (Exception e) {
			System.out.println("ArtDaoImpl listArtManager Exception->"+e.getMessage());
		}
		return artList;
	}

}
