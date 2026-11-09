package suda.com.trabajos.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import suda.com.trabajos.model.Order
import suda.com.trabajos.model.OrderStatus

@Repository
interface OrderRepository : JpaRepository<Order, Int> {

    // Método para validar la regla del negocio:
    // "Un cliente no puede recibir un pedido si ya tiene otro pedido pendiente"
    fun existsByCustomerNameAndStatus(customerName: String, status: OrderStatus): Boolean
}