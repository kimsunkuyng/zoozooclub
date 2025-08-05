package com.zoozooclub.board.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zoozooclub.board.model.exception.BoardException;
import com.zoozooclub.board.model.service.BoardService;
import com.zoozooclub.board.model.vo.Board;
import com.zoozooclub.board.model.vo.Board_Reply;
import com.zoozooclub.board.model.vo.CsReply;
import com.zoozooclub.common.PageInfo;
import com.zoozooclub.common.Pagination;
import com.zoozooclub.member.model.vo.Member;
import com.zoozooclub.shopping.model.service.ShoppingService;
import com.zoozooclub.shopping.model.vo.Product;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService bService;
	private final ShoppingService sService;
	
	

	@GetMapping({"/cslist","csListSearch","csSort"})
	public ModelAndView CsList(@RequestParam(value ="page", defaultValue="1")int currentPage,
								ModelAndView mv, HttpServletRequest request,
								@RequestParam(value="csSortType", defaultValue="latest")String csSortType,
								@RequestParam HashMap<String,String>map,
								HttpSession session) {
		
		Member loginUser=(Member)session.getAttribute("loginUser");
		String userName="";
		if(loginUser != null) {
			userName=loginUser.getMemName();
					
		}else {
			userName="____";
		}
		
		map.put("type","c");
		int listCount = bService.getListCount(map); // 같은 getListCount() 사용, c=cs / q=qna
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
		ArrayList<Board> cslist = bService.selectBoardList(pi,map);
		
		mv.addObject("cslist",cslist).addObject("pi",pi)
			.addObject("loc", request.getRequestURI()).setViewName("views/board/cslist");
		mv.addObject("map",map).addObject("loginUser",loginUser).addObject("userName",userName);
		return mv;
		

	}
	
	@GetMapping("cswrite")
	public String CsUpdateWrite(@RequestParam(value="csNo",required=false)Integer csNo,
								@RequestParam(value="pNo",required=false) Integer pNo,
						        Model model) {
		
		System.out.println("@@@@@@@@@@CSNO : " + csNo);
		
		if(csNo!=null) {
			Board b = bService.selectBoardContent(csNo);
			
			model.addAttribute("board",b);
			model.addAttribute("mode","update");
			
		} else if (pNo != null) {
			Product p = sService.selectProduct(pNo);
			System.out.println("@@@@@@@@@@@@@@@@PRODUCT : " + p);
			model.addAttribute("p", p);
			model.addAttribute("board", new Board());
			model.addAttribute("mode","insert");
			
		}
		else {
			model.addAttribute("board", new Board());
			model.addAttribute("mode","insert");
		}
		return "views/board/cswrite";
		
	}
	
	@PostMapping("csinsert")
	public String CsWrite(@ModelAttribute Board b,
						  @RequestParam(value="pNo",required=false) Integer pNo,
						  @RequestParam(value="pImg",required=false) String csImage,
						  HttpSession session,
						  Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		
	    if (loginUser == null) {
	        return "redirect:/login";
	    }
	    
	    System.out.println("@@@@@@@@@@@@CSIMG : " + csImage);
	    
	    b.setMemName(loginUser.getMemName());
	    b.setMemberNo(loginUser.getMemberNo());
	    b.setProductNo(pNo);
	    b.setCsImage(csImage);
	    
	    b.setCsIsDelete("N");
		
	    System.out.println("csTitle: " + b.getCsTitle());
	    System.out.println("csContent: " + b.getCsContent());
	    System.out.println("csSecret: " + b.getCsSecret());
	    System.out.println("csPassword: " + b.getCsPassword());
	    System.out.println("memberNo: " + b.getMemberNo());
	    
		int result = bService.csinsertBoard(b);
		if(result>0) {
			return "redirect:/board/cslist";
		}else {
			throw new BoardException("게시물 등록에 실패하였습니다");
		}
	}
	
	
	@PostMapping("csmain")
	public String ChangeCs(@ModelAttribute Board b,
						   @RequestParam("csMode") String csMode) {
		
		System.out.println("@@@@@@@@@@@@@@@@@@CSCONTENT : " + csMode);
		System.out.println("@@@@@@@@@@@@@@@@BOARD : " + b);
		switch(csMode) {
		case "updateCsPost":
			int result1=bService.updateCsPost(b);
			System.out.println("@@@@@@@@@@@@RESULT1 : " + result1);
			if(result1>0) {
				return"redirect:/board/csmain?csNo="+b.getCsNo();
			}else {
				throw new BoardException("수정실패");
			}
			
		case "deleteCsPost":
			int result2=bService.deleteCsPost(b);
			if(result2>0) {
				return "redirect:/board/cslist";
			}else {
				throw new BoardException("삭제실패");
			}
			
		
		default:
			throw new IllegalArgumentException("잘못된 요청입니다");
		}
		
		
	}
	
	@GetMapping("csmain")
	public String CsMain(@RequestParam("csNo")int csNo, Model model,@ModelAttribute CsReply cr) {
		
		Board b=bService.selectCsBoardDetail(csNo);
		System.out.println("@@@@@@@@@@@@@@@@BOARD : " + b);
		CsReply csreply=bService.selectCsReply(csNo);
		cr.setRefCsboardId(csNo);
		
		model.addAttribute("Board",b);
		model.addAttribute("cr",cr);
		model.addAttribute("csReply",csreply);

	

		 return "views/board/csmain";
		
	}
	@PostMapping("checkSecret")
	public String checkSecret(@RequestParam("csNo")int csNo, @RequestParam("csPassword")int csPassword,Model model) {
		int isCorrect=bService.checkCsPassword(csNo,csPassword);
		
		if(isCorrect>0) {
			return "redirect:/board/csmain?csNo="+csNo;
		}else {
			model.addAttribute("errorMsg","비밀번호가 틀렸습니다.").addAttribute("csNo",csNo);
			return "views/board/secretPage";
		}
	}
	
	@GetMapping("secretPage")
	public String SecretPage(@RequestParam("csNo")int csNo,Model model) {
		model.addAttribute("csNo",csNo);
		return "views/board/secretPage";
	}
	
	
	
	//-------------------------------------------------
	@GetMapping({"/noticelist","noticeListSearch","noticeSort"})
	public ModelAndView NoticeList(@RequestParam(value ="page", defaultValue="1")int currentPage,ModelAndView mv, 
									HttpServletRequest request,
									@RequestParam(value="noticeSortType", defaultValue="latest")String noticeSortType,
									@RequestParam HashMap<String,String>map,
									HttpSession session) {

		Member loginUser=(Member)session.getAttribute("loginUser");
		String userName="";
		
		if(loginUser != null) {
			userName=loginUser.getMemName();
		}else {
			userName="____";
		}
		
		map.put("type", "n");
		int listCount = bService.getListCount(map);
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
		ArrayList<Board> noticelist = bService.selectBoardList(pi,map);
		
		mv.addObject("noticelist",noticelist).addObject("pi",pi)
			.addObject("loc",request.getRequestURI()).setViewName("views/board/noticelist");
		mv.addObject("map",map).addObject("loginUser",loginUser).addObject("userName",userName);
		return mv;
	}
	
	
	@GetMapping("noticemain")
	public String NoticeMain(@RequestParam("boardNo")int boardNo,Model model, @ModelAttribute Board_Reply br) {
		
		Board n=bService.selectNoticeBoardDetail(boardNo);
		Board_Reply nr=bService.selectNoticeReply(boardNo);
		
		br.setBoardNo(boardNo);
		
		model.addAttribute("notice",n).addAttribute("br",br).addAttribute("nr", nr);
		
		return "views/board/noticemain";
	}
	@GetMapping("noticewrite")
	public String NoticeWrite() {
		return "views/board/noticewrite";
	}
	
	@GetMapping({"/Q&A","QnASearch"})
	public ModelAndView QnA(@RequestParam(value ="page", defaultValue="1")int currentPage,ModelAndView mv, 
							HttpServletRequest request,
							@RequestParam HashMap<String,String>map) {
		 
			map.put("type", "q");
			int listCount = bService.getListCount(map); // 같은 getListCount() 사용, c=cs / q=qna
			PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
			ArrayList<Board> QnA = bService.selectBoardList(pi,map);
			
			mv.addObject("Q&A",QnA).addObject("pi",pi)
				.addObject("loc", request.getRequestURI()).setViewName("views/board/Q&A");
			mv.addObject("map",map);
			return mv;
		
	}
	@GetMapping("Q&Amain")
	public String QnAmain() {
		return "views/board/Q&Amain";
	}
	@GetMapping("Q&Awrite")
	public String QnAwrite() {
		return "views/board/Q&Awrite";
	}
	@GetMapping("report")
	public String Report() {
		return "views/board/report";
	}
	
	
}

