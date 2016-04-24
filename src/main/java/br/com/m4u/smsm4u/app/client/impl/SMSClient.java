/**
 *
 */
package br.com.m4u.smsm4u.app.client.impl;

import java.util.Date;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.m4u.smsm4u.app.business.ITextMessageBusiness;
import br.com.m4u.smsm4u.app.client.IAPPClient;
import br.com.m4u.smsm4u.app.client.IAPPResult;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * @author AndersonFirmino
 *
 */
public class SMSClient implements IAPPClient {

	private static final long serialVersionUID = 5414876299887893996L;
	private static final Logger LOG = Logger.getLogger(SMSClient.class);

	@Inject
	private ITextMessageBusiness business;

	@Override
	public IAPPResult sendMenssage(String textMessage, Date validity, String originPhoneNumber, String destinationPhoneNumber) throws Exception {
		final TextMessage message = new TextMessage(textMessage, validity, originPhoneNumber, destinationPhoneNumber);
		if (business.isEntityValid(message)) {
			business.save(message);
			//TODO - Enviar o SMS
		} else {
			for (String errorMessage : business.getErrorsMessages()) {
				LOG.error(errorMessage);
			}
			throw new Exception("Validations erros ocurred");
		}
		return null;
	}
}