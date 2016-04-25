/**
 *
 */
package br.com.m4u.smsm4u.integrationtest.client;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.fitbur.testify.Cut;
import com.fitbur.testify.Module;
import com.fitbur.testify.integration.SpringIntegrationTest;

import br.com.m4u.smsm4u.app.SMSConfig;
import br.com.m4u.smsm4u.app.business.impl.TextMessageBusinessImpl;
import br.com.m4u.smsm4u.app.client.impl.SMSSingleClient;
import br.com.m4u.smsm4u.app.client.status.SmsStatusMessage;
import br.com.m4u.smsm4u.app.client.validation.impl.TextMessageValidation;
import br.com.m4u.smsm4u.app.dao.service.TextMessageDAOServiceImpl;

/**
 * @author AndersonFirmino
 *
 */
@Module(SMSConfig.class)
@RunWith(SpringIntegrationTest.class)
public class SMSSingleClientTest implements Serializable {

	private static final long serialVersionUID = 3775413532598203133L;

	@Cut
	SMSSingleClient client;

	@Inject
	TextMessageBusinessImpl business;

	@Inject
    EntityManagerFactory entityManagerFactory;

	@Inject
	TextMessageDAOServiceImpl daoService;

	@Inject
	TextMessageValidation validation;

	@Test
	public void sendARealMessageTest() {
		String from = "21988941978";
		String to = "21985208719";
		String message = "Hello this is an test";
		try {
			SmsStatusMessage status = client.sendMenssage(message, new Date(), from, to);
			assertNotNull(status);
			assertEquals(SmsStatusMessage.OK, status);
		} catch (Exception e) {
			fail("An error ocurred while a message was sent.");
		}
	}

	@Test
	public void sendNoPhoneNumbersShouldReturnValidationError() {
		String message = "Hello this is an test";
		try {
			SmsStatusMessage status = client.sendMenssage(message, new Date(), null, null);
			assertNotNull(status);
			assertEquals(SmsStatusMessage.VALIDATION_EXCEPTION, status);
		} catch (Exception e) {
			fail("An error ocurred while a message was sent.");
		}
	}
}