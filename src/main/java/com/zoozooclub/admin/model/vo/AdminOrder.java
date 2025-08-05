package com.zoozooclub.admin.model.vo;

import java.sql.Date;

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
public class AdminOrder {

	private int orderNo;
	private String orderAddress;
	private Date orderDate;
	private int memberNo;
	private String payMethod;
	private String payState;
	private String deliveryState;
	private String deliveryCompany;
	private int deliveryPrice;

	private String memName;
}
