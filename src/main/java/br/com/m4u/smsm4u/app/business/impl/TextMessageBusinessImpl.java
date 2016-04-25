/**
 *
 */
package br.com.m4u.smsm4u.app.business.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.m4u.smsm4u.app.business.AbstractBusiness;
import br.com.m4u.smsm4u.app.business.ITextMessageBusiness;
import br.com.m4u.smsm4u.app.business.exception.BusinessException;
import br.com.m4u.smsm4u.app.client.validation.ITextMessageValidation;
import br.com.m4u.smsm4u.app.dao.service.ITextMessageDAOService;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * @author AndersonFirmino
 *
 */
@Service
public class TextMessageBusinessImpl extends AbstractBusiness<TextMessage, Long> implements ITextMessageBusiness {

	private static final long serialVersionUID = -1376742752186582708L;

	@Inject
	public TextMessageBusinessImpl(ITextMessageDAOService daoService, ITextMessageValidation validation) {
		super(daoService, validation);
	}

	@Override
	public List<TextMessage> findTextMessagesByValidity(Date validity) throws BusinessException {
		return ((ITextMessageDAOService)daoService).findByValidity(validity);
	}
}