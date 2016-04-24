package br.com.m4u.smsm4u.app.dao.service;

import java.util.Date;
import java.util.List;

import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * Interface que define o contrato para o serviço de persistência da classe TextMessage
 *
 * @author AndersonFirmino
 *
 */
public interface ITextMessageDAOService extends IDAOService<TextMessage, Long> {
	public List<TextMessage> findByValidity(Date validity);
}
