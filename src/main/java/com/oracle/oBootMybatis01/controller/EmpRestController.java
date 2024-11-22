 package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.SampleVO;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;

@RestController
	// @Controller + @ResponseBody
@RequiredArgsConstructor
public class EmpRestController {
	
	private final EmpService es;
	
	// 1. helloText
	@RequestMapping("/helloText")
	public String helloText() {
		System.out.println("EmpRestController Start...");
		
		String hello = "안녕";
		//		StringConverter
		return hello;
	}
	
	// 2. sendVO2
	@RequestMapping("/sample/sendVO2")
	public SampleVO sendVO2(Dept dept) {
		System.out.println("@RestController dept.getDeptno()->"+dept.getDeptno());
		
		SampleVO vo = new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setMno(dept.getDeptno());
		
		return vo;
	}
	
	// 3. sendVO3
	@RequestMapping("/sendVO3")
	public List<Dept> sendVO3() {
		System.out.println("@RestController sendVO3 Start...");
		
		List<Dept> deptList = es.deptSelect();
		
		return deptList;
	}
	
	// 8-6. listEmpAjaxForm2(ajax JSP 객체리스트 Get)
		// 결과를 text로 받음 
	// (0820)현장 HW 1-2
	@RequestMapping("/empnoDelete")
	public String empnoDelete(Emp emp) {
		// jsp에서 stringify로 보내지 않았기 때문에,
		// Controller에서 @RequestBody가 아닌 객체 그대로 받아서 사용할 수 있음
		System.out.println("@RestController empnoDelete Start...");
		System.out.println("@RestController empnoDelete emp->"+emp);
		
		int		delStatus = es.deleteEmp(emp.getEmpno());
		String	delStatusStr = Integer.toString(delStatus);
		
		return delStatusStr;
	}
	
	// 8-6. listEmpAjaxForm2(ajax JSP 객체리스트 Get)
		// 결과를 객체로 받음 
	@RequestMapping("/empnoDelete03")
	public Map<String, Object> empnoDelete03 (Emp emp) {
		//jsp에서 stringify로 보내면 Controller에서 @RequestBody를 입력하여 풀어서 받아줘야 함
		System.out.println("@RestController empnoDelete03 Start...");
		System.out.println("@RestController empnoDelete03 emp->"+emp);
		
		int		delStatus = es.deleteEmp(emp.getEmpno());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("delStatus", delStatus);
		
		return resultMap;
	}
	
}
