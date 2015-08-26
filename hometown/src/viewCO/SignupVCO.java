package viewCO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupVCO implements VCOInterface
{
	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
		throws IOException 
	{
		try 
		{
			req.getRequestDispatcher("signup.jsp").forward(req, resp);
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
	}
}