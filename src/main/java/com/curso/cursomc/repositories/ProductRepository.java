package com.curso.cursomc.repositories;

import com.curso.cursomc.domain.Category;
import com.curso.cursomc.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT product FROM Product product INNER JOIN product.categories cat WHERE product.name LIKE %:name% AND cat IN :categories")
    Page<Product> findDistinctByNameContainingAndCategriesIn(@Param("name") String name,@Param("categories") List<Category> categories, Pageable pageable);
}
