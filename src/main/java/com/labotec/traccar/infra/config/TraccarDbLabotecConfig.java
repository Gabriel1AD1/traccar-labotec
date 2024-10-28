package com.labotec.traccar.infra.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.labotec.traccar.infra.db.mysql.jpa.labotec",
        entityManagerFactoryRef = "traccarDbLabotecEntityManagerFactory",
        transactionManagerRef = "traccarDbLabotecTransactionManager"
)
public class TraccarDbLabotecConfig {

    @Primary
    @Bean(name = "traccarDbLabotecDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Value("${spring.jpa.hibernate.ddl.traccar.labotec}")
    public String UpdateOrCreate ;
    @Primary
    @Bean(name = "traccarDbLabotecEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("traccarDbLabotecDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.labotec.traccar.infra.db.mysql.jpa.labotec.entity")
                .persistenceUnit("traccarDbLabotec")
                .properties(Map.of("hibernate.hbm2ddl.auto", UpdateOrCreate)) // Esto ya no será necesario
                .build();
    }

    @Primary
    @Bean(name = "traccarDbLabotecTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("traccarDbLabotecEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
