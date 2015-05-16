package businessControl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import entityBeans.Person;
import entityBeans.Payee;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;

public class LoadBillPayBCO implements BCOInterface{
	
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		
		List<Payee> list1 = new ArrayList();
		req.setAttribute("success", "");
		req.setAttribute("error", "");
		req.setAttribute("payid", "");
		req.setAttribute("errlist1", list1);

		Person person = null;
		Context jndiContext;
		
		try
		{			
			jndiContext = new InitialContext();			
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);			
			person = businessRulesRemote.getPersonPayee(uid);

		}catch(Exception e){
			
		}		
		return person;
	}
}
