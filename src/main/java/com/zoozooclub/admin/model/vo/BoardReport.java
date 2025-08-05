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
public class BoardReport {
    private int reportNo;           // 신고 번호 (PK)
    private int boardNo;            // 신고된 게시글 번호 (FK)
    private String reporterMemberNo;// 신고자 회원번호 (FK)
    private Date reportDate;        // 신고 일자
    private String reportReason;    // 신고 사유
    private String boardTitle;    // 게시글 제목
    private String reporterName;  // 신고자 이름(=
    
    private String isDelete;
}
