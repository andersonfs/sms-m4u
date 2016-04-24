/**
 *
 */
package br.com.m4u.smsm4u.app.business.exception;

import java.util.LinkedList;
import java.util.List;

/**
 * @author AndersonFirmino
 *
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 4230165896982221834L;

	private List<String> errorsMessages;

	/**
	 *
	 * @return {@link List}
	 */
	public List<String> getErrorsMessages() {
		return errorsMessages;
	}

	/**
	 *
	 * @param errorMessage
	 */
	public void addErrorMessage(String errorMessage) {
		if (this.errorsMessages == null) {
			this.errorsMessages = new LinkedList<String>();
		}
		errorsMessages.add(errorMessage);
	}
}