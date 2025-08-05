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
public class AdminCsReply {

	private int csReplyNo;
	private Date csReplyDate;
	private String csReplyContent;
	private int csNo;
	private int memberNo;
	
	private String memName;
}
