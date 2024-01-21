package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

	@Autowired
	SqlSession sqlSession;
	
	public List getUserList(Map map) {
		return sqlSession.selectList("user.getUserList", map);
	}
	
	public int getUserTotal(Map map) {
		return sqlSession.selectOne("user.getUserTotal", map);
	}
}
