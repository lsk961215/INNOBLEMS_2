package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
	private int usrSeq;
	private String usrId;
	private String usrPw;
	private String usrNm;
	private String usrBDT;
	private String gdCD;
	private String usrINDT;
	private String stCD;
	private String raCD;
	private String grCD;
	private String dvCD;
	private String usrImg;
	private String usrPn;
	private String usrEm;
	private String usrAd;
	private String minDT;
	private String maxDT;
	private String skills;
	private String message;
	private String salt;
	private int countPerPage = 5;
	private int pageNum = 1;
	private int startNum;
	private int endNum;
	private int beginPaging;
	private int endPaging;
	private int totalPaging;
	private List skillList;
}
