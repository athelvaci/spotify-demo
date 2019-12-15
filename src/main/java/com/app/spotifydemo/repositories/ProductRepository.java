package com.app.spotifydemo.repositories;

import com.app.spotifydemo.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
