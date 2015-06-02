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
						BigDecimal balance = fr.getBalance();	
						
						if (balance.compareTo(amount) > 0 || fr.getAccountType().equals("R"))
						{
							String toNum = req.getParameter("toAccount").toString();
							String fromNum = req.getParameter("fromAccount").toString();
							req.setAttribute("validate", "true");
							req.setAttribute("error", "suc");							
							req.setAttribute("toNum", toNum);
							req.setAttribute("fromNum", fromNum);
						}
						else
						{
							System.out.println("Insufficient Funds");
							req.setAttribute("validate", "false");
							req.setAttribute("error", "**Unable to make transfer.  Insufficient funds to complete transaction.**");
						}				
					}
					else
					{
						System.out.println("Amount entered was zero.");
						req.setAttribute("validate", "false");
						req.setAttribute("error", "**Unable to make transfer.  Please enter an amount greater than zero.**");
					}
				}
				else
				{
					System.out.println("Transfer to same account.");
					req.setAttribute("validate", "false");
					req.setAttribute("error", "**Unable to make transfer.  Cannot make a transfer to the same account.**");
				}				
				person = businessRulesRemote.getPerson(uid);							
	
			}catch(EJBException e){
				e.printStackTrace();
				//req.setAttribute("error", "jndierror");
				
			}catch(Exception ex){
				ex.printStackTrace();
				//req.setAttribute("error", "jndierror");
			}
		}
		else
		{
			System.out.println("Invalid Number");
			req.setAttribute("validate", "false");
			req.setAttribute("amt", "");
			req.setAttribute("error", "**Unable to make transfer.  Please enter a valid amount.**");
			
			try
			{
				jndiContext = new InitialContext();			
				BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
				person = businessRulesRemote.getPerson(uid);
			
			}catch (Exception ee){
				ee.printStackTrace();
				req.setAttribute("error", "jndierror");
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
