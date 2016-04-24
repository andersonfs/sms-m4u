package br.com.m4u.smsm4u.integrationtest.dao;

import com.fitbur.testify.Cut;
import com.fitbur.testify.Fake;
import com.fitbur.testify.Module;
import com.fitbur.testify.integration.SpringIntegrationTest;

import br.com.m4u.smsm4u.app.SMSConfig;
import br.com.m4u.smsm4u.app.dao.service.TextMessageDAOServiceImpl;
import br.com.m4u.smsm4u.app.entity.TextMessage;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Date;

/**
 *
 * @author AndersonFirmino
 *
 */
@Module(SMSConfig.class)
@RunWith(SpringIntegrationTest.class)
public class TextMessageFakeTest {

	private static final String DESTINATION_PHONE_NUMBER = "21987198520";
	private static final String ORIGIN_PHONE_NUMBER = "21988941078";

    @Cut
    TextMessageDAOServiceImpl daoService;

    @Fake
    Provider<EntityManager> entityManagerProvider;

    @Test
    public void callToTextMessageShouldReturnHello() {
        //Arrange
        String text = "Hello";
        TextMessage entity = new TextMessage(text, new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        EntityManager entityManager = mock(EntityManager.class);

        given(entityManagerProvider.get()).willReturn(entityManager);
        willThrow(RuntimeException.class).given(entityManager).persist(entity);

        try {
            daoService.save(entity);
        } catch (RuntimeException e) {
            verify(entityManagerProvider).get();
            verify(entityManager).persist(entity);
        }
    }
}