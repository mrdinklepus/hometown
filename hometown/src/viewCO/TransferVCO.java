package viewCO;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entityBeans.Person;
import entityBeans.Account;

public class TransferVCO implements VCOInterface {

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{	
		if (!req.getAttribute("error").toString().equals("jndierror"))
		{		
			if (req.getParameter("cmd").toString().equals("confirmTransfer") && req.getAttribute("validate").toString().equals("true"))
			{
				try 
				{
					req.getRequestDispatcher("WEB-INF/confirmTransfer.jsp").forward(req, resp);
				
				} catch (ServletException e) {
					e.printStackTrace();
				}
			}
			else
			{		
				System.out.println("in else");
				Comparator acomp = new AccountComparator();
				Person person = (Person)req.getAttribute("reqObject");
				List list1 = new LinkedList();
				list1.addAll(person.getAccountCollection());
				Collections.sort(list1, acomp);
					
				req.setAttribute("alist", list1);
				
				try 
				{
					req.getRequestDispatcher("WEB-INF/transfer.jsp").forward(req, resp);
				
				} catch (ServletException e) {
					e.printStackTrace();
				}
			}			
		}
		else
		{
			try {
				req.getRequestDispatcher("WEB-INF/networkErrorPage.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}
}