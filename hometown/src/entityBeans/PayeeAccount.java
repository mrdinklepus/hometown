package entityBeans;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@NamedQuery(name="PayeeAccount.findByAccountId", query="SELECT p FROM PayeeAccount p" +
    " WHERE p.payeeAccountKey.payeeaccountno = :accountid")
@Entity
public class PayeeAccount implements Serializable
{
	@EmbeddedId
	private PayeeAccountKey payeeAccountKey;

  private static final long serialVersionUID = 1L;

  public PayeeAccount()
  {
    
  }
  
  public PayeeAccount(String accnum, Person person, Payee payee)
	{
	  payeeAccountKey = new PayeeAccountKey(accnum, person, payee);
	}
	
	/**
   * @return the payeeaccountkey
   */
  public PayeeAccountKey getPayeeAccountKey()
  {
    return payeeAccountKey;
  }

  /**
   * @param payeeaccountkey the payeeaccountkey to set
   */
  public void setPayeeAccountKey(PayeeAccountKey payeeAccountKey)
  {
    this.payeeAccountKey = payeeAccountKey;
  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((payeeAccountKey == null) ? 0 : payeeAccountKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PayeeAccount))
			return false;
		final PayeeAccount other = (PayeeAccount) obj;
		if (payeeAccountKey == null) {
			if (other.payeeAccountKey != null)
				return false;
		} else if (!payeeAccountKey.equals(other.payeeAccountKey))
			return false;
		return true;
	}
}
