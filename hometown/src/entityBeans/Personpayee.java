package entityBeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Personpayee implements Serializable {
	@Id
	@GeneratedValue
	private int personpayeeid;

	private String payeeaccountno;

	@ManyToOne
	@JoinColumn(name="payeeid")
	private Payee payeeid;

	@ManyToOne
	@JoinColumn(name="personid")
	private Person personid;

	private static final long serialVersionUID = 1L;

	public Personpayee() {
		super();
	}

	public int getPersonpayeeid() {
		return this.personpayeeid;
	}

	public void setPersonpayeeid(int personpayeeid) {
		this.personpayeeid = personpayeeid;
	}

	public String getPayeeaccountno() {
		return this.payeeaccountno;
	}

	public void setPayeeaccountno(String payeeaccountno) {
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
	
	public void createPP(String accnum, Person person, Payee payee){
		this.payeeaccountno = accnum;
		this.personid = person;
		this.payeeid = payee;
	}
	
	@Override
	public String toString(){		
		return personpayeeid + " " + payeeaccountno;
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
		if (!(obj instanceof Personpayee))
			return false;
		final Personpayee other = (Personpayee) obj;
		if (payeeaccountno == null) {
			if (other.payeeaccountno != null)
				return false;
		} else if (!payeeaccountno.equals(other.payeeaccountno))
			return false;
		return true;
	}
}
