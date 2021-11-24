package com.mycompany.webapp.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Stock;

@Mapper
public interface ProductDao {
	public List<Product> selectProductsWithLarge(HashMap<String, Object> categoryPager);
	public List<Product> selectProductsWithMedium(HashMap<String, Object> categoryPager);
	public List<Product> selectProductsWithSmall(HashMap<String, Object> categoryPager);
	public int countProductsWithLarge(Category category);
	public int countProductsWithMedium(Category category);
	public int countProductsWithSmall(Category category);
	public List<Color> selectProductColors(String pid);
	public Product selectProductWithPno(int pno);
	public Stock selectProductStock(HashMap<String, Object> pidColorcode);
}