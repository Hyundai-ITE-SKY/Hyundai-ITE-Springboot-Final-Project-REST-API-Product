package com.mycompany.webapp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Stock;
import com.mycompany.webapp.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductController {
	@Resource
	ProductService productService;

	@GetMapping("/productlist/{clarge}/{pageNo}")
	public List<Product> getProductList(@PathVariable String clarge, @PathVariable int pageNo) {
		log.info("실행: 대분류");

		Category category = new Category();
		category.setClarge(clarge);

		int totalRows = productService.getTotalProductNum(category);
		Pager pager = new Pager(12, 5, totalRows, pageNo);

		List<Product> list = productService.selectWithCategory(category, pager);

		for (Product product : list) {
			String pid = product.getPid();
			product.setColors(productService.getProductColors(pid));
		}

		return list;
	}

	@GetMapping("/productlist/{clarge}/{cmedium}/{pageNo}")
	public List<Product> getProductList(@PathVariable String clarge, @PathVariable String cmedium,
			@PathVariable int pageNo) {
		log.info("실행: 중분류");

		Category category = new Category();
		category.setClarge(clarge);
		category.setCmedium(cmedium);

		int totalRows = productService.getTotalProductNum(category);
		Pager pager = new Pager(12, 5, totalRows, pageNo);

		List<Product> list = productService.selectWithCategory(category, pager);

		for (Product product : list) {
			String pid = product.getPid();
			product.setColors(productService.getProductColors(pid));
		}

		return list;
	}

	@GetMapping("/productlist/{clarge}/{cmedium}/{csmall}/{pageNo}")
	public List<Product> getProductList(@PathVariable String clarge, @PathVariable String cmedium,
			@PathVariable String csmall, @PathVariable int pageNo) {
		log.info("실행: 소분류");

		Category category = new Category();
		category.setClarge(clarge);
		category.setCmedium(cmedium);
		category.setCsmall(csmall);
		log.info(category.toString());

		int totalRows = productService.getTotalProductNum(category);
		Pager pager = new Pager(12, 5, totalRows, pageNo);

		List<Product> list = productService.selectWithCategory(category, pager);

		for (Product product : list) {
			String pid = product.getPid();
			product.setColors(productService.getProductColors(pid));
		}
		
		return list;
	}

	@GetMapping("/product/colorlist/{pid}")
	public List<Color> getProductColor(@PathVariable String pid) {
		log.info("실행");
		return productService.getProductColors(pid);
	}

	@GetMapping("/product/stock/{pid}/{colorcode}")
	public Stock getProductStock(@PathVariable String pid, @PathVariable String colorcode) {
		log.info("실행");
		return productService.selectStock(pid, colorcode);
	}

//	@GetMapping("/product/{pno}")
//	public Product getProduct(@PathVariable int pno) {
//		log.info("실행");
//		Product product = productService.selectWithPno(pno);
//
//		String pid = product.getPid();
//		product.setColors(productService.getProductColors(pid));
//
//		return product;
//	}
	
	@GetMapping("/product/{pid}")
	public Product getProduct(@PathVariable String pid) {
		log.info("실행");
		Product product = productService.selectWithPid(pid);
		product.setColors(productService.getProductColors(pid));

		return product;
	}
	
	@PostMapping("/product/updatestock")
	public int updateStock(Stock stock) {
		log.info("실행");
		productService.updateStock(stock);
		productService.updateProductTotalAmount(stock);
		return 1;
	}
	
	
}