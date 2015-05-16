package businessControl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;
import appcontroller.TinySessionManager;

import entityBeans.Person;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;

public class LoadChecksBCO implements BCOInterface{
	
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{	
		TinySession aSession = (TinySession) req.getAttribute("session");	
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		req.setAttribute("success", "");
		
		Person person = null;
		Context jndiContext;
		
		try
		{			
			jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			person = businessRulesRemote.getPerson(uid);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return person;		
	}
}
