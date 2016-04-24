package br.com.m4u.smsm4u.integrationtest.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fitbur.testify.Cut;
import com.fitbur.testify.Module;
import com.fitbur.testify.Real;
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
public class TextMessageDelegateTest extends AbstractTextMessageTest {

    private static final String DEFAULT_DESTINATION_PHONE_NUMBER = "21987198520";
	private static final String DEFAULT_ORIGIN_PHONE_NUMBER = "21988941978";
	private static final String DEFAULT_TEXT_MESSAGE = "hello it is an first text message";

	@Cut
    TextMessageDAOServiceImpl serviceDAO;

    @Real(true)
    Provider<EntityManager> entityManagerProvider;

    @Inject
    EntityManagerFactory entityManagerFactory;

    private TextMessage message;

    @Before
    public void buildEntity() {
    	Calendar today = GregorianCalendar.getInstance();
    	String textMessage = DEFAULT_TEXT_MESSAGE;
    	String originPhoneNumber = DEFAULT_ORIGIN_PHONE_NUMBER;
    	String destinationPhoneNumber= DEFAULT_DESTINATION_PHONE_NUMBER;
    	this.message = buildTextMessage(textMessage, today.getTime(), originPhoneNumber, destinationPhoneNumber);
    }

    @SuppressWarnings("unchecked")
	@Test
    public void givenTextMessageSaveShouldSavePhrase() {
        serviceDAO.save(message);

        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("SELECT e FROM TextMessage e");
        assertThat(query).isNotNull();
        List<TextMessage> entities = query.getResultList();
        assertThat(entities).hasSize(1);

        TextMessage entity = entities.get(0);
        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getText()).isEqualTo(DEFAULT_TEXT_MESSAGE);
        assertThat(entity.getOriginPhoneNumber()).isEqualTo(DEFAULT_ORIGIN_PHONE_NUMBER);
        assertThat(entity.getDestinationPhoneNumber()).isEqualTo(DEFAULT_DESTINATION_PHONE_NUMBER);
        em.close();

        verify(entityManagerProvider).get();
    }

    @Test
    public void givenHayeSaveShouldSavePhrase() {
        EntityManager entityManager = mock(EntityManager.class);
        given(entityManagerProvider.get()).willReturn(entityManager);
        willThrow(RuntimeException.class).given(entityManager).persist(message);

        try {
            serviceDAO.save(message);
        } catch (RuntimeException exception) {
            verify(entityManagerProvider).get();
            verify(entityManager).persist(message);
        }
    }
}