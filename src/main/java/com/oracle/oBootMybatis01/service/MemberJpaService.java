package com.oracle.oBootMybatis01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.repository.MemberJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberJpaService {

	private final MemberJpaRepository memberJpaRepository;

	// 1. 새 멤버 입력
	public void join(Member member) {
		System.out.println("MemberJpaService Join Start...");

		memberJpaRepository.save(member);
		
	}

	//2. 멤버 조회
	public List<Member> getListAllMember() {
		System.out.println("MemberJpaService getListAllMember Start...");
		
		List<Member> listMember = memberJpaRepository.findAll();
		System.out.println("MemberJpaService getListAllMember listMember.size()->"+listMember.size());
		
		return listMember;
	}

	// 3. 멤버 정보 Null확인
	public Optional<Member> findById(Long memberId) {
		System.out.println("MemberJpaService findById Start...");
		
		Optional<Member> member = memberJpaRepository.fingById(memberId);
		
		return member;
	}
	
	//4. 멤버 정보 수정 
	public void memberUpdate(Member member) {
		System.out.println("MemberJpaService memberUpdate member->"+member);

		memberJpaRepository.updateByMember(member);
		System.out.println("MemberJpaService memberUpdate after...");
	
		return;
	}
}
