/**
 *
 */
package br.com.m4u.smsm4u.app.business;

import java.io.Serializable;
import java.util.List;

import br.com.m4u.smsm4u.app.business.exception.BusinessException;
import br.com.m4u.smsm4u.app.entity.IEntity;

/**
 * @author AndersonFirmino
 *
 */
@SuppressWarnings("rawtypes")
public interface IBusiness<E extends IEntity, K> extends Serializable {
	/**
	 *
	 * @param bean
	 * @throws BusinessException
	 */
	public void save(E bean) throws BusinessException;

	/**
	 *
	 * @param key
	 * @throws BusinessException
	 */
	public void delete(K key) throws BusinessException;

	/**
	 *
	 * @param key
	 * @return E
	 * @throws BusinessException
	 */
	public E findByKey(K key) throws BusinessException;

	/**
	 *
	 * @param bean
	 * @return {@link Boolean}
	 */
	public Boolean isEntityValid(E bean);

	/**
	 *
	 * @return {@link List}
	 */
	public List<String> getErrorsMessages();
}