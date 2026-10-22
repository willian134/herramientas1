package suda.com.trabajos.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import suda.com.trabajos.model.Product
import suda.com.trabajos.repository.ProductRepository

@Service
class ProductService {

    @Autowired
    lateinit var productRepository: ProductRepository

    fun saludoService(): String {
        return "Hola desde el servicio"
    }

    fun list(): List<Product> {
        return productRepository.findAll()
    }
}