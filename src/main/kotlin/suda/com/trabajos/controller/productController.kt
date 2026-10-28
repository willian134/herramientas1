package suda.com.trabajos.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import suda.com.trabajos.model.Product
import suda.com.trabajos.service.ProductService

@RestController
@RequestMapping("/product")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @GetMapping
    fun list(): List<Product> {
        return productService.list()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): Product {
        return productService.getById(id)
    }

    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED)
    fun crear(@RequestBody product: Product): Product {
        return productService.save(product)
    }

    @PatchMapping("/{id}")
    fun actualizarParcial(@PathVariable id: Int, @RequestBody product: Product): Product {
        return productService.updatePatch(id, product)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun eliminar(@PathVariable id: Int) {
        productService.delete(id)
    }
}