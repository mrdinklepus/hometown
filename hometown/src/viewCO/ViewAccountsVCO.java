package viewCO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;
import appcontroller.TinySessionManager;

import entityBeans.Account;
import entityBeans.Person;

public class ViewAccountsVCO implements VCOInterface {
	
	public void doDisplay(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{						
		TinySession aSession = (TinySession)req.getAttribute("session");	
		Person person = (Person)req.getAttribute("reqObject");
	
		if (person == null)
		{		
			System.out.println("bad username");
			resp.sendError(985,"Incorrect username or password!");			
		}
		else
		{
			aSession.setAttribute("personid", person.getPersonid() );
			
			try
			{
				System.out.println("displaying viewAccounts");
				req.getRequestDispatcher("WEB-INF/viewaccounts.jsp").forward(req, resp);
				System.out.println("View Accounts displayed");
			
			}catch (Exception e){
				e.printStackTrace();
			}
		}	
	}	
}
