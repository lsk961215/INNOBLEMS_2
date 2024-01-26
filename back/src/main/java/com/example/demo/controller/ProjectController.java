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
	
//	@RequestMapping("/addUser")
//	public String addUser(HttpServletRequest request, @RequestBody UserDTO userDTO) throws Exception {
//		Map resultMap = new HashMap();
//		
//		try {
//			String join_pw = userDTO.getUsrPw();
//			
//			new SHA256();
//			
//			String salt = SHA256.createSalt(join_pw);
//			
//			String pw = SHA256.encrypt(join_pw, salt);
//			
//			userDTO.setUsrPw(pw);
//			userDTO.setSalt(salt);
//			
//			int usrSeq = userService.addUser(userDTO);
//			userDTO.setUsrSeq(usrSeq);
//			
//			userService.addUserSkill(userDTO);
//			
//			return "1";
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.println("오류가 발생했습니다.");
//			
//			return "-1";
//		}
//	}
//	
//	@RequestMapping("/getUserDetail")
//	public UserDTO getUserDetail (HttpServletRequest request, @RequestBody UserDTO userDTO) {
//		return userService.getUserDetail(userDTO);
//	}
//	
//	@RequestMapping("/editUser")
//	public String editUser(HttpServletRequest request, @RequestBody UserDTO userDTO) throws Exception {
//		try {
//			if(userDTO.getUsrPw() != null) {
//				String save_pw = userDTO.getUsrPw();
//				
//				new SHA256();
//				
//				String salt = SHA256.createSalt(save_pw);
//				
//				String pw = SHA256.encrypt(save_pw, salt);
//				
//				userDTO.setUsrPw(pw);
//				userDTO.setSalt(salt);
//			}
//			
//			userService.editUser(userDTO);
//			
//			userService.addUserSkill(userDTO);
//			
//			return "1";
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.println("오류가 발생했습니다.");
//			
//			return "-1";
//		}
//	}
//	
//	@RequestMapping("/delUser")
//	public int delUser(HttpServletRequest request, @RequestBody List<String> usrSeqList) throws Exception {
//		try {
//			userService.delUser(usrSeqList);
//			return 0;
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.println("오류가 발생했습니다.");
//			return 1;
//		}
//	}
}