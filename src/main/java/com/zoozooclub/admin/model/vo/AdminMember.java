package com.zoozooclub.admin.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AdminMember {
	
	private int memberNo;
	private String memberId;
	private String memName;
	private String gender;
	private int age;
	private String email;
	private String phone;
	private String grade;
	private String isDelete;
	private String password;
	private String isAdmin;
}
