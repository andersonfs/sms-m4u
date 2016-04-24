/**
 *
 */
package br.com.m4u.smsm4u.integrationtest.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.fitbur.testify.Cut;
import com.fitbur.testify.Module;
import com.fitbur.testify.integration.SpringIntegrationTest;
import com.fitbur.testify.need.Need;
import com.fitbur.testify.need.hsql.InMemoryHSQL;

import br.com.m4u.smsm4u.app.SMSConfig;
import br.com.m4u.smsm4u.app.business.exception.BusinessException;
import br.com.m4u.smsm4u.app.business.impl.TextMessageBusinessImpl;
import br.com.m4u.smsm4u.app.client.validation.impl.TextMessageValidation;
import br.com.m4u.smsm4u.app.dao.service.TextMessageDAOServiceImpl;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * @author AndersonFirmino
 *
 */
@Module(SMSConfig.class)
@Need(InMemoryHSQL.class)
@RunWith(SpringIntegrationTest.class)
public class TextMessageBusinessTest implements Serializable {

	private static final long serialVersionUID = -6232871200601438235L;

	@Cut
	private TextMessageBusinessImpl business;

	@Inject
    EntityManagerFactory entityManagerFactory;

	@Inject
	TextMessageDAOServiceImpl daoService;

	@Inject
	TextMessageValidation validation;

	@Test
	public void saveTest() {
		TextMessage message = new TextMessage("hello", new Date(), "21988941978", "21987198520");
		try {
			business.save(message);
			assertNotNull(message.getId());
		} catch (BusinessException exception) {
			fail("An error ocurred when tried save an Text Message entity");
		}
	}

	@Test
	public void findByIdTest() {
		TextMessage message = new TextMessage("hello", new Date(), "21988941978", "21987198520");
		try {
			business.save(message);
			Long id = message.getId();
			assertNotNull(id);
			TextMessage entity = business.findByKey(id);
			assertNotNull(entity);
			assertNotNull(entity.getId());
			assertThat(entity.getId()).isEqualTo(id);
		} catch (BusinessException exception) {
			fail("An error ocurred when tried save an Text Message entity");
		}
	}

	@Test
	public void deleteTest() {
		TextMessage message = new TextMessage("hello", new Date(), "21988941978", "21987198520");
		try {
			business.save(message);
			Long id = message.getId();
			assertNotNull(id);
			business.delete(id);
			TextMessage entity = business.findByKey(id);
			assertNull(entity);
		} catch (BusinessException exception) {
			fail("An error ocurred when tried save an Text Message entity");
		}
	}

	@Test
	public void findByValidityTest() {
		Date today = new Date();
		TextMessage message = new TextMessage("hello", today, "21988941978", "21987198520");
		TextMessage anotherMessage = new TextMessage("another hello message", today, "21988941978", "21987198520");
		try {
			business.save(message);
			assertNotNull(message.getId());
			business.save(anotherMessage);
			assertNotNull(anotherMessage.getId());

			List<TextMessage> entities = business.findTextMessagesByValidity(today);
			assertNotNull(entities);
			assertThat(entities).hasSize(2);
		} catch (BusinessException exception) {
			fail("An error ocurred when tried save an Text Message entity");
		}
	}

	@Test
	public void validationTestShouldBeTrue() {
		TextMessage message = new TextMessage("hello", new Date(), "21978990606", "21988007878");
		Boolean valid = business.isEntityValid(message);
		assertTrue(valid);
		List<String> errorsMessages = business.getErrorsMessages();
		assertTrue(errorsMessages.isEmpty());
	}

	@Test
	public void validationTestShouldBeFalse() {
		TextMessage message = new TextMessage(null, null, null, null);
		Boolean valid = business.isEntityValid(message);
		assertFalse(valid);
		List<String> errorsMessages = business.getErrorsMessages();
		assertFalse(errorsMessages.isEmpty());
		assertThat(errorsMessages).hasSize(4);
	}
}
