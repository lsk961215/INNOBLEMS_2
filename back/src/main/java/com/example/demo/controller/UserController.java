package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Map getUserList(HttpServletRequest request, @RequestBody UserDTO userDTO) throws Exception {
		Map resultMap = new HashMap();
		
		try {
			resultMap = userService.getUserList(userDTO);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("오류가 발생했습니다.");
		}
		
		return resultMap;
	}
}
