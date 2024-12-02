package org.example.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("org.example.models");
        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.show_sql", "true");
        factoryBean.setHibernateProperties(props);
        return factoryBean;
    }

    @Bean
    public org.springframework.orm.hibernate5.HibernateTransactionManager transactionManager(LocalSessionFactoryBean sessionFactoryBean) throws Exception {
        return new org.springframework.orm.hibernate5.HibernateTransactionManager(sessionFactoryBean.getObject());
    }
}