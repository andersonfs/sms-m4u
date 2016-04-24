package br.com.m4u.smsm4u.integrationtest.dao;

import static org.assertj.core.api.Assertions.assertThat;

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

import br.com.m4u.smsm4u.app.SMSConfig;
import br.com.m4u.smsm4u.app.dao.service.TextMessageDAOServiceImpl;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 *
 * @author AndersonFirmino
 *
 */
@Module(SMSConfig.class)
@RunWith(SpringIntegrationTest.class)
public class TextMessageRealTest extends AbstractTextMessageTest{

	private static final String DESTINATION_PHONE_NUMBER = "21987198520";
	private static final String ORIGIN_PHONE_NUMBER = "21988941078";

    @Cut
    TextMessageDAOServiceImpl daoService;

    @Inject
    EntityManagerFactory entityManagerFactory;

    @SuppressWarnings("unchecked")
	@Test
    public void persistTextMessageShouldReturnFirstText() {
        String text = "First Text";
        Date today = new Date();
		TextMessage message = buildTextMessage(text, today, ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
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
        assertThat(entity.getText()).isEqualTo(text);
        String validityFormatted = dateFormat.format(entity.getValidity());
        assertThat(validityFormatted).isEqualTo(todayFormatted);
        assertThat(entity.getOriginPhoneNumber()).isEqualTo(ORIGIN_PHONE_NUMBER);
        assertThat(entity.getDestinationPhoneNumber()).isEqualTo(DESTINATION_PHONE_NUMBER);
        em.close();
    }
}