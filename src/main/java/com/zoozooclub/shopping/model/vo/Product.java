package com.zoozooclub.shopping.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Product {
	private int productNo;
	private String productName;
	private int productPrice;
	private String productCategory;
	private String productDescription;
	private String productCompany;
	private int productStock;
	private String productImage;
}
