package com.zoozooclub.admin.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AdminCsBoard {
	
	private int csNo;
	private String csTitle;
	private String csContent;
	private Date csDate;
	private String csIsdelete;
	private String csSecret;
	private int memberNo;
	private int password;
	private String csImage;
	private String csState;
	
	private String memName;
	private String csReplyContent;
}
