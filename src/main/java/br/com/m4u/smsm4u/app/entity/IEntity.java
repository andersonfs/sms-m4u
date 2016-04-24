/**
 *
 */
package br.com.m4u.smsm4u.app.entity;

import java.io.Serializable;

/**
 * @author AndersonFirmino
 *
 */
public interface IEntity<K extends Serializable> extends Serializable {
	public K getId();
	public void setId(K id);
}