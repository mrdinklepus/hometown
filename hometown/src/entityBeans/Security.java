package entityBeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Security implements Serializable {
	@Id
	@GeneratedValue
	private String securityid;

	private BigDecimal rate;

	private String description;

	private BigDecimal price;

	private BigDecimal daystomaturity;

//	@OneToMany(mappedBy="securityid")
//	private Set<Purchase> purchaseCollection;

	private static final long serialVersionUID = 1L;

	public Security() {
		super();
	}

	public String getSecurityid() {
		return this.securityid;
	}

	public void setSecurityid(String securityid) {
		this.securityid = securityid;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDaystomaturity() {
		return this.daystomaturity;
	}

	public void setDaystomaturity(BigDecimal daystomaturity) {
		this.daystomaturity = daystomaturity;
	}

//	public Set<Purchase> getPurchaseCollection() {
//		return this.purchaseCollection;
//	}
//
//	public void setPurchaseCollection(Set<Purchase> purchaseCollection) {
//		this.purchaseCollection = purchaseCollection;
//	}

}
