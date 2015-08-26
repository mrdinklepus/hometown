package businessControl;

import java.math.BigDecimal;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import appcontroller.TinySession;
import entityBeans.Account;
import entityBeans.Person;

public class ConfirmTransferBCO implements BCOInterface
{
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{				
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		int to = Integer.parseInt(req.getParameter("to"));
		int from = Integer.parseInt(req.getParameter("from"));
		boolean test = isParsableToBig(req.getParameter("amt"));
		Person person = null;
		Context jndiContext;
		
		req.setAttribute("to", to);
		req.setAttribute("from", from);
		
		if (test)
		{
			BigDecimal amount = new BigDecimal(req.getParameter("amt"));
			req.setAttribute("amt", amount);
			
			try
			{	
				jndiContext = new InitialContext();			
				BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
															
				if (to != from)
				{
					BigDecimal z = new BigDecimal("0");
					
					if (amount.compareTo(z) > 0)
					{
						Account fr = businessRulesRemote.getAccount(from);
						Account toAcc = businessRulesRemote.getAccount(to);
						BigDecimal balance = fr.getBalance();	
						
						if (balance.compareTo(amount) > 0 || fr.getAccountType().equals("R"))
						{
							String toNum = req.getParameter("toAccount").toString();
							String fromNum = req.getParameter("fromAccount").toString();
							req.setAttribute("toType", toAcc.getAccountType().name());
							req.setAttribute("fromType", fr.getAccountType().name());
							req.setAttribute("toNum", toNum);
							req.setAttribute("fromNum", fromNum);
						}
						else
						{
							req.setAttribute("error", "**Unable to make transfer.  Insufficient funds to complete transaction.**");
						}				
					}
					else
					{
						req.setAttribute("error", "**Unable to make transfer.  Please enter an amount greater than zero.**");
					}
				}
				else
				{
					req.setAttribute("error", "**Unable to make transfer.  Cannot make a transfer to the same account.**");
				}				
				person = businessRulesRemote.getPerson(uid);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return "jndierror";
			}
		}
		else
		{
			req.setAttribute("error", "**Unable to make transfer.  Please enter a valid amount.**");
			
			try
			{
				jndiContext = new InitialContext();			
				BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
				person = businessRulesRemote.getPerson(uid);
			}
			catch (Exception ee)
			{
				ee.printStackTrace();
				return "jndierror";
			}
		}		
		return person;				
	}
	
	public boolean isParsableToBig(String i)
	{
		try
		{
			BigDecimal a = new BigDecimal(i);
			return true;
		
		}catch(NumberFormatException nfe){
			return false;
		}
	}
}
