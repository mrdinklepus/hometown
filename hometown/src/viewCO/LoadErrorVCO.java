package viewCO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadErrorVCO implements VCOInterface {

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		try 
		{
			req.getRequestDispatcher("WEB-INF/networkErrorPage.jsp").forward(req, resp);
		
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}
