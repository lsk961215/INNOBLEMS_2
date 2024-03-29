package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDTO;

@Repository
public class UserDAO {

	@Autowired
	SqlSession sqlSession;
	
	public List getUserList(UserDTO userDTO) {
		return sqlSession.selectList("user.getUserList", userDTO);
	}
	
	public int getUserTotal(UserDTO userDTO) {
		return sqlSession.selectOne("user.getUserTotal", userDTO);
	}
	
	public int addUser(UserDTO userDTO) {
		sqlSession.insert("user.addUser", userDTO);
		return userDTO.getUsrSeq();
	}
	
	public void addUserSkill(UserDTO userDTO) {
		sqlSession.insert("skill.addUserSkill", userDTO);
		return;
	}
	
	public UserDTO getUserDetail(UserDTO userDTO) {
		return sqlSession.selectOne("user.getUserDetail", userDTO);
	}
	
	public void delUserSkill(UserDTO userDTO) {
		sqlSession.delete("skill.delUserSkill", userDTO);
	}
	
	public void editUser(UserDTO userDTO) {
		sqlSession.update("user.editUser", userDTO);
		return;
	}
	
	public void delUser(List list) {
		sqlSession.delete("user.delUser", list);
		return;
	}
	
	public List getUserProjectList(UserDTO userDTO) {
		return sqlSession.selectList("user.getUserProjectList", userDTO);
	}
	
	public int getUserProjectTotal(UserDTO userDTO) {
		return sqlSession.selectOne("user.getUserProjectTotal", userDTO);
	}
}
