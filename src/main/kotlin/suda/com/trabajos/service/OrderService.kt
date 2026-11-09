package suda.com.trabajos.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suda.com.trabajos.model.Order
import suda.com.trabajos.model.OrderStatus
import suda.com.trabajos.model.Product
import suda.com.trabajos.repository.OrderRepository
import suda.com.trabajos.repository.ProductRepository
import java.util.Random

@Service
open class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) {

    // 1. CREAR PEDIDO
    @Transactional
    open fun createOrder(customerName: String, productIdsWithQuantity: Map<Int, Int>): Order {
        // Regla: Un cliente no puede recibir un pedido si ya tiene otro pedido pendiente
        val hasPendingOrder = orderRepository.existsByCustomerNameAndStatus(customerName, OrderStatus.PENDIENTE)
        if (hasPendingOrder) {
            throw RuntimeException("El cliente '$customerName' ya tiene un pedido en estado PENDIENTE.")
        }

        val productsList = mutableListOf<Product>()

        for ((productId, quantity) in productIdsWithQuantity) {
            // Regla: El producto debe existir
            val product = productRepository.findById(productId)
                .orElseThrow { RuntimeException("El producto con ID $productId no existe.") }

            val currentStock = product.stock ?: 0

            // Regla: No se pueden hacer pedidos de un producto sin suficiente stock
            if (currentStock < quantity) {
                throw RuntimeException("Stock insuficiente para el producto '${product.nombre}'. Stock disponible: $currentStock")
            }

            // Regla: Cuando un pedido se realiza, el stock del producto baja
            product.stock = currentStock - quantity
            productRepository.save(product)

            // Agregar las unidades al pedido
            repeat(quantity) {
                productsList.add(product)
            }
        }

        // Regla: Código de verificación generado por el sistema (randomizador)
        val generatedCode = String.format("%06d", Random().nextInt(1000000))

        val newOrder = Order().apply {
            this.customerName = customerName
            this.status = OrderStatus.PENDIENTE
            this.verificationCode = generatedCode
            this.products = productsList
        }

        return orderRepository.save(newOrder)
    }

    // 2. CAMBIAR A ESTADO ENVIADO
    @Transactional
    open fun markAsSent(orderId: Int): Order {
        val order = orderRepository.findById(orderId)
            .orElseThrow { RuntimeException("Pedido no encontrado con ID: $orderId") }

        if (order.status != OrderStatus.PENDIENTE) {
            throw RuntimeException("Solo se puede pasar a ENVIADO si el pedido está PENDIENTE.")
        }

        order.status = OrderStatus.ENVIADO
        return orderRepository.save(order)
    }

    // 3. CAMBIAR A ESTADO RECIBIDO (CON CÓDIGO)
    @Transactional
    open fun markAsReceived(orderId: Int, inputCode: String): Order {
        val order = orderRepository.findById(orderId)
            .orElseThrow { RuntimeException("Pedido no encontrado con ID: $orderId") }

        // Regla: Antes de marcar el producto como recibido tiene que estar en estado de enviado
        if (order.status != OrderStatus.ENVIADO) {
            throw RuntimeException("El pedido debe estar en estado ENVIADO antes de marcarlo como RECIBIDO.")
        }

        // Regla: Solo se puede marcar un pedido como entregado cuando se ingresa un código de verificación
        if (order.verificationCode != inputCode) {
            throw RuntimeException("Código de verificación incorrecto.")
        }

        order.status = OrderStatus.RECIBIDO
        return orderRepository.save(order)
    }

    // Consultas útiles
    open fun findAll(): List<Order> = orderRepository.findAll()
}