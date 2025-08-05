package com.zoozooclub.shopping.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.zoozooclub.common.PageInfo;
import com.zoozooclub.shopping.model.vo.Cart;
import com.zoozooclub.shopping.model.vo.Coupon;
import com.zoozooclub.shopping.model.vo.OrderDetail;
import com.zoozooclub.shopping.model.vo.Orders;
import com.zoozooclub.shopping.model.vo.ProDetail;
import com.zoozooclub.shopping.model.vo.Product;
import com.zoozooclub.shopping.model.vo.Review;

@Mapper
public interface ShoppingMapper {

	ArrayList<Product> selectList(PageInfo pi);

	Product selectProduct(int pNo);

	int insertCart(Cart c);

	ArrayList<Cart> selectCart(String memberNo);

	int deleteCart(int cNo);

	ArrayList<Review> selectReviewList(int pNo);

	int countReivew(int pNo);

	Double reviewAvg(int product_no);

	int rBtnShow(Map<String, Object> map);

	int insertReview(Review r);

	Integer duplicationReview(Review r);

	Integer getOrderNo(Map<String, Object> map);

	int getProductCount(); // 페이징용 전체 상품 개수

	int getSearchCount(String keyword);

	List<Product> searchProductList(String keyword, RowBounds rowBounds);

	int getReviewCountByProduct(int pNo);

	ArrayList<Product> selectProductList();

	int insertOrders(Orders o);

	int insertProDetail(ProDetail pd);

	List<Cart> getCartListByNos(List<Integer> cartNoList);

	int getOrderNoForOD(int memberNo);

	int insertOrderDetail(OrderDetail od);

	ArrayList<Coupon> getCouponList(int memberNo);
}
