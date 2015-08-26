package viewCO;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entityBeans.Person;
import entityBeans.Account;

public class TransferVCO implements VCOInterface
{
	public void doDisplay(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{	
		if (req.getAttribute("error") != null && req.getAttribute("error").toString().equals("jndierror"))
		{
		  try
      {
        req.getRequestDispatcher("WEB-INF/generalErrorPage.jsp").forward(req, resp);
      } catch (ServletException e) {
        e.printStackTrace();
      }
		}
		else if (req.getParameter("cmd") != null && req.getParameter("cmd").toString().equals("confirmTransfer")
		            && req.getAttribute("error") == null)
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
			Person person = (Person)req.getAttribute("reqObject");
			List<Account> list = new ArrayList<>(person.getAccounts());
			Collections.sort(list, new AccountComparator());
			
			req.setAttribute("accountCollection", list);
			
			try 
			{
				req.getRequestDispatcher("WEB-INF/transfer.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}
}