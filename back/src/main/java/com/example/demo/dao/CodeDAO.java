package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CodeDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	public List getCodeList() {
		return sqlSession.selectList("code.getCodeList");
	}

}
