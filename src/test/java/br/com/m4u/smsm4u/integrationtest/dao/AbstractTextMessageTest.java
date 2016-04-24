/**
 *
 */
package br.com.m4u.smsm4u.integrationtest.dao;

import java.util.Date;

import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * @author AndersonFirmino
 *
 */
public abstract class AbstractTextMessageTest {
	protected TextMessage buildTextMessage(String text, Date validity, String originPhoneNumber, String destinationPhoneNumber) {
    	TextMessage message = new TextMessage(text, validity, originPhoneNumber, destinationPhoneNumber);
    	return message;
    }
}
