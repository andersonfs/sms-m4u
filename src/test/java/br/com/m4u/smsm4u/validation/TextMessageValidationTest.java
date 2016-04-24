/**
 *
 */
package br.com.m4u.smsm4u.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.fitbur.testify.Cut;
import com.fitbur.testify.integration.SpringIntegrationTest;

import br.com.m4u.smsm4u.app.client.validation.impl.TextMessageValidation;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * @author AndersonFirmino
 *
 */
@RunWith(SpringIntegrationTest.class)
public class TextMessageValidationTest implements Serializable {

	private static final long serialVersionUID = 859272747828497609L;
	private static final String BIG_MESSAGE = "This message is for validate an sms message wich must be less than one hundred and sixty chacarters. And for it, we will create this big message for validation and integration tests.";

	@Cut
	private TextMessageValidation validation;

	@Test
	public void validationTextMessageNullShouldReturnFalse() {
		TextMessage message = null;
		assertFalse(validation.validate(message));
	}

	@Test
	public void validationTextMessageEmptyMessageShouldReturnFalse() {
		TextMessage message = new TextMessage("", new Date(), "21999990000", "21999990000");
		assertFalse(validation.validate(message));
	}

	@Test
	public void validationTextMessageCharactersLimitShouldReturnFalse() {
		TextMessage message = new TextMessage(BIG_MESSAGE, new Date(), "21999990000", "21999990000");
		assertFalse(validation.validate(message));
	}

	@Test
	public void validationTextMessageValidityNullShouldReturnFalse() {
		TextMessage message = new TextMessage("hello", null, "21999990000", "21999990000");
		assertFalse(validation.validate(message));
	}

	@Test
	public void validationTextMessageOriginPhoneNumberNullShouldReturnFalse() {
		TextMessage message = new TextMessage("hello", new Date(), null, "21999990000");
		assertFalse(validation.validate(message));
	}

	@Test
	public void validationTextMessageOriginPhoneNumberEmptyShouldReturnFalse() {
		TextMessage message = new TextMessage("hello", new Date(), "", "21999990000");
		assertFalse(validation.validate(message));
	}

	@Test
	public void validationTextMessageDestinationPhoneNumberNullShouldReturnFalse() {
		TextMessage message = new TextMessage("hello", new Date(), "21999990000", null);
		assertFalse(validation.validate(message));
	}

	@Test
	public void validationTextMessageDestinationPhoneNumberEmptyShouldReturnFalse() {
		TextMessage message = new TextMessage("hello", new Date(), "21999990000", "");
		assertFalse(validation.validate(message));
	}

	@Test
	public void validationTextMessageWithValidityAndOriginPhoneNumberNullShouldReturnTwoErrorsMessages() {
		TextMessage message = new TextMessage("hello", null, null, "21989990099");
		assertFalse(validation.validate(message));
		List<String> errorsMessages = validation.getErrorsMesages();
		assertNotNull(errorsMessages);
		assertThat(errorsMessages).hasSize(2);
	}

	@Test
	public void validationTextMessageWithValidityAndNoOnePhoneNumbersNullShouldReturnThreeErrorsMessages() {
		TextMessage message = new TextMessage("hello", null, null, null);
		assertFalse(validation.validate(message));
		List<String> errorsMessages = validation.getErrorsMesages();
		assertNotNull(errorsMessages);
		assertThat(errorsMessages).hasSize(3);
	}
}