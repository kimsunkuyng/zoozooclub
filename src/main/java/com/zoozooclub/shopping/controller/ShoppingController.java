package com.zoozooclub.shopping.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zoozooclub.common.PageInfo;
import com.zoozooclub.common.Pagination;
import com.zoozooclub.member.model.vo.Member;
import com.zoozooclub.shopping.model.service.ShoppingService;
import com.zoozooclub.shopping.model.vo.Cart;
import com.zoozooclub.shopping.model.vo.Coupon;
import com.zoozooclub.shopping.model.vo.OrderDetail;
import com.zoozooclub.shopping.model.vo.Orders;
import com.zoozooclub.shopping.model.vo.ProDetail;
import com.zoozooclub.shopping.model.vo.Product;
import com.zoozooclub.shopping.model.vo.Review;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/shopping")
@RequiredArgsConstructor
public class ShoppingController {

    private final SecurityFilterChain securityFilterChain;
	
	private final ShoppingService sService;
	// 이 클래스 안에서만 쓸 수 있고, 한 번 주입되면 바꿀 수 없는 ShoppingService 객체
	
	// 쇼핑몰 목록
	@GetMapping("/list")
	public String productList(@RequestParam(value="keyword", required = false) String keyword, 
	                          @RequestParam(value="page", defaultValue="1") int currentPage, 
	                          Model model, 
	                          HttpServletRequest request) { 
		
		// 검색어 기준으로 상품 개수 count
	    int listCount = (keyword != null && !keyword.isEmpty()) 
	                    ? sService.getSearchCount(keyword) 
	                    : sService.getProductCount(); 

	    // 페이징 처리
	    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10); 

	    List<Product> list = (keyword != null && !keyword.isEmpty())
	                         ? sService.searchProductList(keyword, pi) 
	                         : sService.selectList(pi); 
	    
	    // 검색어 기준으로 상품 리스트 select
	    ArrayList<Integer> rCountList = new ArrayList<>(); 
	    ArrayList<Double> rAvgList = new ArrayList<>(); 
	    
	    // 각 상품 마다의 평점과 리뷰 개수 
	    for (Product p : list) { 
	        int rCount = sService.countReivew(p.getProductNo()); 
	        Double rAvg = sService.reviewAvg(p.getProductNo()); 
	        rCountList.add(rCount); 
	        rAvgList.add(rAvg != null ? rAvg : 0.0); 
	    }
	    
	    model.addAttribute("pList", list);
	    model.addAttribute("pi", pi); 
	    model.addAttribute("keyword", keyword); 
	    model.addAttribute("loc", request.getRequestURI()); 
	    model.addAttribute("rAvgList", rAvgList); 
	    model.addAttribute("rCountList", rCountList); 

