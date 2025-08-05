package com.zoozooclub.shopping.model.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Orders {
	
	private int orderNo;
	private String orderAddress;
	private Date orderDate;
	private int memberNo;
	private String payMethod;
	private String payState;
	private String deliveryState;
	private String delvertyCompany;
	private double deliveryPrice;
}
