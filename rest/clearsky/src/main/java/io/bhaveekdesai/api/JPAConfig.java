package io.bhaveekdesai.api;

import io.bhaveekdesai.api.constants.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class JPAConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean emf(){
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setDataSource(getDataSource());
        emf.setPackagesToScan("io.bhaveekdesai.api.entity");
        emf.setJpaProperties(jpaProperties());
        return emf;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(URI.DRIVER_CLASS_NAME);
        ds.setUrl(URI.MYSQL_URL);
        ds.setUsername(URI.MYSQL_USERNAME);
        ds.setPassword(URI.MYSQL_PASSWORD);
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    private Properties jpaProperties() {
        Properties props = new Properties();
        props.setProperty(URI.PROP_HIBERNATE_DIALECT, URI.PROP_HIBERNATE_DIALECT_VALUE);
        props.setProperty(URI.PROP_HIBERNATE_HBM2DDL_AUTO, URI.PROP_HIBERNATE_HBM2DDL_AUTO_VALIDATE);
        props.setProperty(URI.PROP_HIBERNATE_SHOW_SQL,"true");
        props.setProperty(URI.PROP_HIBERNTE_FORMAT_SQL,"true");
        return props;
    }
}
