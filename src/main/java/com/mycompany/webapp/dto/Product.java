package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class Product {
	private String pid;
	private String bname;
	private String clarge;
	private String cmedium;
	private String csmall;
	private int pno;
	private String pname;
	private int pprice;
	private String pdetail;
	private String pseason;
	private int ptotalamount;
	private List<Color> colors;
}