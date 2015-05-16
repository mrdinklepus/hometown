package entityBeans;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Payments implements Serializable {
	@Id
	@GeneratedValue
	private int paymentid;

	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name="personid")
	private Person personid;
	
	@ManyToOne
	@JoinColumn(name="accountid")
	private Account accountid;

	@ManyToOne
	@JoinColumn(name="payeeid")
	private Payee payeeid;

	private String paydate;

	private static final long serialVersionUID = 1L;

	public Payments() {
		super();
	}

	public int getPaymentid() {
		return this.paymentid;
	}

	public void setPaymentid(int paymentid) {
		this.paymentid = paymentid;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Account getAccountid() {
		return this.accountid;
	}

	public void setAccountid(Account accountid) {
		this.accountid = accountid;
	}

	public Payee getPayeeid() {
		return this.payeeid;
	}

	public void setPayeeid(Payee payeeid) {
		this.payeeid = payeeid;
	}

	public String getPaydate() {
		return this.paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	
	public void schedulePay(Person person, Account a, Payee p, BigDecimal amount, String date){
		this.personid = person;
		this.accountid = a;
		this.payeeid = p;
		this.amount = amount;
		this.paydate = date;
	}

	/**
	 * @return the personid
	 */
	public Person getPersonid() {
		return personid;
	}

	/**
	 * @param personid the personid to set
	 */
	public void setPersonid(Person personid) {
		this.personid = personid;
	}

}
