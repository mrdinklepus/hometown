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
import entityBeans.Personpayee;

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
			Person per = businessRulesRemote.getPersonPayee(uid);
			
			Set<Personpayee> spp = per.getPersonpayeeCollection();
			
			Personpayee pp = null;
			
			for (Iterator iterator = spp.iterator(); iterator.hasNext();)
			{
				Personpayee tpp = (Personpayee)iterator.next();
				
				if (payee.getPayeeid() == (tpp.getPayeeid().getPayeeid()))
				{
					pp = tpp;
				}
			}
			req.setAttribute("pp", pp);
			System.out.println("Account number is " + pp.getPayeeaccountno());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return payee;	
	}
}

