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
		
		return resultMap;
	}
}
