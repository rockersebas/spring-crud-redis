package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductDao;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class SpringDataRedisApplication {
	
	
	
	@Autowired
	private ProductDao dao;
	
	@PostMapping
	public Product save(@RequestBody Product product) {
		dao.save(product);
		return product;
	}
	@GetMapping
	public List<Object> getAll()
	{
		return dao.findAll();
	}
	@GetMapping("/{id}")
	@Cacheable(key = "#id", value = "product")
	public Product findProduct(@PathVariable int id) {
		return dao.findById(id);
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = "product", allEntries = true)
	public String deleteProduct(@PathVariable int id) {
		return dao.deleteProduct(id);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataRedisApplication.class, args);
	}

}
