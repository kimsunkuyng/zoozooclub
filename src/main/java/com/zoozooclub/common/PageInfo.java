package com.zoozooclub.common;

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

public class PageInfo {
	private int currentPage; //현재페이지번호
	private int listCount; // 전체글개수
	private int pageLimit; //페이지 하단에 보일 페이지 번호 개수
	private int maxPage; // 총 페이지 개수
	private int startPage;//페이지의 시작 번호 [1][2][3][4][5]// [6][7][8][9][10] 이런거
	private int endPage; //현재 보여지는 페이지 번호 그룹의 마지막 번호 [1][2][3][4][5] // [6][7][8][9][10] 일때 각각 5, 10 이런거
	private int boardLimit; //한페이지에 보여줄 게시글 개수

}
