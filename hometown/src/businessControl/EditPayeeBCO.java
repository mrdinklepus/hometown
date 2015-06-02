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

public class EditPayeeBCO implements BCOInterface{

	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		System.out.println("In EditPayee BCO");
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		
		int payeeid = Integer.parseInt(req.getParameter("payeeid"));
//		int addid = Integer.parseInt(req.getParameter("addressid"));
		PhoneType phoneid = PhoneType.valueOf(req.getParameter("phoneid"));
		int ppid = Integer.parseInt(req.getParameter("ppid"));
		String coname = req.getParameter("coname");
		String street = req.getParameter("street");
		String city = req.getParameter("city");
		String state = req.getParameter("state");
		String zip = req.getParameter("zip");
		String phone = req.getParameter("phone");
		String accnum = req.getParameter("accnum");
		req.setAttribute("error", "");
		
		String isvalid = validate(coname, street, city, state, zip, phone, accnum);
		
		System.out.println("Payee name is " + coname + " '" + phone + "'");
		System.out.println("Street address is " + street);
		System.out.println("City is " + city);
		
		Person person = null;
		Payee payee = null;
		
		Context jndiContext;
				
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
				businessRulesRemote.updatePayee(payeeid, phoneid, ppid, coname, 
								                street, city, state.toUpperCase(), zip, phone, accnum);
				req.setAttribute("error", "suc");
			}
				
			payee = businessRulesRemote.getpayeebyid(payeeid);
			Person per = businessRulesRemote.getPerson(uid);
			
			Set<PayeeAccount> spp = per.getPayeeAccounts();
			
			PayeeAccount pp = null;
			
			for (Iterator iterator = spp.iterator(); iterator.hasNext();)
			{
				PayeeAccount tpp = (PayeeAccount)iterator.next();
				if (payee.getPayeeid() == (tpp.getPayeeAccountKey().getPayeeid().getPayeeid()))
				{
					pp = tpp;
					System.out.println("Personpayee found!");
				}
			}
			req.setAttribute("pp", pp);
						
			System.out.println("Got Payee");
			System.out.println("account number is " + pp.getPayeeAccountKey().getPayeeAccountNo());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return payee;		
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
					test = "**Unable to update Payee.  Invalid zip code or phone number.**";
				}				
			}else{
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
			if (i.compareTo("") != 0)
			{
				double a = Double.valueOf(i);
			}
			return true;
		
		}catch(NumberFormatException nfe){
			System.out.println("Invalid number");
			return false;
		}
	}
}
