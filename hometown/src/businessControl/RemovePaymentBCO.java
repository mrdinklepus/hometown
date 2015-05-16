package businessControl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Person;

import appcontroller.TinySession;

public class RemovePaymentBCO implements BCOInterface {

	@Override
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		int paymentid = Integer.parseInt(req.getParameter("paymentid"));
		
		req.setAttribute("error", "");
		Person person = null;
		Context jndiContext;
		
		try
		{			
			jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);		
			String pname = businessRulesRemote.removePayment(uid, paymentid );
			
			req.setAttribute("error", "remsuc");
			req.setAttribute("remsuc", pname);			
			person = businessRulesRemote.getPersonPayee(uid);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return person;		
	}
}