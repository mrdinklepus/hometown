package businessControl;

import java.math.BigDecimal;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;


import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Account;
import entityBeans.Person;

public class TransferBCO implements BCOInterface{
	
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		req.setAttribute("validate", "");
		req.setAttribute("to", "");
		req.setAttribute("from", "");
		req.setAttribute("amt", "");
		
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		int to = Integer.parseInt(req.getParameter("to"));
		int from = Integer.parseInt(req.getParameter("from"));
		String desc = req.getParameter("tDescription").toString();
		Person person = null;
		
		BigDecimal amount = new BigDecimal(req.getParameter("amt"));
			
		try
		{	
		  Context jndiContext = new InitialContext();			
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			businessRulesRemote.transfer(to, from, amount, desc);
			person = businessRulesRemote.getPerson(uid);
			req.setAttribute("success", "Thank You.  Your Transfer has been made.  You may make another transfer.");
		} catch (Exception ex) {
			ex.printStackTrace();
			return "jndierror";
		}
		return person;				
	}
}