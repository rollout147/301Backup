package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.oBootMybatis01.model.Member1;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member1DaoImpl  implements Member1Dao {
	
	private final PlatformTransactionManager transactionManager;
	
	//Mybatis 연동
	private final SqlSession session;

	// (0814) 현장 HW 1-3
	@Override
	public int memCount(String id) {
		System.out.println("Member1DaoImpl memCount Start...");
		
		// Mapper ==> Member1.xml 
		// result = session.selectOne("memCount", id);
		int result = 0;
		System.out.println("Member1DaoImpl id->"+id);
		
		try {
			result = session.selectOne("memCount", id);
			
		} catch (Exception e) {
			System.out.println("Member1DaoImpl memCount Exception->"+e.getMessage());
		}
		
		return result;
	}

	// (0814) HW 
	// 4. interCeptor 진행 Test
	@Override
	public List<Member1> listMem(Member1 member1) {
		List<Member1> listMember1 = null;
		System.out.println("Member1DaoImpl listMem Start...");
		
		try { 
			listMember1 = session.selectList("listMember1", member1);
			System.out.println("Member1DaoImpl listMem listMember1->"+listMember1);
			
		} catch (Exception e) {
			System.out.println("Member1DaoImpl listMember1 Exception->"+e.getMessage());
		}
		return listMember1;
	}

	// 9. Transaction Test
		// transaction False
	@Override
	public int transactionInsertUpdate() {
		int result = 0;
		System.out.println("Member1DaoImpl transactionInsertUpdate Start...");
		
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();
		
		try {
			// transaction Test 실패 케이스
			// 결론 ==> SqlSession은 하나 실행할 때마다 자동으로 Commit됨
			member1.setId("1005");
			member1.setPassword("2345");
			member1.setName("강유6");
			
			result = session.insert("insertMember1", member1);
			System.out.println("Member1DaoImpl transactionInsertUpdate member1 result->"+result);
			
			member2.setId("1006");
			member2.setPassword("3456");
			member2.setName("이순신7");
			
			result = session.insert("insertMember1", member2);
			System.out.println("Member1DaoImpl transactionInsertUpdate member2 result->"+result);
			
		} catch (Exception e) {
			System.out.println("Member1DaoImpl transactionInsertUpdate Exception->"+e.getMessage());
			result = -1;
		}
		
		return result;
	}

	// transaction True
	@Override
	public int transactionInsertUpdate3() {
		int result = 0;
		System.out.println("Member1DaoImpl transactionInsertUpdate3 Start...");
		
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();
		
		TransactionStatus txStatus = 
						transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			// transaction Test 성공 케이스
			// 결론 ==> SqlSession은 하나 실행할 때마다 자동으로 Commit됨
			// Transaction관리는 transactionManager의 getTransaction을가지고 상태따라 설정
			member1.setId("1007");
			member1.setPassword("2345");
			member1.setName("강유6");
			
			result = session.insert("insertMember1", member1);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 member1 result->"+result);
			
			// member1과 member2의 Id를 같게 설정하면 오류가 남
			member2.setId("1008");
			member2.setPassword("3457");
			member2.setName("이순신7");
			
			result = session.insert("insertMember1", member2);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 member2 result->"+result);
			transactionManager.commit(txStatus);
			
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 Exception->"+e.getMessage());
			result = -1;
		}
		
		return result;
	}

}
