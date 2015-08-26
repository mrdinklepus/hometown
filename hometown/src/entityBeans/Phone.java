package entityBeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Phone implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int phoneid;

	private String phonenumber;

	@Enumerated(EnumType.STRING)
	private PhoneType phonetype;

	private static final long serialVersionUID = 1L;

	public Phone()
	{
	  
	}
	
	public Phone(String phone, PhoneType type)
	{
    this.phonetype = type;
    this.phonenumber = phone;
	}

	public int getPhoneid() {
		return this.phoneid;
	}

	public void setPhoneid(int phoneid) {
		this.phoneid = phoneid;
	}

	public String getPhoneNumber() {
		return this.phonenumber;
	}

	public void setPhoneNumber(String phone) {
		this.phonenumber = phone;
	}

	public PhoneType getPhonetype() {
		return this.phonetype;
	}

	public void setPhonetype(PhoneType phonetype) {
		this.phonetype = phonetype;
	}
}
