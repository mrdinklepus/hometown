package viewCO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadManagePayeesVCO implements VCOInterface {

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{    
		if (req.getParameter("cmd").toString().equals("confirmRemovePayee"))
		{
			try 
			{
				req.getRequestDispatcher("WEB-INF/confirmRemPayee.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			}			
		}
		else
		{
			try 
			{
				req.getRequestDispatcher("WEB-INF/managepayees.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}
}
