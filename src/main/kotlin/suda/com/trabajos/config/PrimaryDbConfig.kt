package suda.com.trabajos.config

import jakarta.persistence.EntityManagerFactory
import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["suda.com.trabajos.repository"],
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager"
)
class PrimaryDbConfig {

    @Primary
    @Bean(name = ["primaryDataSource"])
    fun primaryDataSource(): DataSource {
        return DataSourceBuilder.create()
            .url("jdbc:postgresql://localhost:5432/Ejercicio1")
            .username("postgres")
            .password("1")
            .driverClassName("org.postgresql.Driver")
            .build()
    }

    @Primary
    @Bean(name = ["primaryEntityManagerFactory"])
    fun primaryEntityManagerFactory(
        @Qualifier("primaryDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan("suda.com.trabajos.model")
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        return em
    }

    @Primary
    @Bean(name = ["primaryTransactionManager"])
    fun primaryTransactionManager(
        @Qualifier("primaryEntityManagerFactory") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}