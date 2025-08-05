package com.zoozooclub.shopping.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Cart {
	private int cartNo;
	private int cartAmount;
	private String deleteState;
	private int memberNo;
	private int pId;
	private String productName;
	private int productPrice;
	private double cartPrice;
	private String productImage;
}
