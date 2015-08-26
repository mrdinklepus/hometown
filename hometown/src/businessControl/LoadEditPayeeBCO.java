package businessControl;

import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Payee;
import entityBeans.Person;
import entityBeans.PayeeAccount;

public class LoadEditPayeeBCO implements BCOInterface{
	
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{	
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());				
		int payeeid = Integer.parseInt(req.getParameter("payeeid"));
		
		Payee payee = null;
		
		try
		{			
		  Context jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
					
			payee = businessRulesRemote.getpayeebyid(payeeid);
			Person per = businessRulesRemote.getPerson(uid);
			
			Set<PayeeAccount> payeeAccounts = per.getPayeeAccounts();
			
			PayeeAccount payeeAccount = null;
			
			Iterator<PayeeAccount> it = payeeAccounts.iterator();
			while (it.hasNext())
			{
				PayeeAccount tpp = it.next();
				
				if (payee.getPayeeid() == (tpp.getPayeeAccountKey().getPayeeid().getPayeeid()))
				{
				  payeeAccount = tpp;
				}
			}
			req.setAttribute("payeeAccount", payeeAccount);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return payee;	
	}
}

