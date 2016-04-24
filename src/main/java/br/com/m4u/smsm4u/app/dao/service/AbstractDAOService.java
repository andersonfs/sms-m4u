package br.com.m4u.smsm4u.app.dao.service;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.HibernateException;

import br.com.m4u.smsm4u.app.entity.IEntity;

/**
 * Classe abstrata do serviço de persistência que implementa métodos comuns para as entidades.
 *
 * @author AndersonFirmino
 *
 * @param <E> Entidade persistente
 * @param <K> Tipo da chave primária
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractDAOService<E extends IEntity, K> implements IDAOService<E, K> {

	private static final long serialVersionUID = -5407180687728037935L;
	private final Provider<EntityManager> entityManagerProvider;

	AbstractDAOService(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

	protected EntityManager getEntityManager() {
		final EntityManager entityManager =  entityManagerProvider.get();
		return entityManager;
	}

	public void save(E bean) {
		EntityManager manager = getEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		try {
			if (transaction != null && manager != null) {
				transaction.begin();
				manager.persist(bean);
				transaction.commit();
				manager.close();
			}
		} catch (Throwable exception) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	@Override
	public void delete(K key) {
		EntityManager manager = getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			if (transaction != null && manager != null && key != null) {
				transaction.begin();
				E entity = manager.find(getEntityClass(), key);
				if (entity != null) {
					manager.remove(entity);
					transaction.commit();
				} else {
					transaction.rollback();
					throw new HibernateException("Key is not valid.");
				}
				manager.close();
			}
		} catch (Throwable exception) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			throw new HibernateException(exception);
		}
	}

	@Override
	public E findById(K key) {
		return this.getEntityManager().find(getEntityClass(), key);
	}
}