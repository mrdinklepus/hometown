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
		String out = req.getParameter("bills");
		System.out.println(out);
		String[] bills = req.getParameter("bills").split("``");
		
		List<String> list1 = new ArrayList();
		req.setAttribute("success", "");
		req.setAttribute("error", "");
		req.setAttribute("payid", "");
		req.setAttribute("errlist1", "");
		
		Person person = null;		
		BigDecimal amount;
		int payeeId;
		int accountId;
		boolean test2 = true;
		boolean onegood = false;
				
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		
		try 
		{			
			jndiContext = new InitialContext();
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			for (int i = 0; i < bills.length; i++)
			{
				String[] args = bills[i].split("~~");
				payeeId = Integer.parseInt(args[0]);
				
				if (args[1].compareTo("") != 0)
				{
					onegood = true;
					String isvalid = validate(args[1]);
					if (isvalid.compareTo("isValid") != 0)
					{
						System.out.println("Invalid Number");
						req.setAttribute("error", isvalid);
						test2 = false;
					}
					else
					{															
						amount = new BigDecimal(args[1]);
						accountId = Integer.parseInt(args[2]);
						BigDecimal z = new BigDecimal("0");
						if (amount.compareTo(z) > 0)
						{
							Account fr = businessRulesRemote.getAccount(accountId);
							BigDecimal balance = fr.getBalance();	
							if (balance.compareTo(amount) > 0 || fr.getAccountType().equals("R"))
							{
								String desc = args[3];
								businessRulesRemote.payBill(accountId, payeeId, amount, desc);
								person = businessRulesRemote.getPerson(uid);
								req.setAttribute("success", "suc");
							}
							else
							{
								System.out.println("Not Sufficient Funds");
								req.setAttribute("error", "**One or more payments were incomplete.  Insufficient funds to complete transactions.**");
								test2 = false;														
							}				
						}
						else
						{
							System.out.println("Amount entered is zero");
							req.setAttribute("error", "**One or more payments were incomplete.  Please enter a valid amount.**");
							test2 = false;
						}									
					}
				}
				if(!test2)
				{
					list1.add(businessRulesRemote.getpayeebyid(payeeId).getCompany());
					person = businessRulesRemote.getPerson(uid);
					test2 = true;
				}											
			}
			if (!onegood)
			{
				System.out.println("No values entered");
				req.setAttribute("error", "No value entered.  Please enter a valid amount.");
				person = businessRulesRemote.getPerson(uid);
			}			
			req.setAttribute("errlist1", list1);
			
		} catch (NumberFormatException e){
			e.printStackTrace();
		} catch (EJBException e){
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return person;		
	}
	
	public String validate(String am)
	{
		String isvalid = "";
		
		boolean test = isParsableToBig(am);					
		if (test)
		{
			isvalid = "isValid";		
		}
		else
		{
			System.out.println("Not parsable to BigDecimal");
			isvalid = "One or more of the payments you made was incomplete.  Please enter a valid amount.";
		}
		return isvalid;
	}
	
	public boolean isParsableToBig(String i)
	{
		try
		{			
			BigDecimal a = new BigDecimal(i);
			return true;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
	}
}
