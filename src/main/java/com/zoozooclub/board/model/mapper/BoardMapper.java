package com.zoozooclub.board.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.zoozooclub.board.model.vo.Board;
import com.zoozooclub.board.model.vo.Board_Reply;
import com.zoozooclub.board.model.vo.CsReply;



@Mapper
public interface BoardMapper {

	int getListcount(HashMap<String, String> map);

	ArrayList<Board> selectBoardList(RowBounds rowBounds, HashMap<String, String> map);

	int csinsertBoard(Board b);

	Board selectCsBoardDetail(int csNo);

	Integer checkCsPassword(@Param("csNo")int csNo, @Param("csPassword")int csPassword);


	CsReply selectCsReply(int csNo);

	int updateCsPost(Board b);

	Board selectBoardContent(int csNo);

	int deleteCsPost(Board b);

	int updateCsReply(CsReply cr);

	int csReplyInsert(CsReply cr);

	Board selectNoticeBoardDetail(int boardNo);

	Board_Reply selectNoticeReply(int boardNo);

	ArrayList<Board> selectBoardListIndex();



	

}
