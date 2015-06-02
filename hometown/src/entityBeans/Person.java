package entityBeans;

import java.io.Serializable;
import java.util.Set;

import javax.ejb.EJBException;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@NamedQuery(name="Person.findByUserPass", query="SELECT p FROM Person p" +
             " WHERE p.username = :username AND p.password = :password")
@Entity
public class Person implements Serializable {
	@Id	
	@GeneratedValue
	private int personid;

	private String firstname;

	private String username;

	private String middlename;

	private String password;

	private String lastname;

	@ManyToOne
	@JoinColumn(name="branchid")
	private Branch branchid;

	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Address addressid;
	
	@OneToMany(fetch=FetchType.EAGER, orphanRemoval=true, cascade=CascadeType.ALL)
	@JoinTable(name="personphone",
    joinColumns=@JoinColumn(name="personid"),
    inverseJoinColumns=@JoinColumn(name="phoneid"))
  private Set<Phone> phones;
	
	@OneToMany(mappedBy="payeeAccountKey.personid", fetch=FetchType.EAGER, orphanRemoval=true, cascade=CascadeType.ALL)
	private Set<PayeeAccount> payeeAccounts;
  
  @OneToMany(mappedBy="personid", fetch=FetchType.EAGER, orphanRemoval=true, cascade=CascadeType.ALL)
  private Set<Payments> scheduledPayments;
  
  @ManyToMany(fetch=FetchType.EAGER)
  @JoinTable(name="personaccount",
    joinColumns=@JoinColumn(name="accountid"),
    inverseJoinColumns=@JoinColumn(name="personid"))
  private Set<Account> accounts;
  
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

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String userid) {
		if (userid == null || userid.trim().length() > 15 || userid.trim().isEmpty()){
			throw new EJBException("Person UserId is Invalid");
		}
		this.username = userid;
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
		if (password == null || password.trim().length() > 10 || password.trim().isEmpty()) {
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

	public Branch getBranch() {
		return this.branchid;
	}

	public void setBranch(Branch branch) {
		this.branchid = branch;
	}

	public Address getAddress() {
		return this.addressid;
	}

	public void setAddress(Address address) {
		this.addressid = address;
	}

	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<PayeeAccount> getPayeeAccounts() {
		return this.payeeAccounts;
	}

	public void setPayeeAccounts(Set<PayeeAccount> payeeAccounts) {
		this.payeeAccounts = payeeAccounts;
	}

	public Set<Phone> getPhones() {
		return this.phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public String getFullname() {
		return this.firstname + " " + this.middlename + " " + this.lastname;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + personid;
		
//		result = prime
//				* result
//				+ ((accountCollection == null) ? 0 : accountCollection
//						.hashCode());
//		result = prime * result
//				+ ((addressid == null) ? 0 : addressid.hashCode());
//		result = prime * result
//				+ ((branchid == null) ? 0 : branchid.hashCode());
//		result = prime * result
//				+ ((firstname == null) ? 0 : firstname.hashCode());
//		result = prime * result
//				+ ((lastname == null) ? 0 : lastname.hashCode());
//		result = prime * result
//				+ ((middlename == null) ? 0 : middlename.hashCode());
//		result = prime * result
//				+ ((password == null) ? 0 : password.hashCode());
//		
//		result = prime
//				* result
//				+ ((personpayeeCollection == null) ? 0 : personpayeeCollection
//						.hashCode());
//		result = prime * result
//				+ ((phoneCollection == null) ? 0 : phoneCollection.hashCode());
//		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		
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
		if (personid != other.personid)
      return false;
		
//		if (accountCollection == null) {
//			if (other.accountCollection != null)
//				return false;
//		} else if (!accountCollection.equals(other.accountCollection))
//			return false;
//		if (addressid == null) {
//			if (other.addressid != null)
//				return false;
//		} else if (!addressid.equals(other.addressid))
//			return false;
//		if (branchid == null) {
//			if (other.branchid != null)
//				return false;
//		} else if (!branchid.equals(other.branchid))
//			return false;
//		if (firstname == null) {
//			if (other.firstname != null)
//				return false;
//		} else if (!firstname.equals(other.firstname))
//			return false;
//		if (lastname == null) {
//			if (other.lastname != null)
//				return false;
//		} else if (!lastname.equals(other.lastname))
//			return false;
//		if (middlename == null) {
//			if (other.middlename != null)
//				return false;
//		} else if (!middlename.equals(other.middlename))
//			return false;
//		if (password == null) {
//			if (other.password != null)
//				return false;
//		} else if (!password.equals(other.password))
//			return false;
//		if (personid != other.personid)
//			return false;
//		if (personpayeeCollection == null) {
//			if (other.personpayeeCollection != null)
//				return false;
//		} else if (!personpayeeCollection.equals(other.personpayeeCollection))
//			return false;
//		if (phoneCollection == null) {
//			if (other.phoneCollection != null)
//				return false;
//		} else if (!phoneCollection.equals(other.phoneCollection))
//			return false;
//		if (userid == null) {
//			if (other.userid != null)
//				return false;
//		} else if (!userid.equals(other.userid))
//			return false;
		
		return true;
	}

	/**
	 * @return the paymentCollection
	 */
	public Set<Payments> getScheduledPayments() {
		return scheduledPayments;
	}

	/**
	 * @param paymentCollection the paymentCollection to set
	 */
	public void setScheduledPayments(Set<Payments> scheduledPayments) {
		this.scheduledPayments = scheduledPayments;
	}	
}
