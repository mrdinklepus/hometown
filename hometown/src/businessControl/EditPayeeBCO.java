package businessControl;

import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import appcontroller.TinySession;
import entityBeans.Payee;
import entityBeans.Person;
import entityBeans.PayeeAccount;
import entityBeans.PhoneType;

public class EditPayeeBCO implements BCOInterface
{
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		int payeeid = Integer.parseInt(req.getParameter("payeeid"));
		
//		PhoneType phoneType = null;
//		String phoneTypeString = req.getParameter("phonetype");
//		if (phoneTypeString != null)
//		{
//		  phoneType = PhoneType.valueOf(phoneTypeString);
//		}
		
		String coname = req.getParameter("coname");
		String street = req.getParameter("street");
		String city = req.getParameter("city");
		String state = req.getParameter("state");
		String zip = req.getParameter("zip");
		String phone = req.getParameter("phone");
		String origaccnum = req.getParameter("origaccnum");
		String accnum = req.getParameter("accnum");
		
		Payee payee = null;
				
		try
		{			
		  Context jndiContext = new InitialContext();			
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			String isvalid = validate(coname, street, city, state, zip, phone, accnum);
			if (isvalid != null)
			{
				req.setAttribute("error", isvalid);
			}
			else
			{
			  payee = businessRulesRemote.updatePayee(payeeid, uid, PhoneType.MAIN, origaccnum, coname, 
								                        street, city, state.toUpperCase(), zip, phone, accnum);
				req.setAttribute("success", "Information for " + coname + " has been successfully updated.");
			}
			
			Person per = businessRulesRemote.getPerson(uid);
			Set<PayeeAccount> spp = per.getPayeeAccounts();
			
			PayeeAccount pa = null;
			
			for (Iterator<PayeeAccount> it = spp.iterator(); it.hasNext();)
			{
				PayeeAccount tpp = it.next();
				if (payee.getPayeeid() == (tpp.getPayeeAccountKey().getPayeeid().getPayeeid()))
				{
					pa = tpp;
					break;
				}
			}
			req.setAttribute("payeeAccount", pa);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "jndierror";
		}
		return payee;		
	}
	
	public String validate(String co, String str, String ci, String st, String z, String p, String an)
	{
		String test = null;
		
		if (p.isEmpty() || p.length() == 10)
		{
			if (co != "" && str != "" && ci != "" && st != "" && z != "" && an != "")
			{
				boolean testzip = isParsableToDouble(z);
				boolean testphone = isParsableToDouble(p);
				if (!testzip && !testphone)
				{
					System.out.println("Invalid zip or phone number");
					test = "**Unable to update Payee.  Invalid zip code or phone number.**";
				}				
			} else {
				System.out.println("Field was blank");
				test = "**Unable to update Payee.  Missing required information.**";
			}		
		}
		else
		{
			System.out.println("Phone not complete");
			test = "**Unable to update Payee.  Phone number must be 10 digits.**";
		}
		return test;
	}
	
	public boolean isParsableToDouble(String i)
	{
		try
		{
			if (!i.isEmpty())
			{
				Double.valueOf(i);
			}
			return true;
		}
		catch (NumberFormatException nfe)
		{
			System.out.println("Invalid number");
			return false;
		}
	}
}
