package businessControl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Account;
import entityBeans.Person;

public class ViewDetailedAccountBCO implements BCOInterface {
	
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{		
		int uid = Integer.parseInt(req.getParameter("accountId")); 
		Account account = null;
		Context jndiContext;
		
		try
		{		
			jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			account = businessRulesRemote.getAccount(uid);
			System.out.println("getAccount Successful");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return account;	
	}
}