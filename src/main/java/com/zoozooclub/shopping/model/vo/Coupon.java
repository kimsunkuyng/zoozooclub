package com.zoozooclub.shopping.model.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Coupon {
	private int memberNo;
	private int couponeNumber;
	private String couponName;
	private String couponTarget;
	private int discountRate;
	private Date startDate;
	private Date endDate;
	private int minOrderPrice;
	
}
