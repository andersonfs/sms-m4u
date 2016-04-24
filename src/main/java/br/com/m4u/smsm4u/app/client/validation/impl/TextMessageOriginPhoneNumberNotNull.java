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
public class TextMessageOriginPhoneNumberNotNull extends AbstractTextMessageValidation
		implements IValidationResponse<TextMessage> {

	private static final long serialVersionUID = 6676968468524521303L;

	@Override
	public Boolean isValid(TextMessage bean) {
		if (isEntityNull(bean)) {
			return Boolean.FALSE;
		}

		if (bean.getOriginPhoneNumber() == null || StringUtils.EMPTY.equals(bean.getOriginPhoneNumber().trim())) {
			this.message = "Origin Phone Number must be typed.";
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}