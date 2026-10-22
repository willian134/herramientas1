package suda.com.trabajos.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import suda.com.trabajos.service.ProductService

@RestController
@RequestMapping("/product")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @GetMapping("/hola")
    fun saludo(): String {
        return "Hola mundo"
    }

    @GetMapping("/adios")
    fun despedida(): String {
        return "Adios mundo"
    }

    @GetMapping("/saludoService")
    fun saludoService(): String {
        return this.productService.saludoService()
    }
}