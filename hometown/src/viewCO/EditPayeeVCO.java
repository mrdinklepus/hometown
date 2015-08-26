package viewCO;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Address;
import entityBeans.Payee;
import entityBeans.Phone;

public class EditPayeeVCO implements VCOInterface {
	
	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
	throws IOException 
	{
		Payee p = (Payee)req.getAttribute("reqObject");
		Set<Phone> ph = p.getPhones();
		req.setAttribute("phone", ph.iterator().next());
		
		try 
		{
			req.getRequestDispatcher("WEB-INF/editpayee.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}