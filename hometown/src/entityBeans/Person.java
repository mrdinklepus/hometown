package entityBeans;

import java.io.Serializable;
import java.util.Set;

import javax.ejb.EJBException;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQuery(name="findByUserPass", query="SELECT DISTINCT p FROM Person p" +
                  " WHERE p.userid = :userid AND p.password = :password")
@Entity
public class Person implements Serializable {
	@Id	
	@GeneratedValue
	private int personid;

	private String firstname;

	private String userid;

	private String middlename;

	private String password;

	private String lastname;

	@ManyToOne
	@JoinColumn(name="branchid")
	private Branch branchid;

	@ManyToOne
	@JoinColumn(name="addressid")
	private Address addressid;

	@OneToMany(mappedBy="personid", fetch=FetchType.EAGER)
	private Set<Account> accountCollection;

	@OneToMany(mappedBy="personid")
	private Set<Personpayee> personpayeeCollection;
	
	@OneToMany(mappedBy="personid")
  private Set<Checkorder> orderCollection;
  
  @OneToMany(mappedBy="personid")
  private Set<Payments> paymentCollection;

	@ManyToMany(mappedBy="personCollection")
	private Set<Phone> phoneCollection;
	
	private static final long serialVersionUID = 1L;

	public Person() {
		super();
	}

	public int getPersonid() {
		return this.personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		if (userid == null || userid.trim().length() > 15 || userid.trim().length() == 0){
			throw new EJBException("Person UserId is Invalid");
		}
		this.userid = userid;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		if (password == null || password.trim().length() > 10 || password.trim().length() == 0 ){
			throw new EJBException("Person password is Invalid");
		}
		this.password = password.trim();
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Branch getBranchid() {
		return this.branchid;
	}

	public void setBranchid(Branch branchid) {
		this.branchid = branchid;
	}

	public Address getAddressid() {
		return this.addressid;
	}

	public void setAddressid(Address addressid) {
		this.addressid = addressid;
	}

	public Set<Account> getAccountCollection() {
		return this.accountCollection;
	}

	public void setAccountCollection(Set<Account> accountCollection) {
		this.accountCollection = accountCollection;
	}

	public Set<Personpayee> getPersonpayeeCollection() {
		return this.personpayeeCollection;
	}

	public void setPersonpayeeCollection(Set<Personpayee> personpayeeCollection) {
		this.personpayeeCollection = personpayeeCollection;
	}

	public Set<Phone> getPhoneCollection() {
		return this.phoneCollection;
	}

	public void setPhoneCollection(Set<Phone> phoneCollection) {
		this.phoneCollection = phoneCollection;
	}

	public String getFullname(){
		return this.firstname + " " + this.middlename + " " + this.lastname;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((accountCollection == null) ? 0 : accountCollection
						.hashCode());
		result = prime * result
				+ ((addressid == null) ? 0 : addressid.hashCode());
		result = prime * result
				+ ((branchid == null) ? 0 : branchid.hashCode());
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result
				+ ((middlename == null) ? 0 : middlename.hashCode());
		result = prime * result
				+ ((orderCollection == null) ? 0 : orderCollection.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + personid;
		result = prime
				* result
				+ ((personpayeeCollection == null) ? 0 : personpayeeCollection
						.hashCode());
		result = prime * result
				+ ((phoneCollection == null) ? 0 : phoneCollection.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Person other = (Person) obj;
		if (accountCollection == null) {
			if (other.accountCollection != null)
				return false;
		} else if (!accountCollection.equals(other.accountCollection))
			return false;
		if (addressid == null) {
			if (other.addressid != null)
				return false;
		} else if (!addressid.equals(other.addressid))
			return false;
		if (branchid == null) {
			if (other.branchid != null)
				return false;
		} else if (!branchid.equals(other.branchid))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (middlename == null) {
			if (other.middlename != null)
				return false;
		} else if (!middlename.equals(other.middlename))
			return false;
		if (orderCollection == null) {
			if (other.orderCollection != null)
				return false;
		} else if (!orderCollection.equals(other.orderCollection))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (personid != other.personid)
			return false;
		if (personpayeeCollection == null) {
			if (other.personpayeeCollection != null)
				return false;
		} else if (!personpayeeCollection.equals(other.personpayeeCollection))
			return false;
		if (phoneCollection == null) {
			if (other.phoneCollection != null)
				return false;
		} else if (!phoneCollection.equals(other.phoneCollection))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

	/**
	 * @return the orderCollection
	 */
	public Set<Checkorder> getOrderCollection() {
		return orderCollection;
	}

	/**
	 * @param orderCollection the orderCollection to set
	 */
	public void setOrderCollection(Set<Checkorder> orderCollection) {
		this.orderCollection = orderCollection;
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
