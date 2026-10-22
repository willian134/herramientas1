package suda.com.trabajos.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import suda.com.trabajos.model.Product

@Repository
interface ProductRepository : JpaRepository<Product, Int> {
}