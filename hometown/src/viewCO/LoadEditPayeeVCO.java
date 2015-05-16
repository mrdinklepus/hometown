package viewCO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Address;
import entityBeans.Payee;
import entityBeans.Phone;

public class LoadEditPayeeVCO implements VCOInterface {
	
	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
	throws IOException 
	{
		Payee p = (Payee)req.getAttribute("reqObject");
		Address add = p.getAddressid();
		Phone ph = p.getPhoneid();
		req.setAttribute("address", add);
		req.setAttribute("phone", ph);
		
		try 
		{
			req.getRequestDispatcher("WEB-INF/editpayee.jsp").forward(req, resp);
		
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}