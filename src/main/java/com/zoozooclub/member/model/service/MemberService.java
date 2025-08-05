package com.zoozooclub.member.model.service;

import com.zoozooclub.member.model.mapper.MemberMapper;
import com.zoozooclub.member.model.vo.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper mapper;

    public Member login(Member m) {
        return mapper.login(m);
    }

	public Member socialLogin(Member m) {
		return mapper.socialLogin(m);
	}

	public int updateMember(Member updatedUser) {
		return mapper.updateMember(updatedUser);
	}

	public int checkId(String memberId) {
		return mapper.checkId(memberId);
	}

	public int delete(Member m) {
		return mapper.delete(m);
		
	}

	public int emailCheck(String email) {
		return mapper.emailCheck(email);
	}


	
}
