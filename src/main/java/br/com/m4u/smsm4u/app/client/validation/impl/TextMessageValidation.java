/**
 *
 */
package br.com.m4u.smsm4u.app.client.validation.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Named;

import br.com.m4u.smsm4u.app.client.validation.ITextMessageValidation;
import br.com.m4u.smsm4u.app.client.validation.IValidationResponse;
import br.com.m4u.smsm4u.app.entity.TextMessage;

/**
 * @author AndersonFirmino
 *
 */
@Named
public class TextMessageValidation implements ITextMessageValidation {

	private static final long serialVersionUID = -8622955504863713522L;

	private List<IValidationResponse<TextMessage>> validationChain;
	private List<String> errorsMessages = new LinkedList<String>();

	public TextMessageValidation() {
		this.validationChain = new LinkedList<IValidationResponse<TextMessage>>();
		this.validationChain.add(new TextMessageEntityNotNull());
		this.validationChain.add(new TextMessageTextValidation());
		this.validationChain.add(new TextMessageValidityNotNull());
		this.validationChain.add(new TextMessageOriginPhoneNumberNotNull());
		this.validationChain.add(new TextMessageDestinationPhoneNumberNotNull());
	}

	@Override
	public Boolean validate(TextMessage bean) {
		Boolean isValid = Boolean.TRUE;
		for (IValidationResponse<TextMessage> chain : this.validationChain) {
			if (!chain.isValid(bean)){
				this.errorsMessages.add(chain.getMessage());
				isValid = Boolean.FALSE;
			}
		}
		return isValid;
	}

	@Override
	public List<String> getErrorsMesages() {
		return this.errorsMessages;
	}
}