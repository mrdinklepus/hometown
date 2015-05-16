package businessControl;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import entityBeans.Person;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;

public class LoadTransferBCO implements BCOInterface{
	
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{	
		req.setAttribute("error", "");
		req.setAttribute("validate", "");
		
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		
		Person person = null;
		Context jndiContext;
		
		try
		{
			jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);			
			person = businessRulesRemote.getPerson(uid);
			
		}catch(Exception e){
			e.printStackTrace();
			req.setAttribute("error", "jndierror");
		}		
		return person;		
	}
}
