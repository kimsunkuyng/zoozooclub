package com.zoozooclub.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
// 롬복이 불가능하다면 일반 방식으로 만들면 된다.
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Member {
	private String memberNo;
	private String memName;
	private String gender;
	private int age;
	private String email;
	private String phone;
	private String grad;
	private String isDelete;
	private String password;
	private String isAdmin;
	private String memberId;
	private Date enrollDate;
	private String userIntro;
	private String phoneVerify;
}
