package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CodeDAO;

@Service
public class CodeService {
	
	@Autowired
	CodeDAO codeDAO;
	
	public List getCodeList() {
		return codeDAO.getCodeList();
	}
	

}
