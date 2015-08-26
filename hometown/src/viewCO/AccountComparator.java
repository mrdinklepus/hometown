package viewCO;

import java.util.Comparator;

import entityBeans.Account;
import entityBeans.AccountType;

public class AccountComparator implements Comparator<Account>
{
	public int compare(Account o, Account p)
	{
		AccountType c = o.getAccountType();
		AccountType d = p.getAccountType();
		return c.compareTo(d);
	}
}