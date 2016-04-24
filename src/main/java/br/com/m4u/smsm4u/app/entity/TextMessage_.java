package br.com.m4u.smsm4u.app.entity;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Classe metamodelo de TextMessage para ser usado em consultas tipadas.
 *
 * @author AndersonFirmino
 *
 */
@StaticMetamodel(TextMessage.class)
public class TextMessage_ {
	public static volatile SingularAttribute<TextMessage_, Long> id;
	public static volatile SingularAttribute<TextMessage_, String> text;
	public static volatile SingularAttribute<TextMessage, Date> validity;
	public static volatile SingularAttribute<TextMessage, String> originPhoneNumber;
	public static volatile SingularAttribute<TextMessage, String> destinationPhoneNumber;
}