	    return "views/shopping/shopping";
	}


	// 상품 상세페이지
	@GetMapping("detail")
	public String detail(@RequestParam("pNo") int pNo, Model model) {
		// 한 개의 상품 정보 select
		Product p = sService.selectProduct(pNo);
		
		// 리뷰 개수 count
		int rCount = sService.countReivew(pNo);
		
		// 리뷰 평점 select
		Double rAvg = sService.reviewAvg(pNo);
		
		// view로 전달
		model.addAttribute("rAvg",rAvg);
		model.addAttribute("p",p);
		model.addAttribute("rCount", rCount);
		return "/views/shopping/shopping_detail";
	}
	

	// 장바구니 리스트 
	@GetMapping("cart")
	public String cart(Model model, HttpSession session) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		// 로그인
		if(loginUser != null) {

			String memberNo = loginUser.getMemberNo();
			
			// 로그인한 회원이 담은 장바구니 리스트 select
			ArrayList<Cart> cList = sService.selectCart(memberNo);
			
			model.addAttribute("cList", cList);
			
			return "/views/shopping/cart";
		  
		// 비로그인	
		} else {
			return "redirect:/";
		}
		
	}
	
	// 장바구니 추가 -> 쇼핑몰 리스트로 이동 
	@GetMapping("addList")
	public String detailToList(@RequestParam("amount") int amount, 
							   @RequestParam("pNo") int pNo,
							   @RequestParam("totalPrice") double totalPrice,
							   HttpSession session) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		int mNo = Integer.parseInt(loginUser.getMemberNo());
		
		Cart c = new Cart();
		
		c.setMemberNo(mNo);         // 회원번호
		c.setCartAmount(amount);    // 상품 수량
		c.setPId(pNo);              // 상품 번호
		c.setCartPrice(totalPrice); // 상품 총 가격
		
		// 장바구니 추가
		int result = sService.insertCart(c);
		
		if(result > 0) {
			return "redirect:/shopping/list";
		} else {
			return "redirect:/error/404";
		}
	}
	
	// 장바구니 추가 -> 장바구니로 이동
	@GetMapping("addCart")
	public String addCart(@RequestParam("amount") int amount, 
						  @RequestParam("pNo") int pNo,
						  @RequestParam("totalPrice") double totalPrice,
						  HttpSession session) {
		
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		int mNo = Integer.parseInt(loginUser.getMemberNo());
		
		Cart c = new Cart();
		
		c.setMemberNo(mNo);         // 회원번호
		c.setCartAmount(amount);    // 상품 수량
		c.setPId(pNo);              // 상품 번호
		c.setCartPrice(totalPrice); // 상품 총 가격
		
		// 장바구니 추가
		int result = sService.insertCart(c);
		
		if(result > 0) {
			return "redirect:/shopping/cart";
		} else {
			return "redirect:/error/404";
		}
	}
	
	@GetMapping("review")
	public String review(@RequestParam("pNo") int pNo, 
						 Model model, 
						 HttpSession session) {
		
		// 특정 상품에 대한 리뷰 select
		ArrayList<Review> rList = sService.selectReviewList(pNo);
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		// 리뷰 작성을 위한 productNo 전달
		model.addAttribute("pNo", pNo);
		model.addAttribute("rList", rList);
		
		if(loginUser != null) {
			
			HashMap<String,Object> map = new HashMap<String,Object>();
			
			map.put("productNo", pNo);
			map.put("memberNo", loginUser.getMemberNo());
			
			// 로그인 + 상품 구매 기록 -> 해당 내용의 리뷰가 없다면
			int result = sService.rBtnShow(map);
			if(result > 0) {
				model.addAttribute("result", result);
				System.out.println("@@@@@@@@@@@@@@@" + result);
			}
			
			return "/views/shopping/review";

		} else {
			model.addAttribute("rList", rList);
			return "/views/shopping/review";
		}
		
	}
	
	@PostMapping("insertReview")
	public String insertReview(RedirectAttributes rttr, 
							  Model model, 
				   			  HttpSession session, 
				   			  @ModelAttribute Review r, 
				   			  @RequestParam("pNo") int pNo) {

	    // 로그인 회원 확인
	    Member loginUser = (Member) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        return "redirect:/member/login";
	    }
	    
	    
	    r.setProductNo(pNo);
	    r.setMemberNo(Integer.parseInt(loginUser.getMemberNo()));
	    
	    System.out.println("@@@@@@@@@@@@REPLY : " + r);

	    Map<String, Object> map = new HashMap<>();
	    
	    map.put("memberNo", r.getMemberNo());
	    map.put("productNo", r.getProductNo());

	    Integer orderNo = sService.getOrderNo(map); 
	    
	    System.out.println("@@@@@@@@ORDERNO : " + orderNo);
	    
	    if (orderNo == null) {
	        rttr.addFlashAttribute("message", "상품을 구매하신 분만 리뷰 작성이 가능합니다.");
	        return "redirect:/error/404";
	    } else {
		    r.setOrderNo(orderNo); 
	    }

