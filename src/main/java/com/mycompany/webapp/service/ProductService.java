package com.mycompany.webapp.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.ProductDao;
import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.CategoryLarge;
import com.mycompany.webapp.dto.CategoryMedium;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Exhibition;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.ProductInfo;
import com.mycompany.webapp.dto.Review;
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

	public List<Product> selectBestWithCategory(Category category) {
		log.info("실행");

		if (category.getCmedium() != null) {
			log.info("중분류");
			return productDao.selectBestProductsWithMedium(category);
		}
		log.info("대분류");
		return productDao.selectBestProductsWithLarge(category);
	}

	public List<Product> selectNewWithCategory(Category category) {
		log.info("실행");

		if (category.getCmedium() != null) {
			log.info("중분류");
			return productDao.selectNewProductsWithMedium(category);
		}
		log.info("대분류");
		return productDao.selectNewProductsWithLarge(category);
	}

	public Product selectWithPno(int pno) {
		log.info("실행");
		return productDao.selectProductWithPno(pno);
	}

	public Product selectWithPid(String pid) {
		log.info("실행");
		return productDao.selectProductWithPid(pid);
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

	public List<Stock> selectStock(String pid, String colorcode) {
		log.info("실행");

		HashMap<String, Object> pidColorcode = new HashMap<String, Object>();
		pidColorcode.put("pid", pid);
		pidColorcode.put("colorcode", colorcode);

		return productDao.selectProductStock(pidColorcode);
	}

	public List<Color> getProductColors(String pid) {
		return productDao.selectProductColors(pid);
	}

	@Transactional
	public int updateStockPTotalAmount(Stock stock) {
		productDao.updateStock(stock);
		productDao.updateProductTotalAmount(stock);

		return 1;
	}

	public CategoryLarge getCategory(String clarge) {
		CategoryLarge categoryLarge = new CategoryLarge();
		categoryLarge.setClarge(clarge);

		List<CategoryMedium> list = productDao.selectCategoryLarge(clarge);

		HashMap<String, Object> clargeCmedium = new HashMap<>();
		clargeCmedium.put("clarge", clarge);

		for (CategoryMedium c : list) {
			clargeCmedium.put("cmedium", c.getCmedium());
			List<String> csmall = productDao.selectCategoryMedium(clargeCmedium);
			c.setCsmall(csmall);
		}

		categoryLarge.setCmedium(list);

		return categoryLarge;
	}

	public ProductInfo getProductInfo(String pid, String colorcode) {
		return productDao.selectProuctInfo(pid, colorcode);
	}

	public Stock selectStock(String pid, String colorcode, String ssize) {
		return productDao.selectStockWithPidColorSize(pid, colorcode, ssize);
	}

	public List<Exhibition> getExhibition() {
		return productDao.getExhibition();
	}

	public int createReview(Review review) {
		return productDao.createReview(review);
	}
	
	public List<Review> getReviewList(String pid) {
		return productDao.getReviewList(pid);
	}
}