package businessControl;

import java.math.BigDecimal;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Person;

public class AddPayeeBCO implements BCOInterface{
	
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		
		String coname = req.getParameter("coname");
		String street = req.getParameter("street");
		String city = req.getParameter("city");
		String state = req.getParameter("state");
		String zip = req.getParameter("zip");
		String phone = req.getParameter("phone");
		String accnum = req.getParameter("accnum");
		req.setAttribute("error", "");
		
		System.out.println("Company name is " + coname + " and phone number is " + phone);
		
		Person person = null;
		Context jndiContext;
		
		String isvalid = validate(coname, street, city, state, zip, phone, accnum);
		
		try
		{	
			jndiContext = new InitialContext();			
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			if (isvalid.compareTo("isValid") != 0)
			{			
				req.setAttribute("error", isvalid);
			}
			else
			{
				businessRulesRemote.addPayee(uid, coname, street, city, state.toUpperCase(), zip, phone, accnum);
				req.setAttribute("error", "suc");
			}					
			person = businessRulesRemote.getPersonPayee(uid);
			
		}catch(Exception e){
			//req.setAttribute("error", "jndierror");
			e.printStackTrace();
		}
		return person;		
	}
	
	public String validate(String co, String str, String ci, String st, String z, String p, String an)
	{
		String test = null;
		if (p == "" || p.length() == 10)
		{
			if (co != "" && str != "" && ci != "" && st != "" && z != "" && an != "")
			{
				boolean testzip = isParsableToDouble(z);
				boolean testphone = isParsableToDouble(p);
				if (testzip == true && testphone == true)
				{
					test = "isValid";
				}else{
					System.out.println("Invalid zip or phone number");
					test = "**Unable to add Payee.  Invalid zip code or phone number.**";
				}				
			}else{
				System.out.println("Field was blank");
				test = "**Unable to add Payee.  Missing required information.**";
			}		
		}else{
			System.out.println("Phone not complete");
			test = "**Unable to add Payee.  Phone number must be 10 digits.**";
		}
		return test;
	}
	
	public boolean isParsableToDouble(String i)
	{
		try
		{
			if (i.compareTo("") != 0)
			{
				double a = Double.valueOf(i);
			}
			return true;
		}
		catch(NumberFormatException nfe)
		{
			System.out.println("Invalid number");
			return false;
		}
	}
}
