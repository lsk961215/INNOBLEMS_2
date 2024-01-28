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
	
	public int addProject(ProjectDTO projectDTO) {
		return projectDAO.addProject(projectDTO);
	}
	
	public void addProjectSkill(ProjectDTO projectDTO) {
		projectDAO.delProjectSkill(projectDTO);
		
		for(int i = 0; i<projectDTO.getSkillList().size(); i++) {
			projectDTO.setSkills((String) projectDTO.getSkillList().get(i));
			projectDAO.addProjectSkill(projectDTO);
		}
		return;
	}
	
	public ProjectDTO getProjectDetail(ProjectDTO projectDTO) {
		return projectDAO.getProjectDetail(projectDTO);
	}

	public void editProject(ProjectDTO projectDTO) {
		projectDAO.editProject(projectDTO);
		return;
	}
	
	public void delProject(List list) {
		projectDAO.delProject(list);
		return;
	}
}
