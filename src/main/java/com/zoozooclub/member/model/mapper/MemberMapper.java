package com.zoozooclub.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zoozooclub.member.model.vo.Member;

@Mapper
public interface MemberMapper {

	Member login(Member m);

	Member socialLogin(Member m);

	int updateMember(Member updatedUser);

	int checkId(String memberId);

	int delete(Member m);

	int emailCheck(String email);

}
