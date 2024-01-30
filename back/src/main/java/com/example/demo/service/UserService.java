package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDAO;
import com.example.demo.dto.UserDTO;

@Service
public class UserService {
	
	@Autowired
	UserDAO userDAO;
	
	public Map getUserList(UserDTO userDTO) {
		Map resultMap = new HashMap();
		
		int pageNum = userDTO.getPageNum();
		int countPerPage = userDTO.getCountPerPage();
		
		int startNum = 0;
		int endNum = 0;
		
		startNum = (pageNum-1)*countPerPage+1;
		endNum = pageNum*countPerPage;
		
		userDTO.setStartNum(startNum);
		userDTO.setEndNum(endNum);
		
		List userList = userDAO.getUserList(userDTO);
		
		int total = userDAO.getUserTotal(userDTO);
		
		int groupCount = 5;
		
		int totalPaging = (int) Math.ceil((double)total / countPerPage);
		
		int position = (int) Math.ceil((double)pageNum / groupCount);
		
		int beginPaging = (position-1) * groupCount + 1;
		int endPaging = position * groupCount;
		
		if(endPaging > totalPaging) {
			endPaging = totalPaging;
		}
		
		resultMap.put("beginPaging", beginPaging);
		resultMap.put("endPaging", endPaging);
		resultMap.put("totalPaging", totalPaging);
		resultMap.put("userList", userList);
		resultMap.put("pageNum", pageNum);
		resultMap.put("groupCount", groupCount);
		resultMap.put("position", position);
		
		return resultMap;
	}
	
	public int addUser(UserDTO userDTO) {
		return userDAO.addUser(userDTO);
	}
	
	public void addUserSkill(UserDTO userDTO) {
		userDAO.delUserSkill(userDTO);
		
		for(int i = 0; i<userDTO.getSkillList().size(); i++) {
			userDTO.setSkills((String) userDTO.getSkillList().get(i));
			userDAO.addUserSkill(userDTO);
		}
		return;
	}
	
	public UserDTO getUserDetail(UserDTO userDTO) {
		return userDAO.getUserDetail(userDTO);
	}

	public void editUser(UserDTO userDTO) {
		userDAO.editUser(userDTO);
		return;
	}
	
	public void delUser(List list) {
		userDAO.delUser(list);
		return;
	}
	
	public Map getUserProjectList(UserDTO userDTO) {
		Map resultMap = new HashMap();
		
		int pageNum = userDTO.getPageNum();
		int countPerPage = userDTO.getCountPerPage();
		
		int startNum = 0;
		int endNum = 0;
		
		startNum = (pageNum-1)*countPerPage+1;
		endNum = pageNum*countPerPage;
		
		userDTO.setStartNum(startNum);
		userDTO.setEndNum(endNum);
		
		List userProjectList = userDAO.getUserProjectList(userDTO);
		
		int total = userDAO.getUserProjectTotal(userDTO);
		
		int groupCount = 5;
		
		int totalPaging = (int) Math.ceil((double)total / countPerPage);
		
		int position = (int) Math.ceil((double)pageNum / groupCount);
		
		int beginPaging = (position-1) * groupCount + 1;
		int endPaging = position * groupCount;
		
		if(endPaging > totalPaging) {
			endPaging = totalPaging;
		}
		
		resultMap.put("beginPaging", beginPaging);
		resultMap.put("endPaging", endPaging);
		resultMap.put("totalPaging", totalPaging);
		resultMap.put("userProjectList", userProjectList);
		resultMap.put("pageNum", pageNum);
		resultMap.put("groupCount", groupCount);
		resultMap.put("position", position);
		
		return resultMap;
	}
}
