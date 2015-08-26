package businessControl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Person;

public class RemovePayeeBCO implements BCOInterface {

	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{	
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
				
		int payeeid = Integer.parseInt(req.getParameter("payeeid"));
		Person person = null;
		
		try
		{			
			Context jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);			
			person = businessRulesRemote.removePayee(uid, payeeid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "jndierror";
		}
		return person;		
	}	
}