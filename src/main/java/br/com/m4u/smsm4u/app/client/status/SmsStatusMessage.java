/**
 *
 */
package br.com.m4u.smsm4u.app.client.status;

import java.io.Serializable;

/**
 * @author AndersonFirmino
 *
 */
public enum SmsStatusMessage implements Serializable {
	OK(201, "SMS sent"),
	NOT_FOUND(404, "Mobile User not found"),
	VALIDATION_EXCEPTION(405, "Validation exception"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	NO_RECOGNIZE(0, "Status not found.");

	private Integer code;
	private String message;

	private SmsStatusMessage(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	/**
	 *
	 * @param code
	 * @return SmsStatusMessage
	 */
	public static SmsStatusMessage getStatusByCode(Integer code) {
		for (SmsStatusMessage status : values()) {
			if (status.code == code) {
				return status;
			}
		}
		return SmsStatusMessage.NO_RECOGNIZE;
	}

	@Override
	public String toString() {
		StringBuilder status = new StringBuilder("Code=");
		status.append(this.code).append(" - Message=");
		status.append(this.message);
		return status.toString();
	}
}