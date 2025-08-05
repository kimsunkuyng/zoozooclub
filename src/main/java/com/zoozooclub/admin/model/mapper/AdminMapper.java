package com.zoozooclub.admin.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.zoozooclub.admin.model.vo.AdminBoard;
import com.zoozooclub.admin.model.vo.AdminCsBoard;
import com.zoozooclub.admin.model.vo.AdminMember;
import com.zoozooclub.admin.model.vo.AdminOrder;
import com.zoozooclub.admin.model.vo.AdminProduct;
import com.zoozooclub.admin.model.vo.Attachment;
import com.zoozooclub.admin.model.vo.BoardReport;
import com.zoozooclub.admin.model.vo.AdminCsReply;
import com.zoozooclub.board.model.vo.Board;

@Mapper
public interface AdminMapper {
    
	int getOrderCount();

	ArrayList<AdminOrder> selectOrderList(RowBounds rowBounds);

	AdminOrder selectOrder(int oId);
	
	int getProductCount();

	ArrayList<AdminProduct> selectProductList(RowBounds rowBounds);

	int enrollProduct(AdminProduct p);

	int deleteProduct(int pId);

	int getCsCount();

	ArrayList<AdminCsBoard> selectCsList(RowBounds rowBounds);

	AdminProduct selectProduct(int pId);

	int updateProduct(AdminProduct p);

	AdminCsBoard selectCs(int csNo);

	int enrollReply(AdminCsReply r);

	int updatePaymentDetail(AdminOrder o);

	int insertAttm(ArrayList<Attachment> list);

	ArrayList<Attachment> selectAttmList(Integer pId);

	int updateCsStatus(HashMap<String, Object> map);

	int deleteAttm(int pId);
	
	// ----------------------------------------
    
    //루강 전체회원수 조회
    int getMemberCount();
    //루강 회원리스트 조회
    ArrayList<AdminMember> selectMemberList(RowBounds rowBounds);
    //루강 회원등급 변경
    int updateMemberGrade(@Param("memberNo") int memberNo, @Param("grade") String grade);
    
    //루강 관리자 권한 변경
    int updateAdminStatus(@Param("memberNo") int memberNo, @Param("isAdmin") String isAdmin);
    
    //루강 회원삭제
    int deleteMember(int memberNo);
    
    //루강 회원이름으로 검색
    List<AdminMember> searchMember(String keyword);
    
    //루강 회원등급으로 검색
    List<AdminMember> searchMemberGrade(String keyword);
    
    //루강 회원등급관리리스트 조회
    ArrayList<AdminMember> selectmanageGrade(); 
    
    //루강 등급관리용 전체회원수
    int getManageGradeCount();
    
    //루강 등급관리 페이징 리스트
    ArrayList<AdminMember> selectmanageGrade(RowBounds rowBounds);
    
    
    //게시글 
    //루강 회원별 게시글수 조회
    int getMemberPostCount();
    //루강 회원별 게시글 리스트
    ArrayList<AdminBoard> selectMemberPostManagement(RowBounds rowBounds);
    
    //루강 회원 게시글 검색
    List<AdminBoard> searchMemberPosts(String keyword);
    
    //루강 공지글(게시글 등록)
    int insertNoticeBoard(Board board);
    
    //루강 공지글리스트
    ArrayList<Board> selectNoticeList();
    
    //루강 전체신고글 개수
    int getReportCount();
    
    //루강 신고글 리스트
    ArrayList<BoardReport> selectBoardReportList(RowBounds rowBounds);
   
    // 루강 신고게시글 검색+페이징
    int getReportSearchCount(@Param("keyword") String keyword);
   
    //루강 신고글 검색리스트(페이징)
    ArrayList<BoardReport> searchBoardReportList(@Param("keyword") String keyword, RowBounds rowBounds);
    
    //루강 신고글 삭제
    int deleteReport(int reportNo);
    
   
    
   

    
   

	
	
    
	
    
   
    






}
