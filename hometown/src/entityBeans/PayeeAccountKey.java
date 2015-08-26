package entityBeans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PayeeAccountKey implements Serializable
{
	@ManyToOne
	@JoinColumn(name="payeeid")
	private Payee payeeid;

	@ManyToOne
	@JoinColumn(name="personid")
	private Person personid;
	
	@Column
	private String payeeaccountno;

	private static final long serialVersionUID = 1L;

	public PayeeAccountKey()
	{
	  
	}
	
	public PayeeAccountKey(String accnum, Person person, Payee payee)
	{
    this.payeeaccountno = accnum;
    this.personid = person;
    this.payeeid = payee;
	}

	public String getPayeeAccountNo() {
		return this.payeeaccountno;
	}

	public void setPayeeAccountNo(String payeeaccountno) {
		this.payeeaccountno = payeeaccountno;
	}

	public Payee getPayeeid() {
		return this.payeeid;
	}

	public void setPayeeid(Payee payeeid) {
		this.payeeid = payeeid;
	}

	public Person getPersonid() {
		return this.personid;
	}

	public void setPersonid(Person personid) {
		this.personid = personid;
	}
	
	@Override
	public String toString()
	{		
		return payeeaccountno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((payeeaccountno == null) ? 0 : payeeaccountno.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PayeeAccountKey))
			return false;
		final PayeeAccountKey other = (PayeeAccountKey) obj;
		if (payeeaccountno == null) {
			if (other.payeeaccountno != null)
				return false;
		} else if (!payeeaccountno.equals(other.payeeaccountno))
			return false;
		return true;
	}
}
