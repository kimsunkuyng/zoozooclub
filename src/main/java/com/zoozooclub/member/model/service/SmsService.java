package com.zoozooclub.member.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class SmsService {
	
	private final DefaultMessageService messageService;

    @Value("${coolsms.api-key}")
    private String apiKey;

    @Value("${coolsms.api-secret}")
    private String apiSecret;

    @Value("${coolsms.sender}")
    private String senderPhone;

    public SmsService(@Value("${coolsms.api-key}") String apiKey,
                      @Value("${coolsms.api-secret}") String apiSecret,
                      @Value("${coolsms.sender}") String senderPhone) {
        this.messageService = NurigoApp.INSTANCE.initialize(
            apiKey,
            apiSecret,
            "https://api.coolsms.co.kr"
        );
        this.senderPhone = senderPhone;
    }
   

    public void sendVerificationCode(String toPhoneNumber, String code) {
        Message message = new Message();
        message.setFrom(senderPhone);  // must be registered with CoolSMS
        message.setTo(toPhoneNumber);
        message.setText("zoozooClub 인증번호는 " + code + " 입니다.");

        messageService.sendOne(new SingleMessageSendingRequest(message));
    }
}

