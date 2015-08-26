package businessControl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import appcontroller.TinySession;
import entityBeans.Person;

public class AddPayeeBCO implements BCOInterface{
	
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession)req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		
		String coname = req.getParameter("coname");
		String street = req.getParameter("street");
		String city = req.getParameter("city");
		String state = req.getParameter("state");
		String zip = req.getParameter("zip");
		String phone = req.getParameter("phone");
		String accnum = req.getParameter("accnum");
		
		System.out.println("Company name is " + coname + " and phone number is " + phone);
		
		Person person = null;
		
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
				businessRulesRemote.addPayee(uid, coname.toUpperCase(), street, city, state.toUpperCase(), zip, phone, accnum);
				req.setAttribute("success", "Payee added!  You may now make payments to " + coname + ".");
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
	
	public String validate(String co, String str, String ci, String st, String z, String p, String an)
	{
		String test = null;
		if (p.isEmpty() || p.length() == 10)
		{
			if (co.isEmpty() || str.isEmpty() || ci.isEmpty() || st.isEmpty() || z.isEmpty() || an.isEmpty())
			{
        System.out.println("Field was blank");
        test = "**Unable to add Payee.  Missing required information.**";
      }
		  else
		  {
				boolean testzip = isParsableToDouble(z);
				boolean testphone = isParsableToDouble(p);
				if (!testzip || !testphone)
				{
					System.out.println("Invalid zip or phone number");
					test = "**Unable to add Payee.  Invalid zip code or phone number.**";
				}				
			}
		}
		else
		{
			System.out.println("Phone not complete");
			test = "**Unable to add Payee.  Phone incomplete - number must be 10 digits.**";
		}
		return test;
	}
	
	public boolean isParsableToDouble(String i)
	{
		try
		{
			if (!i.isEmpty())
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
