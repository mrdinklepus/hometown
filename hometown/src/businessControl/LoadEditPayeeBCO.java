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
		req.setAttribute("error", "");
		
		Payee payee = null;
		Context jndiContext;
		
		try
		{			
			jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
					
			payee = businessRulesRemote.getpayeebyid(payeeid);
			Person per = businessRulesRemote.getPerson(uid);
			
			Set<PayeeAccount> spp = per.getPayeeAccounts();
			
			PayeeAccount pp = null;
			
			for (Iterator iterator = spp.iterator(); iterator.hasNext();)
			{
				PayeeAccount tpp = (PayeeAccount)iterator.next();
				
				if (payee.getPayeeid() == (tpp.getPayeeAccountKey().getPayeeid().getPayeeid()))
				{
					pp = tpp;
				}
			}
			req.setAttribute("pp", pp);
			System.out.println("Account number is " + pp.getPayeeAccountKey().getPayeeAccountNo());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return payee;	
	}
}

