package viewCO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Account;
import entityBeans.Payments;
import entityBeans.Person;

public class LoadSchedulePaymentVCO implements VCOInterface {

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{
		Person person = (Person)req.getAttribute("reqObject");
		List<Account> accountList = new ArrayList<>();
		accountList.addAll(person.getAccounts());
		Collections.sort(accountList, new AccountComparator());
		
		List<Payments> paymentList = new ArrayList<>();
		paymentList.addAll(person.getScheduledPayments());
		Collections.sort(paymentList, new PaymentComparator());

		req.setAttribute("accountList", accountList);
		req.setAttribute("paymentList", paymentList);
		
		try 
		{
			req.getRequestDispatcher("WEB-INF/schedule.jsp").forward(req, resp);
		
		} catch (ServletException e) {
			e.printStackTrace();
		}	
	}
}