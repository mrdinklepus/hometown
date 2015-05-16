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
		
		req.setAttribute("error", "");
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
		Context jndiContext;
		
		BigDecimal amount = new BigDecimal(req.getParameter("amt"));
			
		try
		{	
			jndiContext = new InitialContext();			
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);														
			businessRulesRemote.transfer(to, from, amount, desc);
			System.out.println("Transfer Successful");
			req.setAttribute("error", "suc");			
			person = businessRulesRemote.getPerson(uid);							

		}catch(EJBException e){
			e.printStackTrace();
			req.setAttribute("error", "jndierror");
			
		}catch(Exception ex){
			ex.printStackTrace();
			req.setAttribute("error", "jndierror");
		}
		return person;				
	}
}