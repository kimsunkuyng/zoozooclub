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
public class AdminBoard {
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
	
	private String memName;

}
