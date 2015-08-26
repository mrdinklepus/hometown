package viewCO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;
import appcontroller.TinySessionManager;

public class LoadDefaultVCO implements VCOInterface {

	@Override
	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{
		TinySessionManager tsm = TinySessionManager.getTinySessionManager();		
		TinySession aSession = (TinySession) req.getAttribute("session");			
		tsm.removeSession(aSession.getSessionID());
		
		Cookie killCookie = new Cookie("1234", " ");
		killCookie.setMaxAge(0);
		resp.addCookie(killCookie);
		
		System.out.println("I have maybe deleted the cookie,");
		
		try 
		{
			req.getRequestDispatcher("login.html").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		}	
	}
}