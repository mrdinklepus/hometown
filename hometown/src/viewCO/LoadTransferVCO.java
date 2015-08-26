package viewCO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Account;
import entityBeans.Person;

public class LoadTransferVCO implements VCOInterface
{
	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
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