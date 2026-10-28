package suda.com.trabajos.model

import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    var id: Int? = null

    var description: String? = null

    var price: String? = null

    var stock: Int? = null
}