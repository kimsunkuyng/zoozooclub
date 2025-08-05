package com.zoozooclub.common;

public class Pagination {
	public static PageInfo getPageInfo(int currentPage, int listCount, int boardLimit) { // 현재 페이지 번호, 전체 게시물 수, 한 페이지에 보여줄 게시물 수
		int pageLimit = 10; // 한 페이지에 10개씩
		int maxPage = (int)Math.ceil((double)listCount / boardLimit); // 총페이지수 = 전체 게시물 수 ÷ 한 페이지 게시물 수 (정수 패이지수만 저장)
		int startPage = (currentPage - 1) /pageLimit * pageLimit + 1; // 현재 페이지가 속한 시작페이지 = (현재 페이지 - 1) ÷ 10 * 10 + 1 , 예시) (15 - 1) ÷ 10 * 10 + 1 = 1 * 10 + 1 = 11
		int endPage = startPage + pageLimit - 1; // 현재 페이지가 속한 마지막 페이지 = (시작 페이지 + 페이지 개수 - 1) , 예시) 11 + 10 - 1 = 20
		// 즉 15 페이지일 땐 11 ~ 20까지의 페이지 번호를 보여줌
		if(maxPage < endPage) { // 총 페이지 수 보다 마지막 페이지가 더 크다면
			endPage = maxPage; // 마지막 페이지 = 총 페이지 수
		}
		// 				   현재 페이지     전체게시물수  한페이지당개수 총페이지수 시작페이지번호 마지막페이지번호 한페이지에보여줄게시물 수
		return new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
	}
}
