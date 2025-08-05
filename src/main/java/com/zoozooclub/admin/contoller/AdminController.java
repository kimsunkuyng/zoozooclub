package com.zoozooclub.admin.contoller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zoozooclub.admin.model.exception.AdminException;
import com.zoozooclub.admin.model.service.AdminService;
import com.zoozooclub.admin.model.vo.AdminBoard;
import com.zoozooclub.admin.model.vo.AdminCsBoard;
import com.zoozooclub.admin.model.vo.AdminMember;
import com.zoozooclub.admin.model.vo.AdminOrder;
import com.zoozooclub.admin.model.vo.AdminProduct;
import com.zoozooclub.admin.model.vo.BoardReport;
import com.zoozooclub.admin.model.vo.AdminCsReply;
import com.zoozooclub.board.model.vo.Board;
import com.zoozooclub.common.PageInfo;
import com.zoozooclub.common.Pagination;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.zoozooclub.admin.model.vo.Attachment;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService aService;

 // 결제(주문)관리 리스트
    @GetMapping("/paymentList")
    public String paymentList(@RequestParam(value="page", defaultValue="1") int currentPage, 
    							Model model, HttpServletRequest request) {
    	int listCount = aService.getOrderCount();
    	
    	PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
    	
    	ArrayList<AdminOrder> list = aService.selectOrderList(pi);
    	
    	model.addAttribute("list", list);
    	model.addAttribute("pi", pi);
    	model.addAttribute("loc", request.getRequestURI());

        return "paymentList";
    }
    
    @GetMapping("/order/{id}/{page}")
    public String selectOrder(@PathVariable("id") int oId, @PathVariable("page") int page, Model model) {
    AdminOrder o = aService.selectOrder(oId);
	    	if(o != null) {
	    		model.addAttribute("o", o);
	    		model.addAttribute("page", page);
	    		return "paymentDetail";
	    	} else {
	    		throw new AdminException("주문 상세보기를 실패하였습니다.");
	    	}
    }
    
    @PostMapping("updatePaymentDetail")
    public String updatePaymentDetail(@ModelAttribute AdminOrder o) {
    	
    	int result = aService.updatePaymentDetail(o);
    	if(result > 0) {
    		return "redirect:/admin/paymentList";
    	} else {
    		throw new AdminException("주문 수정을 실패하였습니다.");
    	}
    }
    
    
    @GetMapping("enrollProduct")
    public String enrollProduct() {
    	return "enrollProduct";
    }
  
    @PostMapping("/enrollProduct")
    public String enrollProduct(@ModelAttribute AdminProduct p, @RequestParam("file") ArrayList<MultipartFile> files) {
    	ArrayList<Attachment> list = new ArrayList<Attachment>();
    	for (int i = 0; i < files.size(); i++) {
    		MultipartFile upload = files.get(i);
    		// System.out.println(upload.getOriginalFilename()); // 업로드한 파일의 원본 이름 리턴 : 파일을 넣은 경우 파일 이름, 파일을 넣지 않은 경우 ""
    		if(!upload.getOriginalFilename().equals("")) {
    			String[] returnArr = saveFile(upload); // saveFile() : 파일 리네임 및 파일 저장소에 파일 저장하는 메소드
    			if(returnArr[1] != null) {
    				Attachment a = new Attachment();
    				a.setOriginalName(upload.getOriginalFilename());
    				a.setRenameName(returnArr[1]);
    				a.setAttmPath(returnArr[0]);
    				
    				list.add(a);
    			}
    		}
		}
    	
    	int result1 = aService.enrollProduct(p);
    	
    	for(Attachment a : list) {
    		a.setRefProductNo(p.getProductNo());
    	}
    	int result2 = aService.insertAttm(list);
    	
    	if(result1 + result2 == list.size() + 1) {
    		return "redirect:/admin/productList";
    	} else {
    		for(Attachment a : list) {
    			deleteFile(a.getRenameName());
    		}
    		throw new AdminException("상품 등록을 실패하였습니다.");
    	}
//    	int result = aService.enrollProduct(p);
//    	
//	    	if(result > 0) {
//	    		return "redirect:/admin/productList";
//	    	} else {
//	    		throw new AdminException("상품 등록 실패");
//	    	}
    }
    
    public void deleteFile(String renameName) {
    	String savePath = "c:\\uploadFiles";
    	
    	File f = new File(savePath + "\\" + renameName);
    	if(f.exists()) {
    		f.delete();
    	}
    }

    public String[] saveFile(MultipartFile upload) {
    	String savePath = "c:\\uploadFiles2"; // 외부에 저장하는게 권장사항때문에 바람직하다
    	
    	File folder = new File(savePath);
    	if(!folder.exists()) {
    		folder.mkdirs();
    	}
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	int ranNum = (int)(Math.random()*100000);
    	String originFileName = upload.getOriginalFilename();
    	// 시간으로 rename 
    	String renameFileName = sdf.format(new Date()) + ranNum + originFileName.substring(originFileName.lastIndexOf("."));
    	
    	String renamePath = folder + "\\" + renameFileName;
    	try {
			upload.transferTo(new File(renamePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	String[] returnArr = new String[2];
    	returnArr[0] = savePath;
    	returnArr[1] = renameFileName;
    	
    	return returnArr;
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute AdminProduct p, @RequestParam(value="page", defaultValue="1") int page) {
    	
    	int result = aService.updateProduct(p);
	    	if(result > 0) {
	    		return "redirect:/admin/productList";
	    	} else {
	    		throw new AdminException("게시글 수정을 실패했습니다.");
	    	}
    }
    
    @PostMapping("deleteProduct")
    public String deleteProduct(@RequestParam("productNo") int pId) {
    	int result1 = aService.deleteAttm(pId);
    	int result2 = aService.deleteProduct(pId);
	    	if(result1 > 0 && result2 > 0) {
	    		return "redirect:/admin/productList";
	    	} else {
	    		throw new AdminException("상품 삭제를 실패했습니다.");
	    	}
    }
    
    // 상품 관리 리스트
    @GetMapping("/productList")
    public String productList(@RequestParam(value="page", defaultValue="1") int currentPage, 
			Model model, HttpServletRequest request) {
    	
    	int listCount = aService.getProductCount();
    	
    	PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 4);
    	
    	ArrayList<AdminProduct> list = aService.selectProductList(pi);
    	ArrayList<Attachment> alist = aService.selectAttmList(null);
    			
    	model.addAttribute("alist", alist);
    	model.addAttribute("list", list);
    	model.addAttribute("pi", pi);
    	model.addAttribute("loc", request.getRequestURI());

        return "/productList";
    }
    

    @GetMapping("/product/{id}/{page}")
    public String selectProduct(@PathVariable("id") int pId, @PathVariable("page") int page, Model model) {
    	AdminProduct p = aService.selectProduct(pId);
    	
    	ArrayList<Attachment> alist = aService.selectAttmList(pId);
    	
    	if(p != null) {
    		model.addAttribute("p", p);
    		model.addAttribute("page", page);
    		model.addAttribute("alist", alist);
    		return "updateProduct";
    	} else {
    		throw new AdminException("상품 상세보기를 실패하였습니다.");
    	}
    }

    // 고객문의 관리
    @GetMapping("/csList")
    public String csList(@RequestParam(value="page", defaultValue="1") int currentPage, 
			Model model, HttpServletRequest request) {
    	
    	int listCount = aService.getCsCount();
    	
    	PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
    	
    	ArrayList<AdminCsBoard> list = aService.selectCsList(pi);
    	
    	model.addAttribute("list", list);
    	model.addAttribute("pi", pi);
    	model.addAttribute("loc", request.getRequestURI());
    	
        return "/csList";
    }
    
    @GetMapping("/cs/{id}/{page}")
    public String selectCs(@PathVariable("id") int csNo, @PathVariable("page") int page, Model model) {
    	AdminCsBoard c = aService.selectCs(csNo);
    	System.out.println(c);
    	System.out.println(csNo);
	    	if(c != null) {
	    		model.addAttribute("c", c);
	    		model.addAttribute("page", page);
	    		return "csReply";
	    	} else {
	    		return "csReply";
	    	}
    }
    
    @PostMapping("csReply")
    public String enrollReply(@ModelAttribute AdminCsReply r, @RequestParam("csNo") int csNo, @RequestParam("csState") String csState) {
    	int result1 = aService.enrollReply(r);
    	
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("csNo", csNo);
    	map.put("csState", csState);
    	
    	int result2 = aService.updateCsStatus(map);
    	if(result1 > 0 && result2 > 0) {
    		return "redirect:/admin/csList";
    	} else {
    		throw new AdminException("답변 등록을 실패하였습니다.");
    	}
    	
    }

    @GetMapping("/faq")
    public String faqPage() {
        return "/faq";
    }

    // 쿠폰 관리
    @GetMapping("/eventEnroll")
    public String eventEnroll() {
        return "/eventEnroll";
    }

    @GetMapping("/createCoupon")
    public String createCoupon() {
        return "/createCoupon";
    }

    
 
    //루강 회원조회
    @GetMapping("/memberList")
    public String memberList(@RequestParam(value="page", defaultValue="1") int currentPage, Model model) {
        int listCount = aService.getMemberCount(); // 멤버 총 개수
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5); // 한 페이지에 5개씩
        ArrayList<AdminMember> list = aService.selectMemberList(pi);
        model.addAttribute("list", list);
        model.addAttribute("pi", pi);
        return "/memberList";
    }
    
    // 루강 회원조회검색
    @GetMapping("/searchMember")
    @ResponseBody
    public List<AdminMember> searchMember(@RequestParam("keyword") String keyword) {
        return aService.searchMember(keyword);
    }
    
    // 루강 회원 삭제 (다중 회원)
    @PostMapping("/deleteMembers")
    public String deleteMembers(
        @RequestParam(value = "memberNo") List<Integer> memberNos
    ) {
        for (int memberNo : memberNos) {
            aService.deleteMember(memberNo);
        }
        return "redirect:/admin/memberList";
    }
    
    

    // 루강 등급관리
    @GetMapping("/manageGrade")
    public String memberGrade(
        @RequestParam(value = "page", defaultValue = "1") int currentPage,
        Model model) {

        int listCount = aService.getManageGradeCount(); // 총 회원수
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5); // 한 페이지에 5명

        ArrayList<AdminMember> list = aService.selectmanageGrade(pi); // PageInfo 전달!
        model.addAttribute("list", list);
        model.addAttribute("pi", pi); // 페이징 정보도 전달

        return "/manageGrade";
    }

    // 루강 등업처리
    @PostMapping("/updateGrades")
    @ResponseBody
    public String updateGrades(@RequestBody List<Map<String, Object>> data) {
        
    	for(Map<String, Object> item : data) {
            int memberNo = Integer.parseInt(item.get("memberNo").toString());
            String grade = item.get("grade").toString();
            aService.updateMemberGrade(memberNo, grade);
        }
        return "success";
    }


   

    //루강 회원게시글관리(전체)
    @GetMapping("/memberPostManagement")
    public String memberPostManagement(
        @RequestParam(value = "page", defaultValue = "1") int currentPage,
        Model model) {
        int listCount = aService.getMemberPostCount(); // 게시글 전체 개수
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5); // 한 페이지에 5개씩
        ArrayList<AdminBoard> list = aService.selectMemberPostManagement(pi);
        model.addAttribute("list", list);
        model.addAttribute("pi", pi);
        return "memberPostManagement";
    }

    
    
    
    
    // 루강 신고게시글 관리 (검색/페이징)
    @GetMapping("/reportedPostManagement")
    public String reportedPostManagement(
            @RequestParam(value = "page", defaultValue = "1") int currentPage,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model,
            HttpServletRequest request) {

        int listCount;
        PageInfo pi;
        ArrayList<BoardReport> reportList;

        if (keyword != null && !keyword.trim().isEmpty()) {
            listCount = aService.getReportSearchCount(keyword);
            pi = Pagination.getPageInfo(currentPage, listCount, 5);
            reportList = aService.searchBoardReportList(keyword, pi);
        } else {
            listCount = aService.getReportCount();
            pi = Pagination.getPageInfo(currentPage, listCount, 5);
            reportList = aService.selectBoardReportList(pi);
        }
        model.addAttribute("reportList", reportList);
        model.addAttribute("pi", pi);
        model.addAttribute("keyword", keyword);
        model.addAttribute("loc", request.getRequestURI());
        return "reportedPostManagement";
    }

    //루강 관리자 상태
    @PostMapping("/updateAdminStatus")
    public String updateAdminStatus(
        @RequestParam(value = "memberNo") List<Integer> memberNos,
        @RequestParam(value = "isAdmin") List<String> isAdminList
    ) {
        for (int i = 0; i < memberNos.size(); i++) {
            aService.updateAdminStatus(memberNos.get(i), isAdminList.get(i));
        }
        return "redirect:/admin/memberList";
    }

   

    //루강 신고 게시글 삭제
    @PostMapping("/deleteReports")
    public String deleteReports(
        @RequestParam(value = "reportNos") List<Integer> reportNos,
        RedirectAttributes redirectAttributes 
    ) {
        for (int reportNo : reportNos) {
            aService.deleteReport(reportNo);
        }
        redirectAttributes.addFlashAttribute("msg", "삭제 완료되었습니다.");
        return "redirect:/admin/reportedPostManagement";
    }

    
    // 루강  공지사항 관리 페이지 - 초기진입/새 공지 등록(폼)
    @GetMapping("/noticeManagement")
    public String noticeManagement(Model model) {
        // 최초 진입 or 새 공지 등록 시 폼만 보여줌
        if (!model.containsAttribute("registered")) {
            model.addAttribute("registered", false);
        }
        return "/noticeManagement";
    }

    // 루강 공지 등록 처리 (등록 후엔 공지 목록으로!)
    @PostMapping("/noticeEnroll")
    public String noticeEnroll(@ModelAttribute Board board, RedirectAttributes ra) {
        board.setBoardCategory(2);           // 공지 카테고리
        board.setBoardIsnotice("Y");         // 공지글
        board.setMemberNo("2");              // 관리자(임시, 필요시 세션/로그인값으로)

        int result = aService.insertNoticeBoard(board);

        if(result > 0) {
            ra.addFlashAttribute("msg", "등록되었습니다!");
            ra.addFlashAttribute("registered", true); // 등록 성공 플래그
            ra.addFlashAttribute("noticeList", aService.selectNoticeList());
        } else {
            ra.addFlashAttribute("msg", "등록 실패");
            ra.addFlashAttribute("registered", false);
        }
        return "redirect:/admin/noticeManagement";
    }

  

    //루강 회원등급조회화면
    @GetMapping("/searchMemberGrade")
    @ResponseBody
    public List<AdminMember> searchMemberGrade(@RequestParam("keyword") String keyword) {
        return aService.searchMemberGrade(keyword);
    }

    //루강 회원게시물관리화면
    @GetMapping("/searchMemberPosts")
    @ResponseBody
    public List<AdminBoard> searchMemberPosts(@RequestParam("keyword") String keyword) {
        return aService.searchMemberPosts(keyword);
    }
    
    
    
    
    

}
