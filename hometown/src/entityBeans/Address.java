package entityBeans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Address implements Serializable {
	@Id
	@GeneratedValue
	private int addressid;

	private String street;
	
	private String city;

	private String state;
	
	private String zipcode;

	private static final long serialVersionUID = 1L;

	public Address()
	{
		
	}
	
	public Address(String street, String city, String state, String zip)
	{
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zip;	
	}

	public int getAddressid()
	{
		return this.addressid;
	}

	public void setAddressid(int addressid)
	{
		this.addressid = addressid;
	}

	public String getZipcode()
	{
		return this.zipcode;
	}

	public void setZipcode(String zipcode)
	{
		this.zipcode = zipcode;
	}

  public String getCity()
  {
    return this.city;
  }
  
  public void setCity(String city)
  {
    this.city = city;
  }

  public String getStreet()
  {
    return this.street;
  }
  
  public void setStreet(String street)
  {
    this.street = street;
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public void setState(String state)
  {
    this.state = state;
  }
}
