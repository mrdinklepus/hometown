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

public class LoadBillPayVCO implements VCOInterface {

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{
		Person person = (Person)req.getAttribute("reqObject");
		List<Account> accountList = new ArrayList<>(person.getAccounts());
		Collections.sort(accountList, new AccountComparator());
		req.setAttribute("billpayAccounts", accountList);
		
		try 
		{
			req.getRequestDispatcher("WEB-INF/billpay.jsp").forward(req, resp);
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
	}
}