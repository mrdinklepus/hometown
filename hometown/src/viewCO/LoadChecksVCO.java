package viewCO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Account;
import entityBeans.Person;


public class LoadChecksVCO implements VCOInterface
{
	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
			throws IOException
	{	
		Comparator<Account> acomp = new AccountComparator();
		Person person = (Person)req.getAttribute("reqObject");
		List<Account> accountList = new ArrayList<>();
		accountList.addAll(person.getAccounts());
		Collections.sort(accountList, acomp);

		req.setAttribute("alist", accountList);
		
		try 
		{
			req.getRequestDispatcher("WEB-INF/ordercheck.jsp").forward(req, resp);
		
		} catch (ServletException e) {
			e.printStackTrace();
		}		
	}
}