package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectDTO {
	private int prjSeq;
	private String cusCD;
	private String prjNm;
	private String prjSTDT;
	private String prjEDDT;
	private String prjNote;
	private String minSTDT;
	private String maxSTDT;
	private String minEDDT;
	private String maxEDDT;
	private String skills;
	private int countPerPage = 5;
	private int pageNum = 1;
	private int startNum;
	private int endNum;
	private int beginPaging;
	private int endPaging;
	private int totalPaging;
	private List skillList;
}