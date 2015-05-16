package entityBeans;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Branch implements Serializable {
	@Id
	private String branchid;

	private String branchname;

	@ManyToOne
	@JoinColumn(name="addressid")
	private Address addressid;

	@OneToMany(mappedBy="branchid")
	private Set<Person> personCollection;

	private static final long serialVersionUID = 1L;

	public Branch() {
		super();
	}

	public String getBranchid() {
		return this.branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getBranchname() {
		return this.branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public Address getAddressid() {
		return this.addressid;
	}

	public void setAddressid(Address addressid) {
		this.addressid = addressid;
	}

	public Set<Person> getPersonCollection() {
		return this.personCollection;
	}

	public void setPersonCollection(Set<Person> personCollection) {
		this.personCollection = personCollection;
	}

}
