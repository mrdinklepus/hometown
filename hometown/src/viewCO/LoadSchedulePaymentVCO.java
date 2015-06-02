package viewCO;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Person;

public class LoadSchedulePaymentVCO implements VCOInterface {

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{	
		Comparator acomp = new AccountComparator();
		Comparator dcomp = new DateComparator();
		Person person = (Person)req.getAttribute("reqObject");
		List list1 = new LinkedList();
		list1.addAll(person.getAccounts());
		Collections.sort(list1, acomp);
		
		List list2 = new LinkedList();
		list2.addAll(person.getScheduledPayments());
		Collections.sort(list2, dcomp);

		req.setAttribute("alist", list1);
		req.setAttribute("dlist", list2);
		
		try 
		{
			req.getRequestDispatcher("WEB-INF/schedule.jsp").forward(req, resp);
		
		} catch (ServletException e) {
			e.printStackTrace();
		}	
	}
}