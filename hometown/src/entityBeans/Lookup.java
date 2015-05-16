package entityBeans;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Lookup implements Serializable {
	@EmbeddedId
	private LookupPK pk;

	private String shortvalue;

	private String longvalue;

	private static final long serialVersionUID = 1L;

	public Lookup() {
		super();
	}

	public LookupPK getPk() {
		return this.pk;
	}

	public void setPk(LookupPK pk) {
		this.pk = pk;
	}

	public String getShortvalue() {
		return this.shortvalue;
	}

	public void setShortvalue(String shortvalue) {
		this.shortvalue = shortvalue;
	}

	public String getLongvalue() {
		return this.longvalue;
	}

	public void setLongvalue(String longvalue) {
		this.longvalue = longvalue;
	}
}
