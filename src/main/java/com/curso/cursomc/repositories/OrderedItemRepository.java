package com.curso.cursomc.repositories;

import com.curso.cursomc.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedItemRepository extends JpaRepository<Item, Integer> {

}
