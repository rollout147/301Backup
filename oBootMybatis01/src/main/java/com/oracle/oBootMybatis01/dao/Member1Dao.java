package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.Member1;

public interface Member1Dao {
	
	int				memCount(String id);	// Member1의 Count

	// (0814) HW
	// 4. interCeptor 진행 Test
	List<Member1> 	listMem(Member1 member1);

	// 9. Transaction Test
	int 			transactionInsertUpdate();	// transaction False
	int 			transactionInsertUpdate3();	// transaction True
}
