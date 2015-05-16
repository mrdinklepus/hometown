package businessControl;

import java.math.BigDecimal;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Person;

public class OrderChecksBCO implements BCOInterface
{	
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session");	
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		
		String checkid = req.getParameter("cid");
		int accountid = Integer.parseInt(req.getParameter("aid"));
		BigDecimal amount = new BigDecimal(req.getParameter("amt"));
		req.setAttribute("success", "");
		
		Person person = null;
		Context jndiContext;
		
		try
		{			
			jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			businessRulesRemote.orderChecks(accountid, amount, checkid);
			
			req.setAttribute("success", "yes");
			person = businessRulesRemote.getPerson(uid);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return person;		
	}
}
