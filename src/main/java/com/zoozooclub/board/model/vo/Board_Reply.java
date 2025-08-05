package com.zoozooclub.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString

public class Board_Reply {
	private int boardReviewReplyNo;
	private Date boardReplyDate;
	private String boardReplyContent;
	private int boardNo;
	
	
	
}
