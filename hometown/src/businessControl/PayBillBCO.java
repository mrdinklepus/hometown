package businessControl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import entityBeans.Account;
import entityBeans.Payee;
import entityBeans.Person;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;

public class PayBillBCO implements BCOInterface
{
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		String[] bills = req.getParameter("bills").split("``");
		
		List<String> list1 = new ArrayList<>();
		
		Person person = null;
		boolean isValid = true;
		boolean onegood = false;
		
		try 
		{			
		  Context jndiContext = new InitialContext();
		  BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
		  for (int i = 0; i < bills.length; i++)
			{
				String[] args = bills[i].split("~~");
				int payeeId = Integer.parseInt(args[0]);
				
				if (!args[1].isEmpty())
				{
					onegood = true;
					isValid = isParsableToBig(args[1]);
					if (!isValid)
					{
						System.out.println("Invalid Number");
						req.setAttribute("error", "** One or more payments was incomplete.  Please enter a valid amount. **");
					}
					else
					{															
					  BigDecimal amount = new BigDecimal(args[1]);
						int accountId = Integer.parseInt(args[2]);
						BigDecimal z = new BigDecimal("0");
						if (amount.compareTo(z) > 0)
						{
							Account fromAccount = businessRulesRemote.getAccount(accountId);
							BigDecimal balance = fromAccount.getBalance();	
							if (balance.compareTo(amount) > 0 || fromAccount.getAccountType().equals("R"))
							{
								String desc = args[3];
								businessRulesRemote.payBill(accountId, payeeId, amount, desc);
								req.setAttribute("success", "Your payments have been made.  You may make other payments.");
							}
							else
							{
								System.out.println("Not Sufficient Funds");
								req.setAttribute("error", "** One or more payments was incomplete.  Insufficient funds to complete all transactions. **");
								isValid = false;														
							}				
						}
						else
						{
							System.out.println("Amount entered is zero");
							req.setAttribute("error", "** One or more payments was incomplete.  Please enter a valid amount. **");
							isValid = false;
						}									
					}
				}
				
				if (!isValid)
				{
					list1.add(businessRulesRemote.getpayeebyid(payeeId).getCompany());
					isValid = true;
				}											
			}
		  
			if (!onegood)
			{
				req.setAttribute("error", "No value entered.  Please enter a valid amount.");
			}
			
			if (list1.isEmpty())
			{
			  req.setAttribute("success", "Your payments have been made.  You may make other payments.");
			}
			else
			{
			  req.setAttribute("billpayErrorList", list1);
			}
			
			person = businessRulesRemote.getPerson(uid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "jndierror";
		}
		return person;		
	}
	
	public boolean isParsableToBig(String i)
	{
		try
		{			
			new BigDecimal(i);
			return true;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
	}
}
