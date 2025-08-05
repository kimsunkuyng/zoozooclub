package com.zoozooclub.shopping.model.vo;

import java.sql.Date;

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
public class Review {
	private int productReivewNo;
	private String productReviewContent;
	private Date productReviewDate;
	private int productReviewRating;
	private int productNo;
	private int memberNo;
	private String memName;
	private int orderNo;
	private String productImage;
}
