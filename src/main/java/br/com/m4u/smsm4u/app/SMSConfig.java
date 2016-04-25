package br.com.m4u.smsm4u.app;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.transaction.support.TransactionSynchronizationManager.getResource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author AndersonFirmino
 *
 */
@ComponentScan
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class SMSConfig {

    private static final String DATA_SOURCE_NAME = "smsM4UDS";

    /**
     * Provides an entity manager factory based on the data source.
     *
     * @param ds the data source
     * @return the entity manager factory
     */
    @Bean
    EntityManagerFactory entityManagerFactoryProvider() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory(DATA_SOURCE_NAME);
        return emf;
    }

    /**
     * Provides a proxy instance of the current transaction's entity manager
     * which is ready to inject via constructor injection and guarantees
     * immutability.
     *
     * @param emf the entity manager factory
     * @return a proxy of entity manager
     */
    @Bean
    @Scope(SCOPE_PROTOTYPE)
    EntityManager entityManagerProvider(EntityManagerFactory emf) {
        EntityManagerHolder holder = (EntityManagerHolder) getResource(emf);
        if (holder == null) {
            return emf.createEntityManager();
        }
        return holder.getEntityManager();
    }

    /**
     * Provides JPA based Spring transaction manager.
     *
     * @param emf the entity manager factory
     * @return jpa transaction manager
     */
    @Bean
    JpaTransactionManager jpaTransactionManagerProvider(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(emf);
        return transactionManager;
    }
}