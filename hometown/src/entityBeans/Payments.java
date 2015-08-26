package entityBeans;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Payments implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int paymentid;

	private BigDecimal amount;
	
	private Date paydate;

	@ManyToOne
	@JoinColumn(name="personid")
	private Person personid;
	
	@ManyToOne
	@JoinColumn(name="accountid")
	private Account accountid;

	@ManyToOne
	@JoinColumn(name="payeeid")
	private Payee payeeid;

	private static final long serialVersionUID = 1L;
	
	public Payments()
	{
	  
	}
	
	public Payments(Person person, Account a, Payee p, BigDecimal amount, Date date) 
	{
    this.personid = person;
    this.accountid = a;
    this.payeeid = p;
    this.amount = amount;
    this.paydate = date;
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

	public Date getPaydate() {
		return this.paydate;
	}

	public void setPaydate(Date paydate) {
		this.paydate = paydate;
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
