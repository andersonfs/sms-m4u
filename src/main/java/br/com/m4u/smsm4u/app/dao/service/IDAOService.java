package br.com.m4u.smsm4u.app.dao.service;

import java.io.Serializable;

import br.com.m4u.smsm4u.app.entity.IEntity;
/**
 * Interface que define o contrato para o serviço de persistência.
 *
 * @author AndersonFirmino
 *
 * @param <E> Entidade persistente
 * @param <K> Tipo da chave primária
 *
 */
@SuppressWarnings("rawtypes")
public interface IDAOService<E extends IEntity, K> extends Serializable {
	public void save(E bean);
	public void delete(K key);
	public E findById(K key);
	public Class<E> getEntityClass();
}