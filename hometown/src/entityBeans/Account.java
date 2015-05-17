package entityBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.ejb.EJBException;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Account implements Serializable {
	@Id
	@GeneratedValue
	private int accountid;

	@Enumerated(EnumType.STRING)
  private AccountType accounttype;

	private BigDecimal balance;

	private String accountno;

	@ManyToOne
	@JoinColumn(name="personid")
	private Person personid;

	@OneToMany(mappedBy="toaccountid")
	private Set<Banktransaction> banktransactionCollection;

	@OneToMany(mappedBy="fromaccountid")
	private Set<Banktransaction> banktransactionCollection2;
	
	@OneToMany(mappedBy="accountid")
	private Set<Checkorder> orderCollection;
	
	@OneToMany(mappedBy="accountid")
	private Set<Payments> paymentCollection;

	private static final long serialVersionUID = 1L;

	public Account() {
		super();
	}
	
	public Account(AccountType accountType, BigDecimal balance, String accountno, Person personid){
		this.accounttype = accountType;
		this.balance = balance;
		this.accountno = accountno;
		this.personid = personid;
	}

	public int getAccountid() {
		return this.accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	public AccountType getAccounttype() {
		return this.accounttype;
	}

	public void setAccounttype(AccountType accounttype) {
		this.accounttype = accounttype;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		System.out.println("Setting balance: " + balance);
		this.balance = balance;
	}

	public String getAccountno() {
		return this.accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public Person getPersonid() {
		return this.personid;
	}

	public void setPersonid(Person personid) {
		this.personid = personid;
	}

	public Set<Banktransaction> getBanktransactionCollection() {
		return this.banktransactionCollection;
	}

	public void setBanktransactionCollection(Set<Banktransaction> banktransactionCollection) {
		this.banktransactionCollection = banktransactionCollection;
	}

	public Set<Banktransaction> getBanktransactionCollection2() {
		return this.banktransactionCollection2;
	}

	public void setBanktransactionCollection2(Set<Banktransaction> banktransactionCollection2) {
		this.banktransactionCollection2 = banktransactionCollection2;
	}
	
	public String toString(){
		return accountid + " " + accounttype + " " + balance + " " + accountno;
	}
	
	public void debit(BigDecimal amount){
		BigDecimal z = new BigDecimal("0");
				
		if (amount.compareTo(z) <= 0){
			throw new EJBException("Amount must be greater than zero");
		}
		this.balance = balance.add(amount);					
	}
	
	public void credit(BigDecimal amount){
		BigDecimal z = new BigDecimal("0");
				
		if (amount.compareTo(z) <= 0){
			throw new EJBException("Amount must be greater than zero");
		}
		System.out.println(amount);
//		if (balance.compareTo(amount) <= 0){
//			throw new EJBException("Insufficient funds to complete transaction");
//		}
		
		this.balance = balance.subtract(amount);	
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
	
	public String getType(){
		String chk = "CHKNG: ";
		String sav = "SVNGS: ";
		String cc = "CRDIT: ";
		if (accounttype.equals("C"))
			return chk;
		else if (accounttype.equals("R"))
			return cc;
		else if (accounttype.equals("S"))
			return sav;
		else
			return "CD: ";
	}
}
