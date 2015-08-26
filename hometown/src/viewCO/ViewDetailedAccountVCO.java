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
		}
		catch (Exception ex)
		{
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