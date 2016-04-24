package br.com.m4u.smsm4u.integrationtest.dao;

import static org.assertj.core.api.Assertions.assertThat;

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
public class TextMessageNeedContainerTest extends AbstractTextMessageTest {

	private static final String DESTINATION_PHONE_NUMBER = "21987198520";
	private static final String ORIGIN_PHONE_NUMBER = "21988941078";

    @Cut
    TextMessageDAOServiceImpl daoService;

    @Inject
    EntityManagerFactory entityManagerFactory;

    @SuppressWarnings("unchecked")
	@Test
    public void givenHelloTextMessageShouldSaveHello() {
        String text = "Hello";
        TextMessage message = buildTextMessage(text, new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);

        daoService.save(message);

        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("SELECT e FROM TextMessage e");
        assertThat(query).isNotNull();
        List<TextMessage> entities = query.getResultList();
        assertThat(entities).hasSize(1);

        TextMessage entity = entities.get(0);
        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getText()).isEqualTo(text);
        em.close();
    }
}