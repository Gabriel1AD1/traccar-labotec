package com.labotec.traccar.infra.config;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.labotec.traccar.infra.db.mysql.jpa.traccar",  // Repositorios para traccar
        entityManagerFactoryRef = "traccarDbEntityManagerFactory",
        transactionManagerRef = "traccarDbTransactionManager"
)
public class TraccarDbConfig {

    @Bean(name = "traccarDbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2")  // Cambiado el prefijo aquí
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "traccarDbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("traccarDbDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.labotec.traccar.infra.db.mysql.jpa.traccar.entity")  // Paquete de entidades de traccar
                .persistenceUnit("traccarDb")
                .build();
    }

    @Bean(name = "traccarDbTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("traccarDbEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
