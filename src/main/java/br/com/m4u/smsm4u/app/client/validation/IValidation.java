/**
 *
 */
package br.com.m4u.smsm4u.app.client.validation;

import java.io.Serializable;
import java.util.List;

/**
 * @author AndersonFirmino
 *
 */
public interface IValidation<E> extends Serializable {
	public Boolean validate(E bean);
	public List<String> getErrorsMesages();
}
