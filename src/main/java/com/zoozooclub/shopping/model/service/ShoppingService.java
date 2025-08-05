package com.zoozooclub.shopping.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.zoozooclub.common.PageInfo;
import com.zoozooclub.shopping.model.mapper.ShoppingMapper;
import com.zoozooclub.shopping.model.vo.Cart;
import com.zoozooclub.shopping.model.vo.Coupon;
import com.zoozooclub.shopping.model.vo.OrderDetail;
import com.zoozooclub.shopping.model.vo.Orders;
import com.zoozooclub.shopping.model.vo.ProDetail;
import com.zoozooclub.shopping.model.vo.Product;
import com.zoozooclub.shopping.model.vo.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingService {

	private final ShoppingMapper mapper;

	public ArrayList<Product> selectList(PageInfo pi) {
		return mapper.selectList(pi);
	}

	public Product selectProduct(int pNo) {
		return mapper.selectProduct(pNo);
	}

	public int insertCart(Cart c) {
		return mapper.insertCart(c);
	}

	public ArrayList<Cart> selectCart(String memberNo) {
		return mapper.selectCart(memberNo);
	}

	public int deleteCart(int cNo) {
		return mapper.deleteCart(cNo);
	}

	public ArrayList<Review> selectReviewList(int pNo) {
		return mapper.selectReviewList(pNo);
	}

	public int countReivew(int pNo) {
		return mapper.countReivew(pNo);
	}

	public double reviewAvg(int product_no) {
		return mapper.reviewAvg(product_no);
	}

	public int rBtnShow(Map<String, Object> map) {
		return mapper.rBtnShow(map);
	}

	public int insertReview(Review r) {
		return mapper.insertReview(r);
	}

	public Integer duplicationReview(Review r) {
		return mapper.duplicationReview(r);
	}

	public Integer getOrderNo(Map<String, Object> map) {
		return mapper.getOrderNo(map);
	}

	public int getProductCount() {
		return mapper.getProductCount();
	}

	public int getSearchCount(String keyword) {
		return mapper.getSearchCount(keyword);
	}

	public List<Product> searchProductList(String keyword, PageInfo pi) {
		return mapper.searchProductList(keyword, new RowBounds((pi.getCurrentPage() - 1) * pi.getBoardLimit(), pi.getBoardLimit()));
	}

	public int getReviewCountByProduct(int pNo) {
		// TODO Auto-generated method stub
		return mapper.getReviewCountByProduct(pNo);
	}

	public ArrayList<Product> selectProductList() {
		return mapper.selectProductList();
	}

	public int insertOrders(Orders o) {
		return mapper.insertOrders(o);
	}

	public int insertProDetail(ProDetail pd) {
		return mapper.insertProDetail(pd);
	}

	public List<Cart> getCartListByNos(List<Integer> cartNoList) {
		return mapper.getCartListByNos(cartNoList);
	}

	public int getOrderNoForOD(int memberNo) {
		return mapper.getOrderNoForOD(memberNo);
	}

	public int insertOrderDetail(OrderDetail od) {
		return mapper.insertOrderDetail(od);
	}

	public ArrayList<Coupon> getCouponList(int memberNo) {
		return mapper.getCouponList(memberNo);
	}
}
