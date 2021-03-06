package com.mycompany.webapp.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.CategoryMedium;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Exhibition;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.ProductInfo;
import com.mycompany.webapp.dto.Review;
import com.mycompany.webapp.dto.Stock;

@Mapper
public interface ProductDao {
	public List<Product> selectProductsWithLarge(HashMap<String, Object> categoryPager);
	public List<Product> selectProductsWithMedium(HashMap<String, Object> categoryPager);
	public List<Product> selectProductsWithSmall(HashMap<String, Object> categoryPager);
	public List<Product> selectBestProductsWithLarge(Category category);
	public List<Product> selectBestProductsWithMedium(Category category);
	public List<Product> selectNewProductsWithLarge(Category category);
	public List<Product> selectNewProductsWithMedium(Category category);
	public int countProductsWithLarge(Category category);
	public int countProductsWithMedium(Category category);
	public int countProductsWithSmall(Category category);
	public List<Color> selectProductColors(String pid);
	public Product selectProductWithPno(int pno);
	public Product selectProductWithPid(String pid);
	public int updateStock(Stock stock);
	public int updateProductTotalAmount(Stock stock);
	public List<Stock> selectProductStock(HashMap<String, Object> pidColorcode);
	public List<CategoryMedium> selectCategoryLarge(String clarge);
	public List<String> selectCategoryMedium(HashMap<String, Object> clargeCmedium);
	public ProductInfo selectProuctInfo(@Param("pid")String pid, @Param("colorcode")String colorcode);
	public Stock selectStockWithPidColorSize(@Param("pid")String pid, @Param("colorcode")String colorcode, @Param("size")String ssize);
	public List<Exhibition> getExhibition();
	public int createReview(Review review);
	public List<Review> getReviewList(String pid);
}