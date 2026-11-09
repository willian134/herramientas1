package suda.com.trabajos.controller

import org.springframework.web.bind.annotation.*
import suda.com.trabajos.model.Order
import suda.com.trabajos.service.OrderService

// DTOs para la recepción de datos en los endpoints
data class ProductQuantityRequest(
    val productId: Int = 0,
    val quantity: Int = 0
)

data class CreateOrderRequest(
    val customerName: String = "",
    val items: List<ProductQuantityRequest> = emptyList()
)

data class VerifyCodeRequest(
    val verificationCode: String = ""
)

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {

    // 1. Obtener todos los pedidos
    @GetMapping
    fun getAll(): List<Order> = orderService.findAll()

    // 2. Crear pedido (baja stock, genera código random, valida que no tenga pendientes)
    @PostMapping
    fun create(@RequestBody request: CreateOrderRequest): Order {
        val itemsMap = request.items.associate { it.productId to it.quantity }
        return orderService.createOrder(request.customerName, itemsMap)
    }

    // 3. Marcar pedido como ENVIADO
    @PutMapping("/{id}/sent")
    fun markAsSent(@PathVariable id: Int): Order {
        return orderService.markAsSent(id)
    }

    // 4. Marcar pedido como RECIBIDO (requiere el código de verificación)
    @PutMapping("/{id}/receive")
    fun markAsReceived(
        @PathVariable id: Int,
        @RequestBody request: VerifyCodeRequest
    ): Order {
        return orderService.markAsReceived(id, request.verificationCode)
    }
}