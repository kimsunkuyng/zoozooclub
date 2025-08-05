package com.zoozooclub.member.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.zoozooclub.member.model.service.MemberService;
import com.zoozooclub.member.model.vo.Member;
import com.zoozooclub.pet.model.service.PetService;
import com.zoozooclub.pet.model.vo.Pet;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes("loginUser")
@RequestMapping("/member/")
public class MemberController {

	private final MemberService mService;
	private final PetService pService;
	
//	public MemberController(MemberService mService) {
//        this.mService = mService;
//    }
	


	@GetMapping("login")
    public String Login() {
        return "views/member/login"; // maps to templates/member/login.html
    }
	@PostMapping("login")
	public String login( Member m, HttpSession session, Model model) {
		Member loginUser = mService.login(m);
		
		if(loginUser !=null) {
			session.setAttribute("loginUser", loginUser);
			return "views/member/mypageTab";
		} else {
			model.addAttribute("msg", "로그인 실패");
			return "views/member/login";
		}
	}
	@GetMapping("/oauth2login")
	public String oauth2LoginSuccess(OAuth2AuthenticationToken token, HttpSession session) {
	    
	    String provider = token.getAuthorizedClientRegistrationId();
	    OAuth2User user = token.getPrincipal();
	    
	    String email = "";
	    String name = "";
	   
	    if ("google".equals(provider)) {
	        email = user.getAttribute("email");
	        name = user.getAttribute("name");
	    } else if ("naver".equals(provider)) {
	        Map<String, Object> response = user.getAttribute("response");
	        email = (String) response.get("email");
	        name = (String) response.get("name");
	    } else if ("kakao".equals(provider)) {
	        Map<String, Object> kakaoAccount = (Map<String, Object>) user.getAttribute("kakao_account");

	        // 1. profile 안에 있는 name
	        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
	        String nameInProfile = (String) profile.get("name");  // 잘 안 나오는 경우가 많음

	        // 2. account 레벨의 name
	        name = (String) kakaoAccount.get("name");  
	        email = (String) kakaoAccount.get("email");
	        
	    } if ("facebook".equals(provider)) {
	        email = user.getAttribute("email");
	        name = user.getAttribute("name");
	    }
	    

	    System.out.println("✔️ Email: " + email);
	    System.out.println("✔️ Name: " + name);
	    
	   
	    
	    Member m = new Member();
	     m.setEmail(email);
	     m.setMemName(name);

	    Member loginUser = mService.socialLogin(m);
	    if(loginUser !=null) {
			session.setAttribute("loginUser", loginUser);
			return "views/member/mypageTab";
		} else {
			return "views/member/login";
		}

	}
    
//	@GetMapping("/oauth2login")
//	public String oauth2LoginSuccess(OAuth2AuthenticationToken token, HttpSession session) {
//	    OAuth2User user = token.getPrincipal();
//	    System.out.println(" Authenticated via: " + token.getAuthorizedClientRegistrationId());
//	    System.out.println(" OAuth2User attributes:");
//	    user.getAttributes().forEach((key, value) -> {
//	        System.out.println(key + " : " + value);
//	    });
//
//	    return "redirect:/member/login";
//	}
	
//	@GetMapping("/oauth2login")
//	public String oauth2LoginSuccess(OAuth2AuthenticationToken token, HttpSession session) {
//	    OAuth2User user = token.getPrincipal();
//
//	    // Naver는 사용자 정보가 "response" 안에 들어 있음
//	    Map<String, Object> response = (Map<String, Object>) user.getAttributes().get("response");
//
//	    System.out.println("✅ Naver response:");
//	    response.forEach((key, value) -> {
//	        System.out.println(key + " : " + value);
//	    });
//
//	    return "redirect:/";
//	}

//	@GetMapping("/oauth2login")
//	public String oauth2LoginSuccess(OAuth2AuthenticationToken token, HttpSession session) {
//	    OAuth2User user = token.getPrincipal();
//
//	    System.out.println("✅ Provider: " + token.getAuthorizedClientRegistrationId());
//	    System.out.println("✅ Raw attributes from Kakao:");
//	    user.getAttributes().forEach((key, value) -> {
//	        System.out.println("🔑 " + key + " : " + value);
//	    });
//
//	    return "redirect:/member/login";
//	}
	
//	@GetMapping("/oauth2login")
//	public String loginSuccess(OAuth2AuthenticationToken authentication) {
//	    OAuth2User user = authentication.getPrincipal();
//	    System.out.println("Facebook user: " + user.getAttributes());
//	    return "redirect:/";
//	}

    
    @GetMapping("mypage")
    public String showMyPage(@SessionAttribute("loginUser") Member loginUser, Model model) {
    	model.addAttribute("loginUser", loginUser);
    	 
        return "views/member/mypageTab"; // maps to templates/member/login.html
    }
    
