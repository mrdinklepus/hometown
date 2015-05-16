package viewCO;

import java.util.Comparator;
import entityBeans.Account;

public class AccountComparator implements Comparator
{
	public int compare(Object o, Object p)
	{
		Account a = (Account)o;
		Account b = (Account)p;
		String c = a.getAccounttype();
		String d = b.getAccounttype();
		return c.compareTo(d);
	}
}