package businessControl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;

public class SignupBCO implements BCOInterface 
{
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		String fn = req.getParameter("fn");
		String ln = req.getParameter("ln");
		String phone = req.getParameter("phone");
		String un = req.getParameter("un");
		String pw = req.getParameter("pw");
		String pw2 = req.getParameter("pw2");
		req.setAttribute("fn", fn);
		req.setAttribute("ln", ln);
		req.setAttribute("phone", phone);
		req.setAttribute("un", un);
		req.setAttribute("pw", pw);
		req.setAttribute("pw2", pw2);
		
		if (pw.equals(pw2))
		{
			String test = validate(fn, ln, phone, un, pw);
			
			if (test.equals("isValid"))
			{
				try
				{
					Context jndiContext;
					jndiContext = new InitialContext();			
					BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
					String error = businessRulesRemote.signup(fn,ln,phone,un,pw);
					
					if (!error.isEmpty())
					{
						req.setAttribute("error", error);
					}
					else
					{
						req.setAttribute("success", "Thank You!  Your Online User Account has been created.");
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return "jndierror";
				}
			}	
			else
			{
				req.setAttribute("error", test);
			}
		}
		else
		{
			req.setAttribute("error", "**Cannot setup account.  Passwords do not match.**");
		}		
		return null;
	}	
	
	public String validate(String fn, String ln, String p, String un, String pw)
	{
		String test = null;
		if (fn != "" && ln != "" && p != "" && un != "" && pw != "")		
		{
			if (p.length() == 10)
			{
				boolean testphone = isParsableToDouble(p);
				if (testphone == true)
				{
					test = "isValid";
				}else{
					System.out.println("Invalid phone number");
					test = "**Unable to setup account.  Invalid phone number.**";
				}				
			}else{
				System.out.println("Phone not complete");
				test = "**Unable to setup account.  Phone number must be 10 digits.**";
			}		
		}else{
			System.out.println("Field was blank");
			test = "**Unable to setup account.  Missing required information.**";			
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