    @GetMapping("signup")
    public String singup() {
        return "views/member/signup"; // maps to templates/member/login.html
    }
    
    @GetMapping("profile")
    public String profile(@SessionAttribute("loginUser") Member loginUser, Model model) {
        ArrayList<Pet> pList = pService.petList(loginUser.getMemberNo());
        model.addAttribute("petList", pList);
    	return "views/member/profile";  // path inside /templates/
    }
    @GetMapping("myInfoUpdate")
    public String myInfoUpdate() {
    	return "views/member/memberInfoUpdate";
    }
     
    @PostMapping("update")
    public String myInfoUpdate(@ModelAttribute("loginUser") Member updatedUser, Model model) {
    	mService.updateMember(updatedUser);
    	return "views/member/mypageTab";
    }
    @GetMapping("delete")
    public String deleteMember(@SessionAttribute("loginUser") Member loginUser, SessionStatus status, HttpSession session) {
    	int result =mService.delete(loginUser);
    	System.out.println(loginUser);
    	if (result >0) {
    		return logout(status, session);
    	} else return "views/member/mypageTab";
    }
    @GetMapping("couponPoint")
    public String couponPoint() {
        return "views/member/couponPoint";
    }

    @GetMapping("orderhistory")
    public String orderhistory() {
        return "views/member/orderhistory";
    }

    @GetMapping("viewer")
    public String viewer() {
        return "views/member/viewer";
    }

    @GetMapping("myactivity")
    public String activity() {
        return "views/member/myactivity";
    }

    @GetMapping("setting")
    public String setting() {
        return "views/member/setting";
    }
    

    @GetMapping("review")
    public String review() {
        return "views/member/review";
    }
    
    @GetMapping("logout")
    public String logout(SessionStatus status, HttpSession session) {
    	 System.out.println("🚪 Logging out...");
        status.setComplete();       // clears @SessionAttributes
        session.invalidate();       // clears HttpSession
        return "redirect:/shopping/list";        // goes to /
    }
    
    @GetMapping("checkId")
    @ResponseBody
    public String checkId(@RequestParam("memberId") String memberId, @SessionAttribute("loginUser") Member m) {

        if (memberId == null || memberId.trim().isEmpty()) {
            return "EMPTY";
        }
    	int result = mService.checkId(memberId);
    	return (result>0) ? "DUPLICATE" : "OK";
    	
    }
    @GetMapping("newIdCheck")
    @ResponseBody
    public String newId(@RequestParam("memberId") String memberId) {

        if (memberId == null || memberId.trim().isEmpty()) {
            return "EMPTY";
        }
    	int result = mService.checkId(memberId);
    	return (result>0) ? "DUPLICATE" : "OK";
    	
    }
    @GetMapping("newEmailCheck")
    @ResponseBody
    public String newEmailCheck(@RequestParam("email") String email) {

        if (email == null || email.trim().isEmpty()) {
            return "EMPTY";
        }
    	int result = mService.emailCheck(email);
    	return (result>0) ? "DUPLICATE" : "OK";
    }
}


