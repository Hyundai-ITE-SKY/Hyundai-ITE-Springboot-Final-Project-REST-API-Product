package com.mycompany.webapp.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.ProductDao;
import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Stock;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Resource
	private ProductDao productDao;

	public List<Product> selectWithCategory(Category category, Pager pager) {
		log.info("실행");

		HashMap<String, Object> categoryPager = new HashMap<String, Object>();
		categoryPager.put("category", category);
		categoryPager.put("pager", pager);

		if (category.getCmedium() != null) {
			if (category.getCsmall() != null) {
				log.info("소분류");
				return productDao.selectProductsWithSmall(categoryPager);
			}
			log.info("중분류");
			return productDao.selectProductsWithMedium(categoryPager);
		}
		log.info("대분류");
		return productDao.selectProductsWithLarge(categoryPager);
	}

	public Product selectWithPno(int pno) {
		log.info("실행");
		return productDao.selectProductWithPno(pno);
	}

	public int getTotalProductNum(Category category) {
		if (category.getCmedium() != null) {
			if (category.getCsmall() != null) {
				return productDao.countProductsWithSmall(category);
			}
			return productDao.countProductsWithMedium(category);
		}
		return productDao.countProductsWithLarge(category);
	}

	public Stock selectStock(String pid, String colorcode) {
		log.info("실행");

		HashMap<String, Object> pidColorcode = new HashMap<String, Object>();
		pidColorcode.put("pid", pid);
		pidColorcode.put("colorcode", colorcode);

		return productDao.selectProductStock(pidColorcode);
	}

	public List<Color> getProductColors(String pid) {
		return productDao.selectProductColors(pid);
	}
}