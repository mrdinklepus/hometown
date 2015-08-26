package viewCO;

import java.util.Comparator;
import java.util.Date;

import entityBeans.Payments;

public class PaymentComparator implements Comparator<Payments>
{
	public int compare(Payments p1, Payments p2)
	{
		Date date1 = p1.getPaydate();
		Date date2 = p2.getPaydate();
		
		if (date1.equals(date2))
		{
			String test1 = p1.getPayeeid().getCompany();
			String test2 = p2.getPayeeid().getCompany();
			return test1.compareTo(test2);
		}
		else
		{
			return date1.compareTo(date2);
		}		
	}
}