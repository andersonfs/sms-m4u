package br.com.m4u.smsm4u.integrationtest.dao.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.fitbur.testify.Module;
import com.fitbur.testify.integration.SpringIntegrationTest;
import com.fitbur.testify.need.Need;
import com.fitbur.testify.need.hsql.InMemoryHSQL;

import br.com.m4u.smsm4u.app.SMSConfig;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 *
 * @author AndersonFirmino
 *
 */
@Module(SMSConfig.class)
@Need(InMemoryHSQL.class)
@RunWith(SpringIntegrationTest.class)
public class TextMessageEntityTest {

    private static final String DESTINATION_PHONE_NUMBER = "21987198520";
	private static final String ORIGIN_PHONE_NUMBER = "21988941078";

	@Inject
    EntityManagerFactory entityManagerFactory;

    @Test(expected = PersistenceException.class)
    public void givenNullTextPersistShouldThrowException() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TextMessage entity = new TextMessage(null, new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
            entityManager.persist(entity);
        } finally {
            entityManager.close();
        }
    }

    @Test
    public void givenEmptyTextMessagePersistShouldPersistEmptyTextMessage() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        TextMessage entity = new TextMessage(StringUtils.EMPTY, new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);

        tx.begin();
        entityManager.persist(entity);
        tx.commit();
        entityManager.close();
        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getText()).isEqualTo(StringUtils.EMPTY);
    }

    @Test
    public void givenHelloTextMessagePersistShouldPersistHello() {
        String textMessage = "Hello";
		TextMessage entity = new TextMessage(textMessage, new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(entity);
        tx.commit();
        entityManager.close();

        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getText()).isEqualTo(textMessage);
    }

    @Test
    public void givenExistingTextMessagePersistShouldPersist() {
        TextMessage entity = new TextMessage(StringUtils.EMPTY, new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(entity);
        tx.commit();
        assertThat(entity.getId()).isNotNull();

        String textMessage = "Hello";
        TextMessage updateEntity = new TextMessage(entity.getId(), textMessage, new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);

        entityManager = entityManagerFactory.createEntityManager();
        tx = entityManager.getTransaction();
        tx.begin();
        TextMessage result = entityManager.merge(updateEntity);
        tx.commit();
        entityManager.close();

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(entity.getId());
        assertThat(result.getText()).isEqualTo(textMessage);
    }

    @Test
    public void givenEqualEntitiesTheyShouldBeEqual() {
    	final Long id = Mockito.anyLong();
    	TextMessage entity1 = new TextMessage(id, "Hello", new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        TextMessage entity2 = new TextMessage(id, "Hello", new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        assertThat(entity1).isEqualTo(entity2);
    }

    @Test
    public void givenNullAndAnEntityTheyShouldNotBeEqual() {
    	final Long id = Mockito.anyLong();
        TextMessage entity1 = new TextMessage(id, "Hello", new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        TextMessage entity2 = null;
        assertThat(entity1).isNotEqualTo(entity2);
    }

    @Test
    public void givenDifferentObjectAndAnEntityTheyShouldNotBeEqual() {
        final Long id = Mockito.anyLong();
        TextMessage entity1 = new TextMessage(id, "Hello", new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        Object entity2 = new Object();
        assertThat(entity1).isNotEqualTo(entity2);
    }

    @Test
    public void givenSameEntityItShouldBeEqualToItself() {
    	final Long id = Mockito.anyLong();
        TextMessage entity = new TextMessage(id, "Hello", new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        assertThat(entity).isEqualTo(entity);
    }

    @Test
    public void givenEqualEntitiesTheyShouldHaveEqualHashCode() {
    	final Long id = Mockito.anyLong();
        TextMessage entity1 = new TextMessage(id, "Hello", new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        TextMessage entity2 = new TextMessage(id, "Hello", new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
    }

    @Test
    public void callToToStringShouldReturnEntityDescription() {
    	final Long id = Mockito.anyLong();
        TextMessage entity = new TextMessage(id, "Hello", new Date(), ORIGIN_PHONE_NUMBER, DESTINATION_PHONE_NUMBER);
        assertThat(entity.toString()).contains("1", "Hello");
    }
}