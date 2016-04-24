package br.com.m4u.smsm4u.fixture;

import static javax.persistence.Persistence.createEntityManagerFactory;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.transaction.support.TransactionSynchronizationManager.getResource;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.fitbur.testify.need.NeedInstance;
import com.github.dockerjava.api.command.InspectContainerResponse;

/**
 *
 * @author AndersonFirmino
 *
 */
@Primary
@Configuration
public class HSQLDBTestConfig {

	@Bean
	DataSource dataSourceProvider(NeedInstance<InspectContainerResponse> instance) {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:hsqldb:MyDB;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
	}

	/**
	 * Provides an entity manager factory based on the data source.
	 *
	 * @param ds
	 *            the data source
	 * @return the entity manager factory
	 */
	@Bean
	EntityManagerFactory entityManagerFactoryProvider(DataSource ds) {
		Map<String, Object> props = new HashMap<>();
		props.put(Environment.DATASOURCE, ds);
		props.put(Environment.PHYSICAL_NAMING_STRATEGY, new PhysicalNamingStrategyStandardImpl());
		props.put(Environment.IMPLICIT_NAMING_STRATEGY, new ImplicitNamingStrategyComponentPathImpl());

		return createEntityManagerFactory("example.junit.springboot.systemtest", props);
	}

	/**
	 * Provides a proxy instance of the current transaction's entity manager
	 * which is ready to inject via constructor injection and guarantees
	 * immutability.
	 *
	 * @param emf
	 *            the entity manager factory
	 * @return a proxy of entity manager
	 */
	@Bean
	@Scope(SCOPE_PROTOTYPE)
	EntityManager entityManagerProvider(EntityManagerFactory emf) {
		EntityManagerHolder holder = (EntityManagerHolder) getResource(emf);

		if (holder == null) {
			throw new IllegalStateException("Transaction not available. Is your service annotated with"
					+ " @Transactional? Did you enable @EnableTransactionManagement?");
		}

		return holder.getEntityManager();
	}

	/**
	 * Provides JPA based Spring transaction manager.
	 *
	 * @param emf
	 *            the entity manager factory
	 * @return jpa transaction manager
	 */
	@Bean
	JpaTransactionManager jpaTransactionManagerProvider(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(emf);

		return transactionManager;
	}
}