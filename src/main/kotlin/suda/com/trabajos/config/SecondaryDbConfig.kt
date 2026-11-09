package suda.com.trabajos.config

import jakarta.persistence.EntityManagerFactory
import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["suda.com.trabajos.db2.repository"],
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager"
)
class SecondaryDbConfig {

    @Bean(name = ["secondaryDataSource"])
    fun secondaryDataSource(): DataSource {
        return DataSourceBuilder.create()
            .url("jdbc:postgresql://localhost:5432/Ejercicio2")
            .username("postgres")
            .password("1")
            .driverClassName("org.postgresql.Driver")
            .build()
    }

    @Bean(name = ["secondaryEntityManagerFactory"])
    fun secondaryEntityManagerFactory(
        @Qualifier("secondaryDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan("suda.com.trabajos.db2.model")
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        return em
    }

    @Bean(name = ["secondaryTransactionManager"])
    fun secondaryTransactionManager(
        @Qualifier("secondaryEntityManagerFactory") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}