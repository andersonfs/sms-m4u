/**
 *
 */
package br.com.m4u.smsm4u.app.client;

import java.io.Serializable;
import java.util.Date;

import br.com.m4u.smsm4u.app.client.status.SmsStatusMessage;

/**
 * @author AndersonFirmino
 *
 */
public interface IAPPClient extends Serializable {
	public SmsStatusMessage sendMenssage(String textMessage, Date validity, String originPhoneNumber, String destinationPhoneNumber) throws Exception;
}
