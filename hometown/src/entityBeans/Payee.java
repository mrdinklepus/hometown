package entityBeans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Payee implements Serializable {
	@Id
	@GeneratedValue
	private int payeeid;

	private String company;

	@ManyToOne
	@JoinColumn(name="phoneid")
	private Phone phoneid;

	@ManyToOne
	@JoinColumn(name="addressid")
	private Address addressid;

	@OneToMany(mappedBy="payeeid")
	private Set<Personpayee> personpayeeCollection;

	@ManyToMany(mappedBy="payeeCollection")
	private Set<Banktransaction> banktransactionCollection;
	
	@OneToMany(mappedBy="payeeid")
	private Set<Payments> paymentCollection;

	private static final long serialVersionUID = 1L;

	public Payee() {
		super();
	}

	public int getPayeeid() {
		return this.payeeid;
	}

	public void setPayeeid(int payeeid) {
		this.payeeid = payeeid;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Phone getPhoneid() {
		return this.phoneid;
	}

	public void setPhoneid(Phone phoneid) {
		this.phoneid = phoneid;
	}

	public Address getAddressid() {
		return this.addressid;
	}

	public void setAddressid(Address addressid) {
		this.addressid = addressid;
	}

	public Set<Personpayee> getPersonpayeeCollection() {
		return this.personpayeeCollection;
	}

	public void setPersonpayeeCollection(Set<Personpayee> personpayeeCollection) {
		this.personpayeeCollection = personpayeeCollection;
	}

	public Set<Banktransaction> getBanktransactionCollection() {
		return this.banktransactionCollection;
	}

	public void setBanktransactionCollection(Set<Banktransaction> banktransactionCollection) {
		this.banktransactionCollection = banktransactionCollection;
	}
	
	public void addPayee(String coname, Address addid, Phone phoneid){
		this.company = coname;
		this.addressid = addid;
		this.phoneid = phoneid;
		
	}
	
	public void addPayeenophone(String coname, Address addid) {
		this.company = coname;
		this.addressid = addid;
	}

	/**
	 * @return the paymentCollection
	 */
	public Set<Payments> getPaymentCollection() {
		return paymentCollection;
	}

	/**
	 * @param paymentCollection the paymentCollection to set
	 */
	public void setPaymentCollection(Set<Payments> paymentCollection) {
		this.paymentCollection = paymentCollection;
	}

}
