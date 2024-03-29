package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CodeService;

@RestController
public class CodeController {

	@Autowired
	CodeService codeService;
	
	@RequestMapping("/getCodeList")
	public List getCodeList() {
		return codeService.getCodeList();
	}
}
