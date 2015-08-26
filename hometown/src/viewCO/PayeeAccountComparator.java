package viewCO;

import java.util.Comparator;

import entityBeans.Payee;
import entityBeans.PayeeAccount;

public class PayeeAccountComparator implements Comparator<PayeeAccount>
{
	public int compare(PayeeAccount o, PayeeAccount p)
	{
	  Payee p1 = o.getPayeeAccountKey().getPayeeid();
		Payee p2 = p.getPayeeAccountKey().getPayeeid();
		return p1.getCompany().compareTo(p2.getCompany());
	}
}