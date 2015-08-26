package entityBeans;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import util.DateUtils;

@Entity
public class CheckOrder implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int orderid;

	private String dateordered;

	private BigDecimal amount;
	
	private String checkid;
	
	@ManyToOne
  @JoinColumn(name="personid")
  private Person personid;
	
	@ManyToOne
	@JoinColumn(name="accountid")
	private Account accountid;

	private static final long serialVersionUID = 1L;

	public CheckOrder()
	{
	  
	}
	
	public CheckOrder(BigDecimal amount, String checkId, Person p, Account a)
	{
    this.amount = amount;
    this.checkid = checkId;
    this.personid = p;
    this.accountid = a;
    // TODO turn this into time
    this.dateordered = DateUtils.Now("yyyy-MM-dd");
	}

	public int getOrderid() {
		return this.orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public Person getPersonid()
  {
    return personid;
  }

  public void setPersonid(Person personid)
  {
    this.personid = personid;
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
	
	public void placeOrder(BigDecimal amount, String checkId, Account a) {
		this.amount = amount;
		this.checkid = checkId;
		this.accountid = a;
		this.dateordered = DateUtils.Now("dd-MMM-yy");	
	}
}
