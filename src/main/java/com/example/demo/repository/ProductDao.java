package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository
public class ProductDao {

	@Autowired
	private RedisTemplate<String, ?> template;
	
	public static  final String HASH_KEY = "product"; 
	
	public Product save (Product product)
	{
		template.opsForHash().put(HASH_KEY, product.getId(), product);
		return product;
	}
	
	public List<Object> findAll()
	{		
		System.out.println("Got from FindAll and DB");
		return template.opsForHash().values(HASH_KEY);
	}
	
	public Product findById(int id)
	{
		System.out.println("Got from findById and DB");
		return (Product) template.opsForHash().get(HASH_KEY, id);
	}
	
	public String deleteProduct(int id)
	{
		template.opsForHash().delete(HASH_KEY, id);
		return "Product deleted";
	}
}
