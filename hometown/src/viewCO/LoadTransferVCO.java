package viewCO;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Account;
import entityBeans.Person;

public class LoadTransferVCO implements VCOInterface {

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{	
		Comparator acomp = new AccountComparator();
		Person person = (Person)req.getAttribute("reqObject");
		List list1 = new LinkedList();
		list1.addAll(person.getAccounts());
		Collections.sort(list1, acomp);

		req.setAttribute("alist", list1);
		req.setAttribute("to", "");
		req.setAttribute("from", "");
		req.setAttribute("amt", "");
		
		try 
		{
			req.getRequestDispatcher("WEB-INF/transfer.jsp").forward(req, resp);
		
		} catch (ServletException e) {
			e.printStackTrace();
		}	
	}
}