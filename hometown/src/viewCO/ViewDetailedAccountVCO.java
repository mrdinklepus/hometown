package viewCO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import appcontroller.TinySession;
import entityBeans.Account;
import entityBeans.BankTransaction;
import entityBeans.Person;

public class ViewDetailedAccountVCO implements VCOInterface {

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{	
		TreeSet<BankTransaction> transactions = new TreeSet<BankTransaction>();
		int counter = -1;
		int elem = -1;
		
		try 
		{
			Account account = (Account)req.getAttribute("reqObject");
			if (account == null)
			{
			  System.out.println("No Valid Account to process!!!");
			  return;
			}
			
			Set<BankTransaction> trans1 = account.getTransactionsIn();
			Set<BankTransaction> trans2 = account.getTransactionsOut();
			
			transactions.addAll(trans1);
			transactions.addAll(trans2);
			
//			// TODO Is this just ordering the transactions???
//			for (Iterator it = trans1.iterator(); it.hasNext();) 
//			{	
//				BankTransaction bt = (BankTransaction)it.next();
//				BankTransaction bt2 = null;
//				
//				if (counter == -1)
//				{
//					if (bt != null)
//					{
//						transactions.add(bt);
//					}
//					elem++;
//					counter++;
//				}
//				else 
//				{
//					int i = 0;
//					while (i == 0)
//					{	
//						bt2 = (BankTransaction)transactions.get(counter);
//						if ((bt.getTransactionid() < bt2.getTransactionid()) && (counter == 0))
//						{
//							transactions.add(counter, bt);
//							elem++;
//							i++;
//						}						
//						else if (bt.getTransactionid() < bt2.getTransactionid()) 
//						{
//							int c = counter - 1;
//							BankTransaction bt3 = (BankTransaction)transactions.get(c);
//							
//							if (bt.getTransactionid() < bt3.getTransactionid())
//							{	
//								counter--;
//							}
//							else 
//							{
//								transactions.add(counter, bt);
//								elem++;
//								i++;
//							}
//						}						
//						else if ((bt.getTransactionid() > bt2.getTransactionid()) && (counter == elem))
//						{	
//							transactions.add(bt);
//							elem++;
//							i++;
//						}
//						else if (bt.getTransactionid() >= bt2.getTransactionid())
//						{
//							int c = counter + 1;
//							BankTransaction bt3 = (BankTransaction)transactions.get(c);
//							
//							if (bt.getTransactionid() >= bt3.getTransactionid())
//							{
//								counter++;
//							}
//							else 
//							{
//								transactions.add(c, bt);
//								elem++;
//								i++;
//							}
//						}	
//					}
//				}
//			}
//			
//			System.out.println("List contains " + transactions.size() + " elements");
		} catch (Exception ex) {
			ex.printStackTrace();			
		}
    
		req.setAttribute("list", transactions);
				
		try
		{
			req.getRequestDispatcher("WEB-INF/accountdetails.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
}