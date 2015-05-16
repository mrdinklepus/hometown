package viewCO;

import java.util.Comparator;
import entityBeans.Payments;

public class DateComparator implements Comparator
{
	public int compare(Object o, Object p)
	{
		Payments p1 = (Payments)o;
		Payments p2 = (Payments)p;
		String date1 = p1.getPaydate().split("\\s")[0];
		String date2 = p2.getPaydate().split("\\s")[0];
		
		if (date1.compareTo(date2) == 0)
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