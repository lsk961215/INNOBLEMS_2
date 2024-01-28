package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.service.ProjectService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	
	@RequestMapping("/getProjectList")
	public Map getProjectList(HttpServletRequest request, @RequestBody ProjectDTO projectDTO) throws Exception {
		Map resultMap = new HashMap();
		
		try {
			resultMap = projectService.getProjectList(projectDTO);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("오류가 발생했습니다.");
		}
		
		return resultMap;
	}
	
	@RequestMapping("/addProject")
	public String addProject(HttpServletRequest request, @RequestBody ProjectDTO projectDTO) throws Exception {
		Map resultMap = new HashMap();
		
		try {
			int prjSeq = projectService.addProject(projectDTO);
			projectDTO.setPrjSeq(prjSeq);
			
			projectService.addProjectSkill(projectDTO);
			
			return "1";
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("오류가 발생했습니다.");
			
			return "-1";
		}
	}
	
	@RequestMapping("/getProjectDetail")
	public ProjectDTO getProjectDetail (HttpServletRequest request, @RequestBody ProjectDTO projectDTO) {
		return projectService.getProjectDetail(projectDTO);
	}
	
	@RequestMapping("/editProject")
	public String editProject(HttpServletRequest request, @RequestBody ProjectDTO projectDTO) throws Exception {
		try {
			projectService.editProject(projectDTO);
			
			projectService.addProjectSkill(projectDTO);
			
			return "1";
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("오류가 발생했습니다.");
			
			return "-1";
		}
	}
	
	@RequestMapping("/delProject")
	public int delProject(HttpServletRequest request, @RequestBody List<String> prjSeqList) throws Exception {
		try {
			projectService.delProject(prjSeqList);
			return 0;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("오류가 발생했습니다.");
			return 1;
		}
	}
}
