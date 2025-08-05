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
public class AdminProduct {

	private int productNo;
	private String productName;
	private int productPrice;
	private String productCategory;
	private String productDescription;
	private String productCompany;
	private int productStock;
	private String productImage;
}
