package adultdinosaurdooley.threesixnine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(

        basePackages = {"adultdinosaurdooley.threesixnine.users","adultdinosaurdooley.threesixnine.cart","adultdinosaurdooley.threesixnine.product"},
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef =  "tmJpa"
)
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("adultdinosaurdooley.threesixnine.users","adultdinosaurdooley.threesixnine.cart","adultdinosaurdooley.threesixnine.product");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comment", "true");
        properties.put("hibernate.hbm2ddl.auto", "update"); // Added for ddl-auto
        properties.put("hibernate.show_sql", "true"); // Added for show_sql

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name="tmJpa")
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean((dataSource)).getObject());
        return transactionManager;
    }
}