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
		
		Person person = null;
		
		try
		{			
		  Context jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			businessRulesRemote.orderChecks(uid, accountid, amount, checkid);
			
			req.setAttribute("success", "Thank You for ordering from Hometown Webbank.  Your checks have been ordered!");
			person = businessRulesRemote.getPerson(uid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "jndierror";
		}
		return person;		
	}
}
