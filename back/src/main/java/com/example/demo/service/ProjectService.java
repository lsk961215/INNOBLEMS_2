package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ProjectDAO;
import com.example.demo.dto.ProjectDTO;

@Service
public class ProjectService {
	
	@Autowired
	ProjectDAO projectDAO;
	
	public Map getProjectList(ProjectDTO projectDTO) {
		Map resultMap = new HashMap();
		
		int pageNum = projectDTO.getPageNum();
		int countPerPage = projectDTO.getCountPerPage();
		
		int startNum = 0;
		int endNum = 0;
		
		startNum = (pageNum-1)*countPerPage+1;
		endNum = pageNum*countPerPage;
		
		projectDTO.setStartNum(startNum);
		projectDTO.setEndNum(endNum);
		
		List projectList = projectDAO.getProjectList(projectDTO);
		
		int total = projectDAO.getProjectTotal(projectDTO);
		
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
		resultMap.put("projectList", projectList);
		resultMap.put("pageNum", pageNum);
		resultMap.put("groupCount", groupCount);
		resultMap.put("position", position);
		
		return resultMap;
	}
	
//	public int addUser(UserDTO userDTO) {
//		return userDAO.addUser(userDTO);
//	}
//	
//	public void addUserSkill(UserDTO userDTO) {
//		userDAO.delUserSkill(userDTO);
//		
//		for(int i = 0; i<userDTO.getSkillList().size(); i++) {
//			userDTO.setSkills((String) userDTO.getSkillList().get(i));
//			userDAO.addUserSkill(userDTO);
//		}
//		return;
//	}
//	
//	public UserDTO getUserDetail(UserDTO userDTO) {
//		return userDAO.getUserDetail(userDTO);
//	}
//
//	public void editUser(UserDTO userDTO) {
//		userDAO.editUser(userDTO);
//		return;
//	}
//	
//	public void delUser(List list) {
//		userDAO.delUser(list);
//		return;
//	}
}
