package entityBeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Purchase implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String purchaseid;

	@ManyToOne
	@JoinColumn(name="selltransactionid")
	private BankTransaction selltransactionid;

	@ManyToOne
	@JoinColumn(name="securityid")
	private Security securityid;

	@ManyToOne
	@JoinColumn(name="buytransactionid")
	private BankTransaction buytransactionid;

	private static final long serialVersionUID = 1L;

	public Purchase() {
		super();
	}

	public String getPurchaseid() {
		return this.purchaseid;
	}

	public void setPurchaseid(String purchaseid) {
		this.purchaseid = purchaseid;
	}

	public BankTransaction getSelltransactionid() {
		return this.selltransactionid;
	}

	public void setSelltransactionid(BankTransaction selltransactionid) {
		this.selltransactionid = selltransactionid;
	}

	public Security getSecurityid() {
		return this.securityid;
	}

	public void setSecurityid(Security securityid) {
		this.securityid = securityid;
	}

	public BankTransaction getBuytransactionid() {
		return this.buytransactionid;
	}

	public void setBuytransactionid(BankTransaction buytransactionid) {
		this.buytransactionid = buytransactionid;
	}

}
