package entityBeans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Purchase implements Serializable {
	@Id
	private String purchaseid;

	@ManyToOne
	@JoinColumn(name="selltransactionid")
	private Banktransaction selltransactionid;

	@ManyToOne
	@JoinColumn(name="securityid")
	private Security securityid;

	@ManyToOne
	@JoinColumn(name="buytransactionid")
	private Banktransaction buytransactionid;

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

	public Banktransaction getSelltransactionid() {
		return this.selltransactionid;
	}

	public void setSelltransactionid(Banktransaction selltransactionid) {
		this.selltransactionid = selltransactionid;
	}

	public Security getSecurityid() {
		return this.securityid;
	}

	public void setSecurityid(Security securityid) {
		this.securityid = securityid;
	}

	public Banktransaction getBuytransactionid() {
		return this.buytransactionid;
	}

	public void setBuytransactionid(Banktransaction buytransactionid) {
		this.buytransactionid = buytransactionid;
	}

}