//	    // 3. 중복 리뷰 체크
//	    Integer duplicationReview = sService.duplicationReview(r);
//	    
//	    System.out.println("@@@@@@@DUPLICAITONREVIEW : " + duplicationReview);
//	    
//	    if (duplicationReview != null && duplicationReview > 0) {
//	        rttr.addFlashAttribute("message", "리뷰는 중복 등록이 불가합니다.");
//	        return "redirect:/error/404";
//	    }

	    // 4. 리뷰 등록
	    int result = sService.insertReview(r);
	    
	    System.out.println("@@@@@@@RESULT : " + result );
	    
	    if (result > 0) {
	        return "redirect:/shopping/review?pNo=" + r.getProductNo();
	    } else {
	        rttr.addFlashAttribute("message", "리뷰 등록에 실패하였습니다.");
	        return "redirect:/error/404";
	    }
	}

	
	
	@GetMapping("deleteCart")
	public String deleteCart(@RequestParam("cartNo") int cNo) {
		int result = sService.deleteCart(cNo);
		System.out.println("@@@@@@@@@@@RESULT: " + result);
		if(result > 0) {
			return "redirect:/shopping/cart";
		} else {
			return "redirect:/error/404";
		}
		
	}
	
	@GetMapping("pay")	
	public String pay(Model model, 
					  @RequestParam(value = "pNo", required = false, defaultValue = "0") int pNo,
					  @RequestParam(value = "amount", required = false, defaultValue = "0") int amount,
					  @RequestParam(value = "cartNos", required = false, defaultValue = "") String cartNos,
					  HttpSession session) {
		
		System.out.println("@@@@@@@PNO" + pNo);
		System.out.println("@@@@@@@CARTNOS IN PAY: " + cartNos);
		
		Product p = sService.selectProduct(pNo);
		
		model.addAttribute("p", p);
		model.addAttribute("pNo", pNo);
		model.addAttribute("amount", amount);
		model.addAttribute("cartNos", cartNos);
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		// 로그인 중이라면
		if(loginUser != null) {
			int memberNo = Integer.parseInt(loginUser.getMemberNo());
			
			// 회원의 쿠폰 리스트 조회
			ArrayList<Coupon> couponList =  sService.getCouponList(memberNo);
			model.addAttribute("couponList", couponList);
			
			return "views/shopping/pay";
		} else {
			return "views/member/login";
		}
		
	}
	
	@GetMapping("/confirm")
	public String confirmPaymentPage(
	        @RequestParam(value = "paymentKey", defaultValue = "") String paymentKey,
	        @RequestParam(value = "orderId", defaultValue = "") String orderId,
	        @RequestParam(value = "amount", defaultValue = "0") int amount,
	        @RequestParam(value = "address", defaultValue = "") String orderAddress,
	        @RequestParam(value = "pNo", defaultValue = "0") String pNo,
	        @RequestParam(value = "productAmount", defaultValue = "0") int productAmount,
	        @RequestParam(value = "cartNos", defaultValue = "") String cartNos,
	        Model model,
	        HttpSession session) {
		
		System.out.println("@@@@@@@@@CARTNOS : " + cartNos);
		
	    // 1. Toss 결제 승인
	    String SECRET_KEY = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
	    String encodedAuth = Base64.getEncoder()
	            .encodeToString((SECRET_KEY + ":").getBytes(StandardCharsets.UTF_8));

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "Basic " + encodedAuth);

	    Map<String, Object> body = Map.of(
	            "paymentKey", paymentKey,
	            "orderId", orderId,
	            "amount", amount
	    );

	    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
	    RestTemplate restTemplate = new RestTemplate();

	    try {
	        ResponseEntity<Map> response = restTemplate.postForEntity(
	                "https://api.tosspayments.com/v1/payments/confirm",
	                request,
	                Map.class
	        );

	        if (!response.getStatusCode().is2xxSuccessful()) {
	            model.addAttribute("error", "결제 승인 실패");
	            return "views/shopping/fail";
	        }

	        Map<String, Object> responseBody = response.getBody();
	        String payMethod = (String) responseBody.get("method");

	        Member loginUser = (Member) session.getAttribute("loginUser");
	        int memberNo = Integer.parseInt(loginUser.getMemberNo());
	        
	        // 장바구니 정보 저장
	        
	        // 주문 저장
	        Orders order = new Orders();
	        
	        order.setPayMethod(payMethod);
	        order.setMemberNo(memberNo);
	        order.setOrderAddress(orderAddress);
	        
	        // 주문 정보 저장
	        int orderResult = sService.insertOrders(order);
	        
	        if (orderResult > 0) {
	            System.out.println("@@@@@@ 주문 정보 저장 완료 @@@@@@");
	        } else {
	            System.out.println("@@@@@@ 주문 정보 저장 실패 @@@@@@");
	        }
	        
	        // 주문 상세정보 저장
	        int orderNo = sService.getOrderNoForOD(memberNo);
	        
	        int totalAmount = (Integer) responseBody.get("totalAmount");
	        System.out.println("결제 금액: " + totalAmount);
	        
	        OrderDetail od = new OrderDetail();
	        
	        od.setOrderNo(orderNo);
	        od.setProductAmount(productAmount);
	        od.setProductPrice(totalAmount);
	        od.setProductNo(Integer.parseInt(pNo));
	        
	        int orderDetailResult = sService.insertOrderDetail(od);
	        
	        if(orderDetailResult > 0) {
	        	System.out.println("@@@@@@ 주문 상세 정보 저장 완료 @@@@@@");
	        } else {
	        	System.out.println("@@@@@@ 주문 상세 정보 저장 실패 @@@@@@");
	        }
	        
	        // 상품 출고 저장
	        int parsedPNo = Integer.parseInt(pNo);
	        
	        if (parsedPNo != 0) {
	        	
	            ProDetail detail = new ProDetail();
	            
	            detail.setProductNo(parsedPNo);
	            detail.setAmount(productAmount);

	            int detailResult = sService.insertProDetail(detail);
	            if (detailResult > 0) {
	                System.out.println("@@@@@@ 입출고 정보 저장 완료 @@@@@@");
	            } else {
	                System.out.println("@@@@@@ 입출고 정보 저장 실패 @@@@@@");
	            }
	        }

	        // 장바구니 항목 처리
	        List<Integer> cartNoList = cartNos != null && !cartNos.isBlank()
	                ? Arrays.stream(cartNos.split(",")).map(Integer::parseInt).collect(Collectors.toList())
	                : List.of();

	        List<Cart> selectedCartList = List.of();
	        if (!cartNoList.isEmpty()) {
	            // ① 장바구니 항목 가져오기
	            selectedCartList = sService.getCartListByNos(cartNoList);
	            model.addAttribute("cartList", selectedCartList);

	            // ② 장바구니 비우기 (delete_state = 'Y')
	            for (int cartNo : cartNoList) {
	               int result = sService.deleteCart(cartNo);
	               if(result > 0) {
	            	   System.out.println("@@@@@@@@@@@@@ 장바구니 삭제 성공!");
	               }
	            }
	        }

	        model.addAttribute("totalPrice", totalAmount);
	        return "views/shopping/success";

	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("error", "서버 오류 발생");
	        return "views/shopping/fail";
	    }
	}



}
