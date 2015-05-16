package entityBeans;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Address implements Serializable {
	@Id
	@GeneratedValue
	private int addressid;

	private String zipcode;

	private String city;

	private String street1;

	private String state;

	private String street2;

	@OneToMany(mappedBy="addressid")
	private Set<Payee> payeeCollection;

	@OneToMany(mappedBy="addressid")
	private Set<Branch> branchCollection;

	@OneToMany(mappedBy="addressid")
	private Set<Person> personCollection;

	private static final long serialVersionUID = 1L;

	public Address() {
		super();
	}
	
	public Address(String street, String city, String state, String zip){
		this.street1 = street;
		this.city = city;
		this.state = state;
		this.zipcode = zip;	
	}

	public int getAddressid() {
		return this.addressid;
	}

	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet1() {
		return this.street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet2() {
		return this.street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public Set<Payee> getPayeeCollection() {
		return this.payeeCollection;
	}

	public void setPayeeCollection(Set<Payee> payeeCollection) {
		this.payeeCollection = payeeCollection;
	}

	public Set<Branch> getBranchCollection() {
		return this.branchCollection;
	}

	public void setBranchCollection(Set<Branch> branchCollection) {
		this.branchCollection = branchCollection;
	}

	public Set<Person> getPersonCollection() {
		return this.personCollection;
	}

	public void setPersonCollection(Set<Person> personCollection) {
		this.personCollection = personCollection;
	}
}
