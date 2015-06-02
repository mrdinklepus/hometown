package viewCO;

import java.util.Comparator;

import entityBeans.Account;
import entityBeans.AccountType;

public class AccountComparator implements Comparator
{
	public int compare(Object o, Object p)
	{
		Account a = (Account)o;
		Account b = (Account)p;
		AccountType c = a.getAccountType();
		AccountType d = b.getAccountType();
		return c.compareTo(d);
	}
}