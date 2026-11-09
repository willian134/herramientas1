package suda.com.trabajos.model

import jakarta.persistence.*

enum class OrderStatus {
    PENDIENTE,
    ENVIADO,
    RECIBIDO
}

@Entity
@Table(name = "orders")
open class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    open var id: Int? = null

    @Column(name = "customer_name", nullable = false)
    open var customerName: String? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var status: OrderStatus = OrderStatus.PENDIENTE

    @Column(name = "verification_code")
    open var verificationCode: String? = null

    // Relación directa sin tabla de entidad intermedia manual
    @ManyToMany
    @JoinTable(
        name = "order_products",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    open var products: MutableList<Product> = mutableListOf()
}