package entityBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.ejb.EJBException;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@OneToMany(mappedBy="toaccountid", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<BankTransaction> transactionsIn;

	@OneToMany(mappedBy="fromaccountid", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<BankTransaction> transactionsOut;
	
	@OneToMany(mappedBy="accountid", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<CheckOrder> checkOrders;
	
	@OneToMany(mappedBy="accountid", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<Payments> payments;
	
	@ManyToMany(mappedBy="accounts")
  private Set<Person> accountOwners;

	private static final long serialVersionUID = 1L;

	public Account() {
		super();
	}
	
	public Account(AccountType accountType, BigDecimal balance, String accountno, Set<Person> personid){
		this.accounttype = accountType;
		this.balance = balance;
		this.accountno = accountno;
		this.accountOwners = personid;
	}

	public int getAccountid() {
		return this.accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	public AccountType getAccountType() {
		return this.accounttype;
	}

	public void setAccountType(AccountType accounttype) {
		this.accounttype = accounttype;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		System.out.println("Setting balance: " + balance);
		this.balance = balance;
	}

	public String getAccountNo() {
		return this.accountno;
	}

	public void setAccountNo(String accountno) {
		this.accountno = accountno;
	}

	public Set<Person> getAccountOwners() {
		return accountOwners;
	}

	public void setAccountOwners(Set<Person> accountOwners) {
		this.accountOwners = accountOwners;
	}

	public Set<BankTransaction> getTransactionsIn() {
		return this.transactionsIn;
	}

	public void setTransactionsIn(Set<BankTransaction> transactionsIn) {
		this.transactionsIn = transactionsIn;
	}

	public Set<BankTransaction> getTransactionsOut() {
		return this.transactionsOut;
	}

	public void setTransactionsOut(Set<BankTransaction> transactionsOut) {
		this.transactionsOut = transactionsOut;
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
	 * @return the checkOrders
	 */
	public Set<CheckOrder> getCheckOrders() {
		return checkOrders;
	}

	/**
	 * @param checkOrders the checkOrders to set
	 */
	public void setCheckOrders(Set<CheckOrder> checkOrders) {
		this.checkOrders = checkOrders;
	}

	/**
	 * @return the paymentCollection
	 */
	public Set<Payments> getPayments() {
		return payments;
	}

	/**
	 * @param paymentCollection the paymentCollection to set
	 */
	public void setPayments(Set<Payments> paymentCollection) {
		this.payments = paymentCollection;
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
