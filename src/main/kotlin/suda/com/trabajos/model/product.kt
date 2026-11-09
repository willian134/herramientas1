package suda.com.trabajos.model

import jakarta.persistence.*

@Entity
@Table(name = "product")
open class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    open var id: Int? = null

    @Column(nullable = false)
    open var nombre: String? = null

    open var description: String? = null

    open var price: Double? = null

    @Column(nullable = false)
    open var stock: Int = 0
}