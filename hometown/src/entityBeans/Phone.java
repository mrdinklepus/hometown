package entityBeans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Phone implements Serializable {
	@Id
	@GeneratedValue
	private int phoneid;

	private String phone;

	private String phonetype;

	@OneToMany(mappedBy="phoneid")
	private Set<Payee> payeeCollection;

	@ManyToMany
	@JoinTable(name="personphone",
		joinColumns=@JoinColumn(name="phoneid"),
		inverseJoinColumns=@JoinColumn(name="personid"))
	private Set<Person> personCollection;

	private static final long serialVersionUID = 1L;

	public Phone() {
		super();
	}

	public int getPhoneid() {
		return this.phoneid;
	}

	public void setPhoneid(int phoneid) {
		this.phoneid = phoneid;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhonetype() {
		return this.phonetype;
	}

	public void setPhonetype(String phonetype) {
		this.phonetype = phonetype;
	}

	public Set<Payee> getPayeeCollection() {
		return this.payeeCollection;
	}

	public void setPayeeCollection(Set<Payee> payeeCollection) {
		this.payeeCollection = payeeCollection;
	}

	public Set<Person> getPersonCollection() {
		return this.personCollection;
	}

	public void setPersonCollection(Set<Person> personCollection) {
		this.personCollection = personCollection;
	}
	
	public void createPhone(String phone){
		this.phonetype = "H";
		this.phone = phone;
	}
}
