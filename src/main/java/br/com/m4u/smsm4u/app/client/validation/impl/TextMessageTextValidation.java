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
public class TextMessageTextValidation extends AbstractTextMessageValidation implements IValidationResponse<TextMessage> {

	private static final long serialVersionUID = -4088243173281625997L;

	@Override
	public Boolean isValid(TextMessage bean) {
		if (isEntityNull(bean)) {
			return Boolean.FALSE;
		}

		if (bean.getText() == null || StringUtils.EMPTY.equals(bean.getText().trim())) {
			this.message = "Text Message must be typed.";
			return Boolean.FALSE;
		}

		if (bean.getText().length() > 160) {
			this.message = "Text Message length must be less or equal than 160 characters.";
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}