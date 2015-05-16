package businessControl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Person;

public class UpdateUPBCO implements BCOInterface{

	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		
		String un = req.getParameter("un");
		String pw1 = req.getParameter("pw1");
		String pw2 = req.getParameter("pw2");
		String message = "";
		System.out.println("user is " + un);
		System.out.println("user id is " + uid);
		System.out.println("pw is " + un);
		
		if (pw1.equals(pw2) && !pw1.equals("") && !pw2.equals(""))
		{
			Context jndiContext;
			try
			{
				jndiContext = new InitialContext();
				BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
				message = businessRulesRemote.updateUser(uid, un, pw1);
				req.setAttribute("error", message);
				System.out.println("error message is " + message);
								
			}catch(Exception e){

				e.printStackTrace();
			}	
		}
		else
		{
			req.setAttribute("error", "**Unable to process.  Passwords do not match.**");
		}		
		return null;
	}
}