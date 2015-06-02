package entityBeans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Payee implements Serializable {
	@Id
	@GeneratedValue
	private int payeeid;

	private String company;

	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Address addressid;
	
	@OneToMany(fetch=FetchType.EAGER, orphanRemoval=true, cascade=CascadeType.ALL)
	@JoinTable(name="payeephone",
    joinColumns=@JoinColumn(name="payeeid"),
    inverseJoinColumns=@JoinColumn(name="phoneid"))
  private Set<Phone> phoneid;

//	@OneToMany(mappedBy="payeeid", fetch=FetchType.EAGER)
//	private Set<PersonPayeeAccount> payeeAccounts;
	
//	@OneToMany(mappedBy="payeeid", fetch=FetchType.EAGER)
//	private Set<Payments> paymentCollection;
	
//	@ManyToMany(mappedBy="payeeCollection", fetch=FetchType.EAGER)
//  private Set<BankTransaction> banktransactionCollection;

	private static final long serialVersionUID = 1L;

	public Payee() {
	  
	}
	
	public Payee(String coname, Address add, Set<Phone> phoneNumbers) {
    this.company = coname;
    this.addressid = add;
    this.phoneid = phoneNumbers;
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

	public Set<Phone> getPhones() {
		return this.phoneid;
	}

	public void setPhones(Set<Phone> phone) {
		this.phoneid = phone;
	}

	public Address getAddress() {
		return this.addressid;
	}

	public void setAddress(Address address) {
		this.addressid = address;
	}

//	public Set<PersonPayeeAccount> getPersonpayeeCollection() {
//		return this.payeeAccounts;
//	}
//
//	public void setPersonpayeeCollection(Set<PersonPayeeAccount> personpayeeCollection) {
//		this.payeeAccounts = personpayeeCollection;
//	}
//
//	public Set<BankTransaction> getBanktransactionCollection() {
//		return this.banktransactionCollection;
//	}
//
//	public void setBanktransactionCollection(Set<BankTransaction> banktransactionCollection) {
//		this.banktransactionCollection = banktransactionCollection;
//	}
	
	public void addPayee(String coname, Address add, Set<Phone> phoneNumbers){
		this.company = coname;
		this.addressid = add;
		this.phoneid = phoneNumbers;
		
	}
	
	public void addPayee(String coname, Address addid) {
		this.company = coname;
		this.addressid = addid;
	}

	/**
	 * @return the paymentCollection
	 */
//	public Set<Payments> getPaymentCollection() {
//		return paymentCollection;
//	}
//
//	/**
//	 * @param paymentCollection the paymentCollection to set
//	 */
//	public void setPaymentCollection(Set<Payments> paymentCollection) {
//		this.paymentCollection = paymentCollection;
//	}

}
