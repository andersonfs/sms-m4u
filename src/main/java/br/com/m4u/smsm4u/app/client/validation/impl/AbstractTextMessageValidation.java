/**
 *
 */
package br.com.m4u.smsm4u.app.client.validation.impl;

import java.io.Serializable;

import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * @author AndersonFirmino
 *
 */
public abstract class AbstractTextMessageValidation implements Serializable {

	private static final long serialVersionUID = -8705984517716769423L;

	private TextMessageEntityNotNull validation = new TextMessageEntityNotNull();
	protected String message;

	protected Boolean isEntityNull(TextMessage bean) {
		if (!validation.isValid(bean)) {
			this.message = validation.getMessage();
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public String getMessage() {
		return message;
	}
}