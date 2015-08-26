package appcontroller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import viewCO.VCOInterface;
import businessControl.BCOInterface;

public class HomeTownBrain 
{
  private CommandMap commandMap = new CommandMap();
  
	public HomeTownBrain()
	{
		
	}
		
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{						
		String cmd = req.getParameter("cmd");
		String bcoName = commandMap.getCommand(cmd);
		String vcoName = commandMap.getViewCommand(cmd);
		
		try
		{
		  // Special case - If logging out, just return
		  if ("logout".equals(bcoName))
		  {
		    VCOInterface vcoObj = (VCOInterface)Class.forName(vcoName).newInstance();
	      vcoObj.doDisplay(req, resp);
	      return;
		  }
		  
			BCOInterface bcoObj = (BCOInterface)Class.forName(bcoName).newInstance();			
			Object obj = bcoObj.doSomething(req, resp);
			
			if (obj != null && obj instanceof String && obj.equals("jndierror"))
			{
			  // Something went terribly wrong!
			  processError(req, resp);
			  return;
			}
			
			TinySessionManager tsm = TinySessionManager.getTinySessionManager();
			String sessionID = tsm.generateSessionString(req.getRemoteAddr());
			TinySession aSession = (TinySession)req.getAttribute("session");			
			
			if (aSession == null)
			{
				aSession = new TinySession(sessionID);
			}
			
			aSession.setSessionID(sessionID);
			Cookie newCookie = new Cookie("1234", sessionID);
			newCookie.setMaxAge(-1);
			resp.addCookie(newCookie);
			tsm.addSession(sessionID, aSession);
			req.setAttribute("session", aSession);
			
			req.setAttribute("reqObject", obj);
			VCOInterface vcoObj = (VCOInterface)Class.forName(vcoName).newInstance();
			vcoObj.doDisplay(req, resp);
		}
		catch (Exception e)
		{
		  processError(req, resp);
		}
	}
	
	public void invalidReq(HttpServletRequest req, HttpServletResponse resp)
	{	
		String vcoName = commandMap.getViewCommand("expiredSession");

		try
		{		
			VCOInterface vcoObj = (VCOInterface)Class.forName(vcoName).newInstance();
			vcoObj.doDisplay(req, resp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void processError(HttpServletRequest req, HttpServletResponse resp)
	{
		String vcoName = commandMap.getViewCommand("generalerror");
		
		try
		{		
			VCOInterface vcoObj = (VCOInterface)Class.forName(vcoName).newInstance();
			vcoObj.doDisplay(req, resp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
