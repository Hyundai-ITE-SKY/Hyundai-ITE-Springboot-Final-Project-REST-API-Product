package com.mycompany.webapp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.CategoryLarge;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.ProductInfo;
import com.mycompany.webapp.dto.Stock;
import com.mycompany.webapp.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductController {
	@Resource
	ProductService productService;

	@GetMapping(value = "/productlist/{clarge}/{pageNo}", produces = "application/json; charset=UTF-8")
	public String getProductList(@PathVariable String clarge, @PathVariable int pageNo) {
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

		JSONObject json = new JSONObject();
		json.put("productlist", list);
		json.put("totalPages", pager.getTotalPageNo());

		return json.toString();
	}

	@GetMapping(value = "/productlist/{clarge}/{cmedium}/{pageNo}", produces = "application/json; charset=UTF-8")
	public String getProductList(@PathVariable String clarge, @PathVariable String cmedium, @PathVariable int pageNo) {
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

		JSONObject json = new JSONObject();
		json.put("productlist", list);
		json.put("totalPages", pager.getTotalPageNo());

		return json.toString();
	}

	@GetMapping(value = "/productlist/{clarge}/{cmedium}/{csmall}/{pageNo}", produces = "application/json; charset=UTF-8")
	public String getProductList(@PathVariable String clarge, @PathVariable String cmedium, @PathVariable String csmall,
			@PathVariable int pageNo) {
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

		JSONObject json = new JSONObject();
		json.put("productlist", list);
		json.put("totalPages", pager.getTotalPageNo());

		return json.toString();
	}

	@GetMapping("/product/colorlist/{pid}")
	public List<Color> getProductColor(@PathVariable String pid) {
		log.info("실행");
		return productService.getProductColors(pid);
	}

	@GetMapping("/product/stock/{pid}/{colorcode}")
	public List<Stock> getProductStock(@PathVariable String pid, @PathVariable String colorcode) {
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

		for (Color color : product.getColors()) {
			List<Stock> stocks = productService.selectStock(pid, color.getCcolorcode());
			color.setStocks(stocks);
		}

		return product;
	}
	
	@PostMapping("/product/updatestock")
	public int updateStock(Stock stock) {
		log.info("실행");
		productService.updateStock(stock);
		productService.updateProductTotalAmount(stock);
		return 1;
	}
	

	@GetMapping("/category/{clarge}")
	public CategoryLarge getCategory(@PathVariable String clarge) {
		log.info("실행");
		CategoryLarge categoryLarge = productService.getCategory(clarge);
		return categoryLarge;
	}

	@GetMapping("/product/info/{pid}/{colorcode}")
	public ProductInfo getProductInfo(@PathVariable String pid, @PathVariable String colorcode) {
		log.info("실행");
		ProductInfo pinfo = productService.getProductInfo(pid, colorcode);
		return pinfo;
	}

	@GetMapping("/productlist/best/{clarge}")
	public List<Product> getBestWithLarge(@PathVariable String clarge) {
		log.info("실행");

		Category category = new Category();
		category.setClarge(clarge);

		List<Product> products = productService.selectBestWithCategory(category);

		for (Product product : products) {
			product.setColors(productService.getProductColors(product.getPid()));

			for (Color color : product.getColors()) {
				List<Stock> stocks = productService.selectStock(product.getPid(), color.getCcolorcode());
				color.setStocks(stocks);
			}
		}

		return products;
	}

	@GetMapping("/productlist/best/{clarge}/{cmedium}")
	public List<Product> getBestWithLarge(@PathVariable String clarge, @PathVariable String cmedium) {
		log.info("실행");

		Category category = new Category();
		category.setClarge(clarge);
		category.setCmedium(cmedium);

		List<Product> products = productService.selectBestWithCategory(category);

		for (Product product : products) {
			product.setColors(productService.getProductColors(product.getPid()));

			for (Color color : product.getColors()) {
				List<Stock> stocks = productService.selectStock(product.getPid(), color.getCcolorcode());
				color.setStocks(stocks);
			}
		}

		return products;
	}
	
	@GetMapping("/productlist/new/{clarge}")
	public List<Product> getNewWithLarge(@PathVariable String clarge) {
		log.info("실행");

		Category category = new Category();
		category.setClarge(clarge);

		List<Product> products = productService.selectNewWithCategory(category);

		for (Product product : products) {
			product.setColors(productService.getProductColors(product.getPid()));

			for (Color color : product.getColors()) {
				List<Stock> stocks = productService.selectStock(product.getPid(), color.getCcolorcode());
				color.setStocks(stocks);
			}
		}

		return products;
	}

	@GetMapping("/productlist/new/{clarge}/{cmedium}")
	public List<Product> getNewWithLarge(@PathVariable String clarge, @PathVariable String cmedium) {
		log.info("실행");

		Category category = new Category();
		category.setClarge(clarge);
		category.setCmedium(cmedium);

		List<Product> products = productService.selectNewWithCategory(category);

		for (Product product : products) {
			product.setColors(productService.getProductColors(product.getPid()));

			for (Color color : product.getColors()) {
				List<Stock> stocks = productService.selectStock(product.getPid(), color.getCcolorcode());
				color.setStocks(stocks);
			}
		}

		return products;
	}
}