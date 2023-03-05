package com.mvpmatch.backend.adapter.database.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDatabaseRepository extends JpaRepository<ProductDBO, Long> {
}
