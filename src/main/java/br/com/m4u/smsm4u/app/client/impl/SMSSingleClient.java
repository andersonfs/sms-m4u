/**
 *
 */
package br.com.m4u.smsm4u.app.client.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.m4u.smsm4u.app.business.impl.TextMessageBusinessImpl;
import br.com.m4u.smsm4u.app.client.IAPPClient;
import br.com.m4u.smsm4u.app.client.status.SmsStatusMessage;
import br.com.m4u.smsm4u.app.entity.TextMessage;
import io.swagger.client.ApiClient;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.Sms;

/**
 * @author AndersonFirmino
 *
 */
@Component
public class SMSSingleClient implements IAPPClient {

	private static final long serialVersionUID = 5414876299887893996L;
	private static final Logger LOG = Logger.getLogger(SMSSingleClient.class);

	@Autowired
	private TextMessageBusinessImpl business;

	@Override
	public SmsStatusMessage sendMenssage(String textMessage, Date validity, String originPhoneNumber, String destinationPhoneNumber) throws Exception {
		final TextMessage message = new TextMessage(textMessage, validity, originPhoneNumber, destinationPhoneNumber);
		if (business.isEntityValid(message)) {

			business.save(message);
			Sms sms = new Sms();
			sms.setBody(textMessage);
			sms.setId(message.getId());
			sms.setFrom(originPhoneNumber);
			sms.setTo(destinationPhoneNumber);
			ApiClient client = new ApiClient();
			DefaultApi api = new DefaultApi(client);
			try {
				api.sendSMS(sms);
				Integer status = client.getStatusCode();
				return SmsStatusMessage.getStatusByCode(status);
			} catch (Throwable exception) {
				LOG.error(exception);
			}
			return SmsStatusMessage.INTERNAL_SERVER_ERROR;
		} else {
			for (String errorMessage : business.getErrorsMessages()) {
				LOG.error(errorMessage);
			}
			return SmsStatusMessage.VALIDATION_EXCEPTION;
		}
	}
}