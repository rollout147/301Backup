package com.oracle.oBootMybatis01.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryImpl implements MemberJpaRepository {

	private final EntityManager em;
	
	// 1. 새 멤버 입력
	@Override
	public Member save(Member member) {
		System.out.println("MemberJpaRepositoryImpl save Start...");
		
		em.persist(member);
		
		return member;
	}

	//2. 멤버 조회
	@Override
	public List<Member> findAll() {
		System.out.println("MemberJpaRepositoryImpl findAll Start...");
		
		List<Member> memberList = em.createQuery("select m from Member m", Member.class)
									.getResultList();
		
		return memberList;
	}
	
	// 3. 멤버 정보 Null확인
	@Override
	public Optional<Member> fingById(Long memberId) {
		System.out.println("MemberJpaRepositoryImpl fingById Start...");
		
		Member member = em.find(Member.class, memberId);
		
		return Optional.ofNullable(member);
	}

	// 4. 멤버 정보 수정
	@Override
	public void updateByMember(Member member) {
		System.out.println("MemberJpaRepositoryImpl updateByMember Start...");

//		1. Update: merge 방법
//			merge	==> 현재 Setting된 것만 수정되고, setting되지 않은 값은 Null이 나옴
		em.merge(member);
		
		
//		2. Update: 일반 방법		
//		Member member3 = em.find(Member.class, member.getId());
//		
//		// Member member3 = new Member();
//		member3.setId(member.getId());
//		member3.setName(member.getName());
		System.out.println("MemberJpaRepositoryImpl updateByMember after...");
		
	}	
}

