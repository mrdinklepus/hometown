package entityBeans;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class LookupPK implements Serializable {
	private String lookupkey;

	private String lookupgroup;

	private static final long serialVersionUID = 1L;

	public LookupPK() {
		super();
	}

	public String getLookupkey() {
		return this.lookupkey;
	}

	public void setLookupkey(String lookupkey) {
		this.lookupkey = lookupkey;
	}

	public String getLookupgroup() {
		return this.lookupgroup;
	}

	public void setLookupgroup(String lookupgroup) {
		this.lookupgroup = lookupgroup;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ( ! (o instanceof LookupPK)) {
			return false;
		}
		LookupPK other = (LookupPK) o;
		return this.lookupkey.equals(other.lookupkey)
			&& this.lookupgroup.equals(other.lookupgroup);
	}

	@Override
	public int hashCode() {
		return this.lookupkey.hashCode()
			^ this.lookupgroup.hashCode();
	}

}
