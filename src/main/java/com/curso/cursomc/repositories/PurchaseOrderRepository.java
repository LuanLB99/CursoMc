package com.curso.cursomc.repositories;

import com.curso.cursomc.domain.Client;
import com.curso.cursomc.domain.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    @Transactional(readOnly = true)
    Page<PurchaseOrder> findByClient(Client client, Pageable pageable);
}
