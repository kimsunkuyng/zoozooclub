package com.zoozooclub.shopping.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderDetail {
	
	private int orderDetailNo;
	private int orderNo;
	private int productNo;
	private int productAmount;
	private double productPrice;
}
