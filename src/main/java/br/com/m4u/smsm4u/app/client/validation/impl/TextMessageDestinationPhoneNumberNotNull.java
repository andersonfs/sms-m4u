/**
 *
 */
package br.com.m4u.smsm4u.app.client.validation.impl;

import org.apache.commons.lang.StringUtils;

import br.com.m4u.smsm4u.app.client.validation.IValidationResponse;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * @author AndersonFirmino
 *
 */
public class TextMessageDestinationPhoneNumberNotNull extends AbstractTextMessageValidation
		implements IValidationResponse<TextMessage> {

	private static final long serialVersionUID = -3411970424853984543L;

	@Override
	public Boolean isValid(TextMessage bean) {
		if (isEntityNull(bean)) {
			return Boolean.FALSE;
		}

		if (bean.getDestinationPhoneNumber() == null || StringUtils.EMPTY.equals(bean.getDestinationPhoneNumber().trim())) {
			this.message = "Destination Phone Number must be typed.";
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}