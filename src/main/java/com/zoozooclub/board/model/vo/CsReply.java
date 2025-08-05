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
public class CsReply {
	private int csReplyNo;          
    private Date csReplyDate;       
    private String csReplyContent;  
    private int csNo;               
    private String memberNo;  
    private int refCsboardId;
}
