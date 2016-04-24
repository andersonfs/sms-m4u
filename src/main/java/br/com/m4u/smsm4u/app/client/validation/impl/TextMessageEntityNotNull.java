/**
 *
 */
package br.com.m4u.smsm4u.app.client.validation.impl;

import br.com.m4u.smsm4u.app.client.validation.IValidationResponse;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * @author AndersonFirmino
 *
 */
public class TextMessageEntityNotNull implements IValidationResponse<TextMessage> {

	private static final long serialVersionUID = -1693497301179736473L;

	@Override
	public Boolean isValid(TextMessage bean) {
		if (bean == null) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public String getMessage() {
		return "Entity Text Message Null. Please verify the code.";
	}
}