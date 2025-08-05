package com.zoozooclub.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zoozooclub.member.model.service.SmsService;
import com.zoozooclub.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;

@RestController
public class SmsController {

    private final SmsService smsService;
    
    private String verificationCode;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("send")
    public ResponseEntity<String> sendSms(@RequestBody  Map<String, String> payload) {
    	String phone = payload.get("phone");
    	System.out.println(phone);
    	
        verificationCode = generateRandomCode();  // e.g., "847293"
        
        System.out.println(verificationCode);
        
        //smsService.sendVerificationCode(phone, verificationCode); // 실제로 문자 발송을 쓰려면 이라인 활성화 시키면 됨.
        
        return ResponseEntity.ok("SENT");
        
    }

    private String generateRandomCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
    
    @PostMapping("verify")
    public String verifyCode(@RequestBody Map<String, String> payload) {
        String verifyNum = payload.get("verifyNum");

        // Do your verification logic here...
        if (verificationCode.equals(verifyNum)) {
            return "Ok";
        } else {
            return "Fail";
        }
    }
    @PostMapping("verifyUpdate")
    public void updatePhoneVerify(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser != null) {
            loginUser.setPhoneVerify("Y");    
        }
    }
}
