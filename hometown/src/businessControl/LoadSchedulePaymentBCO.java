package businessControl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Person;

public class LoadSchedulePaymentBCO implements BCOInterface {

	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		req.setAttribute("error", "");
		req.setAttribute("remsuc", "");
		
		Person person = null;
		Context jndiContext;
		
		try
		{	
			jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			person = businessRulesRemote.getPersonPayee(uid);			
			System.out.println("Collection Size is " + person.getPaymentCollection().size());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return person;		
	}	
}
