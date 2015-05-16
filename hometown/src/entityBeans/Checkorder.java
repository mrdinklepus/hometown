package entityBeans;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import util.DateUtils;

@Entity
public class Checkorder implements Serializable {
	@Id
	@GeneratedValue
	private int orderid;

	private String dateordered;

	private BigDecimal amount;
	
	private String checkid;
	
	@ManyToOne
	@JoinColumn(name="accountid")
	private Account accountid;

	@ManyToOne
	@JoinColumn(name="personid")
	private Person personid;

	private static final long serialVersionUID = 1L;

	public Checkorder() {
		super();
	}

	public int getOrderid() {
		return this.orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public Account getAccountid() {
		return this.accountid;
	}

	public void setAccountid(Account accountid) {
		this.accountid = accountid;
	}

	public String getCheckid() {
		return this.checkid;
	}

	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}

	public String getDateordered() {
		return this.dateordered;
	}

	public void setDateordered(String dateordered) {
		this.dateordered = dateordered;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Person getPersonid() {
		return this.personid;
	}

	public void setPersonid(Person personid) {
		this.personid = personid;
	}
	
	public void placeOrder(BigDecimal amount, String checkId, Account a, Person p) {
		this.amount = amount;
		this.checkid = checkId;
		this.accountid = a;
		this.personid = p;
		this.dateordered = DateUtils.Now("dd-MMM-yy");	
	}
}
