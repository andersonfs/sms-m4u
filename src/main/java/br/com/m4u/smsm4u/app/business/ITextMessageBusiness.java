/**
 *
 */
package br.com.m4u.smsm4u.app.business;

import java.util.Date;
import java.util.List;

import br.com.m4u.smsm4u.app.business.exception.BusinessException;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * @author AndersonFirmino
 *
 */
public interface ITextMessageBusiness extends IBusiness<TextMessage, Long> {

	/**
	 *
	 * @param validity
	 * @return {@link List}
	 * @throws BusinessException
	 */
	public List<TextMessage> findTextMessagesByValidity(Date validity) throws BusinessException;
}
