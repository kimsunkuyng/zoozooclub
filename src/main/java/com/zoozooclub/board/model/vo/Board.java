package com.zoozooclub.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Board {
	private int boardCategory;
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private Date boardDate;
	private int boardReadCount;
	private String boardPublic;
	private String memberNo;
	private String boardIsnotice;
	private String boardImage;
	private String isDelete;
	
	//csboard필드
	private int csNo;
	private String csTitle;
	private String csContent;
	private Date csDate;
	private String csIsDelete;
	private String csSecret;
	private Integer csPassword;
	private String csImage;
	private String csState;
	private int productNo;
	
	//member에서 이름 가져옴
	private String memName;
}
