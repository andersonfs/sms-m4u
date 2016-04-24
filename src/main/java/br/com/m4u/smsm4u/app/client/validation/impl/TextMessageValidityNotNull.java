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
public class TextMessageValidityNotNull extends AbstractTextMessageValidation implements IValidationResponse<TextMessage> {

	private static final long serialVersionUID = -9030493147709556302L;

	@Override
	public Boolean isValid(TextMessage bean) {
		if (isEntityNull(bean)) {
			return Boolean.FALSE;
		}

		if (bean.getValidity() == null) {
			this.message = "Validity must be typed;";
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}



}
