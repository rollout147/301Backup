package com.oracle.oBootMybatis01.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.service.MemberJpaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberJpaController {
	
	private final MemberJpaService memberJpaService;
	
	@GetMapping(value = "/memberJpa/new")
	public String createForm() {
		System.out.println("MemberController /members/new start..");
	
		return "memberJpa/createMemberForm";
	}
	
	// 1. 새 멤버 입력
	@PostMapping(value = "/memberJpa/save")
	public String create(Member member) {
		System.out.println("MemberController memberJpa/save start...");
		System.out.println("MemberController memberJpa/save member->"+member);

		memberJpaService.join(member);
		
		return "memberJpa/createMemberForm";
	}
	
	//2. 멤버 조회
	@GetMapping(value = "/members")
	public String listMember(Model model) {
		System.out.println("MemberController listMember Start...");
		
		List<Member> memberList = memberJpaService.getListAllMember();
		
		model.addAttribute("members", memberList);
		
		return "memberJpa/memberList";
	}
	// 3. 멤버 정보 Null확인
	@GetMapping(value = "/memberJpa/memberUpdateForm")
	public String memberUpdateForm(Member member1, Model model) {
		Member member = null;
		String rtnJsp = "";
		System.out.println("MemberController memberUpdateForm id->"+member1.getId());
		
		Optional<Member> maybeMember = memberJpaService.findById(member1.getId());
		
		if (maybeMember.isPresent()) {
			System.out.println("MemberController memberUpdateForm maybeMember Is Not Null");
			
			member = maybeMember.get();
			model.addAttribute("member",member);
			// model.addAttribute("message", "member가 존재합니다. 수정 후 입력해주세요");
			rtnJsp = "memberJpa/memberModify";
		
		} else {
			System.out.println("MemberController memberUpdateForm maybeMember Is Null");
				// 검증용
			model.addAttribute("message", "member가 입력되지 않았습니다. 입력해주세요.");
			rtnJsp = "forward:/members";
		}
		return rtnJsp;
	}
	
	// 4. 멤버 정보 수정
	@GetMapping(value = "/memberJpa/memberUpdate")
	public String memberUpdate(Member member, Model model) {
		
		System.out.println("MemberController member->"+member);
		
		memberJpaService.memberUpdate(member);
		System.out.println("MemberController memberUpdate after...");
		
		return "redirect:/members";
	}
	
}
