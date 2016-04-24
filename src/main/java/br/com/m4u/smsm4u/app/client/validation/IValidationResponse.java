/**
 *
 */
package br.com.m4u.smsm4u.app.client.validation;

import java.io.Serializable;

/**
 * @author AndersonFirmino
 *
 */
public interface IValidationResponse<E> extends Serializable {
	public Boolean isValid(E bean);
	public String getMessage();
}
