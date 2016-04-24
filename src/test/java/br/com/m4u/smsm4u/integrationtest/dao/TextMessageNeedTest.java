package br.com.m4u.smsm4u.integrationtest.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.fitbur.testify.Cut;
import com.fitbur.testify.Module;
import com.fitbur.testify.integration.SpringIntegrationTest;
import com.fitbur.testify.need.Need;
import com.fitbur.testify.need.hsql.InMemoryHSQL;

import br.com.m4u.smsm4u.app.SMSConfig;
import br.com.m4u.smsm4u.app.dao.service.TextMessageDAOServiceImpl;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 *
 * @author AndersonFirmino
 *
 */
@Module(SMSConfig.class)
@Need(InMemoryHSQL.class)
@RunWith(SpringIntegrationTest.class)
public class TextMessageNeedTest extends AbstractTextMessageTest {

	private static final String DESTINATION_PHONE_NUMBER = "21987198520";
	private static final String ORIGIN_PHONE_NUMBER = "21988941078";

    @Cut
    TextMessageDAOServiceImpl daoService;

    @Inject
    EntityManagerFactory entityManagerFactory;

    @SuppressWarnings("unchecked")
	@Test
    public void givenHelloTextMessageShouldSaveHello() {
        String textMessage = "Hello";
        Date today = new Date();
        TextMessage message = buildTextMessage(textMessage, today, ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        SimpleDateFormat dateFormat = new SimpleDateFormat(TextMessage.VALIDITY_DATE_FORMAT);
		String todayFormatted = dateFormat.format(today);
        daoService.save(message);

        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("SELECT e FROM TextMessage e");
        assertThat(query).isNotNull();
        List<TextMessage> entities = query.getResultList();
        assertThat(entities).hasSize(1);

        TextMessage entity = entities.get(0);
        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getText()).isEqualTo(textMessage);
        String validityFormatted = dateFormat.format(entity.getValidity());
        assertThat(validityFormatted).isEqualTo(todayFormatted);
        assertThat(entity.getOriginPhoneNumber()).isEqualTo(ORIGIN_PHONE_NUMBER);
        assertThat(entity.getDestinationPhoneNumber()).isEqualTo(DESTINATION_PHONE_NUMBER);
        em.close();
    }

    @Test
    public void searchTextMessageShouldReturnMessage() {
    	String textMessage = "Hello";
        Date today = new Date();
        TextMessage message = buildTextMessage(textMessage, today, ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        daoService.save(message);

        List<TextMessage> entities = daoService.findByValidity(today);
        assertThat(entities).hasSize(1);
        TextMessage entity = entities.get(0);
        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getText()).isEqualTo(textMessage);
    }

    @Test
    public void searchMoreTahOneTextMessagesShouldReturnMoreThanOneMessages() {
    	String textMessage = "Hello";
        Date today = new Date();
        TextMessage message = buildTextMessage(textMessage, today, ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        daoService.save(message);

        textMessage = "Another Hello Message";
        message = buildTextMessage(textMessage, today, ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        daoService.save(message);

        List<TextMessage> entities = daoService.findByValidity(today);
        assertThat(entities).hasSize(2);

        for (TextMessage entity : entities) {
        	assertThat(entity.getId()).isNotNull();
		}
    }

    @Test
    public void deleteTextMessagesShouldDeleteMessage() {
    	String textMessage = "Hello For delete test";
        Date today = new Date();
        TextMessage message = buildTextMessage(textMessage, today, ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        daoService.save(message);

        TextMessage entity = daoService.findById(message.getId());
        assertNotNull(entity);
        Long id = entity.getId();
        try {
        	daoService.delete(id);
        	entity = daoService.findById(id);
        	assertNull(entity);
        } catch (Throwable exception) {
            fail("An error ocurred when a TextMessage deletion was invoked");
        }
    }

}