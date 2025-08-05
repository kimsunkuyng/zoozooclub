package com.zoozooclub.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.zoozooclub.board.model.mapper.BoardMapper;
import com.zoozooclub.board.model.vo.Board;
import com.zoozooclub.board.model.vo.Board_Reply;
import com.zoozooclub.board.model.vo.CsReply;
import com.zoozooclub.common.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardMapper mapper;
	
	public int getListCount(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return mapper.getListcount(map);
		
	}

	public ArrayList<Board> selectBoardList(PageInfo pi, HashMap<String, String> map) {
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset,pi.getBoardLimit());
		return mapper.selectBoardList(rowBounds,map);
	}

	public int csinsertBoard(Board b) {
		// TODO Auto-generated method stub
		return mapper.csinsertBoard(b);
	}

	public Board selectCsBoardDetail(int csNo) {
		// TODO Auto-generated method stub
		return mapper.selectCsBoardDetail(csNo);
	}

	public int checkCsPassword(int csNo, int csPassword) {
		Integer result = mapper.checkCsPassword(csNo, csPassword);
	    return result != null ? result : 0;
	}


	public CsReply selectCsReply(int csNo) {
		// TODO Auto-generated method stub
		return mapper.selectCsReply(csNo);
	}

	public int updateCsPost(Board b) {
		// TODO Auto-generated method stub
		return mapper.updateCsPost(b);
	}

	public Board selectBoardContent(int csNo) {
		// TODO Auto-generated method stub
		return mapper.selectBoardContent(csNo);
	}

	public int deleteCsPost(Board b) {
		// TODO Auto-generated method stub
		return mapper.deleteCsPost(b);
	}

	public int updateCsReply(CsReply cr) {
		// TODO Auto-generated method stub
		return mapper.updateCsReply(cr);
	}

	public int csReplyInsert(CsReply cr) {
		// TODO Auto-generated method stub
		return mapper.csReplyInsert(cr);
	}

	public Board selectNoticeBoardDetail(int boardNo) {
		// TODO Auto-generated method stub
		return mapper.selectNoticeBoardDetail(boardNo);
	}

	public Board_Reply selectNoticeReply(int boardNo) {
		// TODO Auto-generated method stub
		return mapper.selectNoticeReply(boardNo);
	}

	public ArrayList<Board> selectBoardListIndex() {
		return mapper.selectBoardListIndex();
	}


	
	

	

}
