package com.zoozooclub.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.zoozooclub.admin.model.mapper.AdminMapper;
import com.zoozooclub.admin.model.vo.AdminMember;
import com.zoozooclub.admin.model.vo.AdminOrder;
import com.zoozooclub.admin.model.vo.AdminProduct;
import com.zoozooclub.admin.model.vo.Attachment;
import com.zoozooclub.admin.model.vo.AdminBoard;
import com.zoozooclub.admin.model.vo.AdminCsBoard;
import com.zoozooclub.admin.model.vo.BoardReport;
import com.zoozooclub.admin.model.vo.AdminCsReply;
import com.zoozooclub.board.model.vo.Board;
import com.zoozooclub.common.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final AdminMapper mapper;

	public int getOrderCount() {
		return mapper.getOrderCount();
	}

	public ArrayList<AdminOrder> selectOrderList(PageInfo pi) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectOrderList(rowBounds);
		
	}
	
	public AdminOrder selectOrder(int oId) {
		AdminOrder o = mapper.selectOrder(oId);
		return o;
	}

	public int getProductCount() {
		return mapper.getProductCount();
	}

	public ArrayList<AdminProduct> selectProductList(PageInfo pi) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectProductList(rowBounds);
	}

	public int enrollProduct(AdminProduct p) {
		return mapper.enrollProduct(p);
	}

	public int deleteProduct(int pId) {
		return mapper.deleteProduct(pId);
	}

	public int getCsCount() {
		return mapper.getCsCount();
	}

	public ArrayList<AdminCsBoard> selectCsList(PageInfo pi) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectCsList(rowBounds);
	}

	public AdminProduct selectProduct(int pId) {
		AdminProduct p = mapper.selectProduct(pId);
		return p;
	}

	public int updateProduct(AdminProduct p) {
		return mapper.updateProduct(p);
	}

	public AdminCsBoard selectCs(int csNo) {
		AdminCsBoard c = mapper.selectCs(csNo);
		return c;
	}

	public int enrollReply(AdminCsReply r) {
		return mapper.enrollReply(r);
	}

	public int updatePaymentDetail(AdminOrder o) {
		return mapper.updatePaymentDetail(o);
	}

	public int insertAttm(ArrayList<Attachment> list) {
		return mapper.insertAttm(list);
	}

	public ArrayList<Attachment> selectAttmList(Integer pId) {
		return mapper.selectAttmList(pId);
	}

	public int updateCsStatus(HashMap<String, Object> map) {
		return mapper.updateCsStatus(map);
	}

	public int deleteAttm(int pId) {
		return mapper.deleteAttm(pId);
	}

	// ----------------------------------------------------------
	
    
    //루강 회원수조회
    public int getMemberCount() { return mapper.getMemberCount(); }
    
    //루강회원리스트(페이징조회)
    public ArrayList<AdminMember> selectMemberList(PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return mapper.selectMemberList(rowBounds);
    }
    
   
    
    //루강 회원등급 검색
    public List<AdminMember> searchMemberGrade(String keyword) { return mapper.searchMemberGrade(keyword); }
    
    //루강 회원등급 변경
    public int updateMemberGrade(int memberNo, String grade) { return mapper.updateMemberGrade(memberNo, grade); }
    
    //루강 관리자상태변경
    public int updateAdminStatus(int memberNo, String isAdmin) { return mapper.updateAdminStatus(memberNo, isAdmin); }
    //루강 회원삭제 회원탈퇴
    public int deleteMember(int memberNo) { return mapper.deleteMember(memberNo); }
    
    //루강 회원이름 검색
    public List<AdminMember> searchMember(String keyword) { return mapper.searchMember(keyword); }
    
    //회원 등급 리스트 조회
    public ArrayList<AdminMember> selectmanageGrade() {
        return mapper.selectmanageGrade();
    }
    
    //루강 회원 등급관리 페이징 리스트
    public ArrayList<AdminMember> selectmanageGrade(PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return mapper.selectmanageGrade(rowBounds);
    }
    
    //루강 회원별 게시글 수 조회
    public int getMemberPostCount() { return mapper.getMemberPostCount(); }
    
    //룩아 게시글 관리 리스트
    public ArrayList<AdminBoard> selectMemberPostManagement(PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return mapper.selectMemberPostManagement(rowBounds);
    }
    
    //루강 회원 게시글 검색
    public List<AdminBoard> searchMemberPosts(String keyword) { return mapper.searchMemberPosts(keyword); }
    
    //루강 공지글 등록
    public int insertNoticeBoard(Board board) { return mapper.insertNoticeBoard(board); }
    
    //루강  공지글(board 테이블에서 카테고리 2 + isnotice=Y만) 리스트 조회
    public ArrayList<Board> selectNoticeList() {
        return mapper.selectNoticeList();
    }
    
    //루강 전체 신고글 개수
    public int getReportCount() { return mapper.getReportCount(); }
    
    //루강 신고글 리스트(페이징)
    public ArrayList<BoardReport> selectBoardReportList(PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return mapper.selectBoardReportList(rowBounds);
    }
    
    //루강 신고게시글 검색
    public int getReportSearchCount(String keyword) { return mapper.getReportSearchCount(keyword); }
    
    //루강 신고글 검색 리스트(페이징)
    public ArrayList<BoardReport> searchBoardReportList(String keyword, PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return mapper.searchBoardReportList(keyword, rowBounds);
    }
    
    //루강 신고글 삭제
    public int deleteReport(int reportNo) { return mapper.deleteReport(reportNo); }
    
    
    
    
    
    
    
   

   
    
    
	
    
   

	
    
    public int getManageGradeCount() {
        return mapper.getManageGradeCount();
    }
    

    
   
    
    
}
