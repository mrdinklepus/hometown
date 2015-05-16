package businessControl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmRemovePayeeBCO implements BCOInterface{

	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{				
		int payeeid = Integer.parseInt(req.getParameter("payeeid"));
		String coname = req.getParameter("payeeCompany").toString();
		req.setAttribute("coname", coname);
		req.setAttribute("payeeid", payeeid);		
		return null;		
	}
}
