package br.com.m4u.smsm4u.app.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;

/**
 * TextMessage Entity.
 *
 * @author Anderson Firmino
 */
@Entity
@Table
public class TextMessage implements IEntity<Long>{

	public static final String VALIDITY_DATE_FORMAT = "dd/MM/yyyy";
	private static final long serialVersionUID = 6302902262691635672L;
	private Long id;
    private String text;
    private Date validity;
    private String originPhoneNumber;
    private String destinationPhoneNumber;

    public TextMessage() {
    	this.text = StringUtils.EMPTY;
    	this.validity = new Date();
    	this.originPhoneNumber = null;
    	this.destinationPhoneNumber = null;
	}

    public TextMessage(String text, Date validity, String originPhoneNumber, String destinationPhoneNumber) {
    	this.text = text;
    	this.validity = validity;
    	this.originPhoneNumber = originPhoneNumber;
    	this.destinationPhoneNumber = destinationPhoneNumber;
    }

    public TextMessage(Long id, String text, Date validity, String originPhoneNumber, String destinationPhoneNumber) {
    	this.id = id;
    	this.text = text;
    	this.validity = validity;
    	this.originPhoneNumber = originPhoneNumber;
    	this.destinationPhoneNumber = destinationPhoneNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 160, nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getValidity() {
		return validity;
	}

    public void setValidity(Date validity) {
		this.validity = validity;
	}

    @Column(nullable = false)
    public String getOriginPhoneNumber() {
		return originPhoneNumber;
	}

    public void setOriginPhoneNumber(String originPhoneNumber) {
		this.originPhoneNumber = originPhoneNumber;
	}

    @Column(nullable = false)
    public String getDestinationPhoneNumber() {
		return destinationPhoneNumber;
	}

    public void setDestinationPhoneNumber(String destinationPhoneNumber) {
		this.destinationPhoneNumber = destinationPhoneNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TextMessage)) {
			return false;
		}
		TextMessage other = (TextMessage) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
    public String toString() {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(VALIDITY_DATE_FORMAT);
		final StringBuilder label = new StringBuilder("TextMessage{");
		label.append("id=").append(this.id).append(",");
		label.append("text=").append(this.text).append(",");
		label.append("validade=").append(dateFormat.format(this.validity)).append(",");
		label.append("originPhoneNumber=").append(this.originPhoneNumber).append(",");
		label.append("destinationPhoneNumber=").append(this.destinationPhoneNumber).append("}");
        return label.toString();
    }
}