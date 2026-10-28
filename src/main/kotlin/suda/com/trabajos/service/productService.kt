package suda.com.trabajos.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import suda.com.trabajos.model.Product
import suda.com.trabajos.repository.ProductRepository

@Service
class ProductService {

    @Autowired
    lateinit var productRepository: ProductRepository

    fun list(): List<Product> {
        return productRepository.findAll()
    }

    fun getById(id: Int): Product {
        return productRepository.findById(id)
            .orElseThrow { RuntimeException("Producto no encontrado con el id: $id") }
    }

    fun save(product: Product): Product {
        return productRepository.save(product)
    }

    fun updatePatch(id: Int, productUpdates: Product): Product {
        val existingProduct = getById(id)

        productUpdates.description?.let { existingProduct.description = it }
        productUpdates.price?.let { existingProduct.price = it }
        productUpdates.stock?.let { existingProduct.stock = it }

        return productRepository.save(existingProduct)
    }

    fun delete(id: Int): String {
        val existingProduct = getById(id)
        productRepository.delete(existingProduct)
        return "Producto eliminado correctamente"
    }
}