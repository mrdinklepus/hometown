package viewCO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;


import entityBeans.Account;
import entityBeans.AccountType;
import entityBeans.Person;

public class loginVCO implements VCOInterface {

	public void doDisplay(Object obj, HttpServletResponse resp) throws IOException 
	{
	  if (obj == null || !(obj instanceof Person))
	  {
	    return;
	  }
	  
		StringBuffer sb = new StringBuffer();
		
		PrintWriter out = resp.getWriter();
		Person person = (Person)obj;
		
		sb.append("<div id=\"header\">");
		sb.append("<ul><li class=\"large\">View Account Information</li>");
		sb.append("<li class=\"small\">Click on an account to view more details</li>");
		sb.append("</ul></div>");
		sb.append("<div id=\"accounts\">");
		
		Set<Account> aCollection = person.getAccounts();
		Iterator<Account> it = aCollection.iterator();
		
		while (it.hasNext())
		{
			Account aAccount = (Account)it.next();
			
			if (aAccount.getAccountType() == AccountType.CHECKING)
			{
				sb.append("<div class=\"title\" onclick=\"toggle('checking');\">Checking <span id=\"checkingtotal\"></span></div>");
				sb.append("<div id=\"checking\" class=\"accountType\">");
			}
			else if (aAccount.getAccountType() == AccountType.SAVINGS)
			{
				sb.append("<div class=\"title\" onclick=\"toggle('savings');\">Savings <span id=\"savingstotal\"></span></div>");
				sb.append("<div id=\"savings\" class=\"accountType\">");
			}
			else if (aAccount.getAccountType() == AccountType.CREDIT)
			{
				sb.append("<div class=\"title\" onclick=\"toggle('credit');\">Credit Cards <span id=\"credittotal\"></span></div>");
				sb.append("<div id=\"credit\" class=\"accountType\">");
			}
			else if (aAccount.getAccountType() == AccountType.SECURITY)
			{
				sb.append("<div class=\"title\" onclick=\"toggle('cashdeposit');\">Cash Deposit (CD's) <span id=\"cashdeposittotal\"></span></div>");
				sb.append("<div id=\"cashdeposit\" class=\"accountType\">");
			}
			
			sb.append("<div class=\"caccount\">");
      sb.append("<ul>");
      sb.append("<li class=\"accountName\">");
      sb.append(aAccount.getAccountNo());
      sb.append("</li>");
      sb.append("<li>");
      sb.append(aAccount.getBalance());
      sb.append("</li>");
      sb.append("</ul>");
      sb.append("</div>");
      sb.append("</div>");
		}
		sb.append("</div></div>");
		
		out.print(sb.toString());
	}

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{				
		TinySession aSession = (TinySession)req.getAttribute("session");
		System.out.println("Session: " + aSession);
		StringBuffer sb = new StringBuffer();
		
		PrintWriter out = resp.getWriter();
		Person person = (Person)req.getAttribute("reqObject");
		
		if (person == null || !(person instanceof Person))
    {		
			resp.sendError(985,"Incorrect username or password!");			
		}
		
		aSession.setAttribute("personid", person.getPersonid());
				
		sb.append("<div id=\"header\">");
		sb.append("<div class=\"welcome\"> Welcome " + person.getFullname() +"!</div>");
		sb.append("<ul><li class=\"large\">View Account Information</li>");
		sb.append("<li class=\"small\">Click on an account to view more details</li>");
		sb.append("</ul></div>");
		sb.append("<div id=\"accounts\">");
		sb.append("<div class=\"title\" onclick=\"toggle('checking');\">Checking <span id=\"checkingtotal\"></span></div>");
		sb.append("<div id=\"checking\" class=\"accountType\">");
		
		Set<Account> aCollection = person.getAccounts();
		Iterator<Account> it = aCollection.iterator();
		
		while (it.hasNext())
		{
			Account aAccount = (Account)it.next();
			
			sb.append("<div class=\"caccount\">");
			sb.append("<ul>");
				sb.append("<li class=\"accountName\">");
					sb.append(aAccount.getAccountNo());
				sb.append("</li>");
				sb.append("<li>");
					sb.append(aAccount.getBalance());
				sb.append("</li>");
			sb.append("</ul>");
			sb.append("</div>");
		}
		sb.append("</div></div>");
		
		out.print(sb.toString());
	}
}
