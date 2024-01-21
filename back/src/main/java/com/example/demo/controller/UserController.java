package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping("/getUserList")
	public Map getUserList(HttpServletRequest request, UserDTO userDTO) throws Exception {
		Map selectMap = new HashMap();
		
		int pageNum = 1;
		int countPerPage = 5;
		
		List skills = new ArrayList();
		
		if(userDTO.getSkills() != null) {
			String str = userDTO.getSkills();
			String[] skillArray = str.split(",");
			
			for (int i = 0; i<skillArray.length; i++) {
				skills.add(skillArray[i]);
			}
		}
		
		String tmp_pageNum = request.getParameter("pageNum");
		
		if(tmp_pageNum != null) {
			try { 
				pageNum = Integer.parseInt(tmp_pageNum);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		String tmp_countPerPage = request.getParameter("countPerPage");
		
		if(tmp_countPerPage != null) {
			try { 
				countPerPage = Integer.parseInt(tmp_countPerPage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		selectMap.put("skills", skills);
		selectMap.put("userDTO", userDTO);
		selectMap.put("pageNum", pageNum);
		selectMap.put("countPerPage", countPerPage);
		
		Map userMap = new HashMap();
		
		try {
			userMap = userService.getUserList(selectMap);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("오류가 발생했습니다.");
		}
		
		return userMap;
	}
}
