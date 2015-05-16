package entityBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import util.DateUtils;

@Entity
public class Banktransaction implements Serializable {
	@Id
	@GeneratedValue
	private int transactionid;

	private String description;

	private BigDecimal amount;
	
	private BigDecimal frombalance;
	
	private BigDecimal tobalance;

	private String transtype;

	private String timemade;
	
	private String userdescription;

	@ManyToOne
	@JoinColumn(name="toaccountid")
	private Account toaccountid;

	@ManyToOne
	@JoinColumn(name="fromaccountid")
	private Account fromaccountid;

	@OneToMany(mappedBy="selltransactionid")
	private Set<Purchase> purchaseCollection;

	@OneToMany(mappedBy="buytransactionid")
	private Set<Purchase> purchaseCollection2;

	@ManyToMany
	@JoinTable(name="billpayment",
		joinColumns=@JoinColumn(name="transactionid"),
		inverseJoinColumns=@JoinColumn(name="payeeid"))
	private Set<Payee> payeeCollection;

	private static final long serialVersionUID = 1L;

	public Banktransaction() {
		super();
	}

	public Banktransaction(Account toAccount, Account fromAccount, BigDecimal amount, String transType){
		this.fromaccountid = fromAccount;
		this.toaccountid = toAccount;
		this.amount = amount;
		this.frombalance = fromAccount.getBalance();
		this.tobalance = toAccount.getBalance();
		this.transtype = transType;
		this.timemade = DateUtils.Now("dd-MMM-yy");	
	}
	
	public Banktransaction(Account toAccount, Account fromAccount, 
			BigDecimal amount, String transType, String description, String userdescription){
		
		this.fromaccountid = fromAccount;
		this.toaccountid = toAccount;
		this.amount = amount;
		this.frombalance = fromAccount.getBalance();
		this.tobalance = toAccount.getBalance();
		this.transtype = transType;
		this.description = description;
		this.userdescription = userdescription;
		this.timemade = DateUtils.Now("dd-MMM-yy");			
	}
	
	public Banktransaction(Account fromAccount, BigDecimal amount, String transType, String description, String userDescription) {

		this.fromaccountid = fromAccount;
		this.amount = amount;
		this.frombalance = fromAccount.getBalance();
		this.transtype = transType;
		this.description = description;
		this.userdescription = userDescription;
		this.timemade = DateUtils.Now("dd-MMM-yy");		
	}
	
	public int getTransactionid() {
		return this.transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTranstype() {
		return this.transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public String getTimemade() {
		return this.timemade;
	}

	public void setTimemade(String timemade) {
		this.timemade = timemade;
	}

	public Account getToaccountid() {
		return this.toaccountid;
	}

	public void setToaccountid(Account toaccountid) {
		this.toaccountid = toaccountid;
	}

	public Account getFromaccountid() {
		return this.fromaccountid;
	}

	public void setFromaccountid(Account fromaccountid) {
		this.fromaccountid = fromaccountid;
	}

	public Set<Purchase> getPurchaseCollection() {
		return this.purchaseCollection;
	}

	public void setPurchaseCollection(Set<Purchase> purchaseCollection) {
		this.purchaseCollection = purchaseCollection;
	}

	public Set<Purchase> getPurchaseCollection2() {
		return this.purchaseCollection2;
	}

	public void setPurchaseCollection2(Set<Purchase> purchaseCollection2) {
		this.purchaseCollection2 = purchaseCollection2;
	}

	public Set<Payee> getPayeeCollection() {
		return this.payeeCollection;
	}

	public void setPayeeCollection(Set<Payee> payeeCollection) {
		this.payeeCollection = payeeCollection;
	}

	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return frombalance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.frombalance = balance;
	}

	/**
	 * @return the tobalance
	 */
	public BigDecimal getTobalance() {
		return tobalance;
	}

	/**
	 * @param tobalance the tobalance to set
	 */
	public void setTobalance(BigDecimal tobalance) {
		this.tobalance = tobalance;
	}
	
	/**
	 * @return the frombalance
	 */
	public BigDecimal getFrombalance() {
		return frombalance;
	}

	/**
	 * @param frombalance the frombalance to set
	 */
	public void setFrombalance(BigDecimal frombalance) {
		this.frombalance = frombalance;
	}

	/**
	 * @return the userdescription
	 */
	public String getUserdescription() {
		return userdescription;
	}
	
	/**
   * @param userdescription the userdescription to set
   */
  public void setUserdescription(String userdescription) {
    this.userdescription = userdescription;
  }
}
