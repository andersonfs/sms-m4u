package br.com.m4u.smsm4u.app.dao.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import br.com.m4u.smsm4u.app.entity.TextMessage;
import br.com.m4u.smsm4u.app.entity.TextMessage_;

/**
 * Serviço de persistência de uma mensagem de texto.
 *
 * @author AndersonFirmino
 *
 */
@Named
@Singleton
@Transactional
public class TextMessageDAOServiceImpl extends AbstractDAOService<TextMessage, Long>  implements ITextMessageDAOService{

	private static final long serialVersionUID = 2049429321074715744L;

	@Inject
	TextMessageDAOServiceImpl(Provider<EntityManager> entityManagerProvider) {
		super(entityManagerProvider);
	}

	@Override
	public List<TextMessage> findByValidity(Date validity) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<TextMessage> query = builder.createQuery(TextMessage.class);
		final Root<TextMessage> root = query.from(TextMessage.class);
		final Predicate condition = builder.equal(root.get(TextMessage_.validity), validity);
		query.where(condition);
		final TypedQuery<TextMessage> typedQuery = this.getEntityManager().createQuery(query);
		List<TextMessage> result = typedQuery.getResultList();
		return result;
	}

	@Override
	public Class<TextMessage> getEntityClass() {
		return TextMessage.class;
	}
}