/**
 *
 */
package br.com.m4u.smsm4u.app.business;

import java.util.List;

import br.com.m4u.smsm4u.app.business.exception.BusinessException;
import br.com.m4u.smsm4u.app.client.validation.IValidation;
import br.com.m4u.smsm4u.app.dao.service.IDAOService;
import br.com.m4u.smsm4u.app.entity.IEntity;

/**
 * @author AndersonFirmino
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractBusiness<E extends IEntity, K> implements IBusiness<E, K> {

	private static final long serialVersionUID = 1328662741017831021L;

	protected IDAOService daoService;
	protected IValidation validation;

	public AbstractBusiness(IDAOService daoService, IValidation validation) {
		this.validation = validation;
		this.daoService = daoService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(E bean) throws BusinessException {
		daoService.save(bean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(K key) throws BusinessException {
		daoService.delete(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E findByKey(K key) throws BusinessException {
		return (E) daoService.findById(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean isEntityValid(E bean) {
		return validation.validate(bean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getErrorsMessages() {
		return validation.getErrorsMesages();
	}
}