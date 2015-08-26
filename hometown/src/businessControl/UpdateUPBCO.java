package businessControl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;

public class UpdateUPBCO implements BCOInterface{

	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		
		String un = req.getParameter("un");
		String pw1 = req.getParameter("pw1");
		String pw2 = req.getParameter("pw2");
		
		if (pw1.equals(pw2) && !pw1.equals("") && !pw2.equals(""))
		{
			try
			{
				Context jndiContext = new InitialContext();
				BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
				String message = businessRulesRemote.updateUser(uid, un, pw1);
				
				if (message != null)
				{
				  req.setAttribute("error", message);
				}
				else
				{
				  req.setAttribute("success", "Thank You.  Your Username and Password have been changed.");
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
			req.setAttribute("error", "**Unable to process.  Passwords do not match.**");
		}		
		return null;
	}
}