package appcontroller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import viewCO.VCOInterface;
import businessControl.BCOInterface;

public class HomeTownBrain 
{	
	public HomeTownBrain()
	{
		
	}
		
	public static void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{						
		String cmd = req.getParameter("cmd");		
		String bcoName = CommandMap.getCommand(cmd);
		String vcoName = CommandMap.getViewCommand(cmd);
		
		try
		{	
			System.out.println(cmd + " , " + bcoName + " , " + vcoName);		
			BCOInterface bcoObj = (BCOInterface)Class.forName(bcoName).newInstance();			
			Object obj = bcoObj.doSomething(req, resp);
			
			if (obj != null)
			{				
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
				System.out.println("Session Saved");
			}			
			req.setAttribute("reqObject", obj);				
			VCOInterface vcoObj = (VCOInterface)Class.forName(vcoName).newInstance();
			vcoObj.doDisplay(req, resp);
			
		}catch(ClassNotFoundException e){
				e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void invalidReq(HttpServletRequest req, HttpServletResponse resp)
	{	
		String vcoName = CommandMap.getViewCommand("expiredSession");

		try
		{		
			VCOInterface vcoObj = (VCOInterface)Class.forName(vcoName).newInstance();
			vcoObj.doDisplay(req, resp);
				
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void processError(HttpServletRequest req, HttpServletResponse resp)
	{
		String vcoName = CommandMap.getViewCommand("error");
		
		try
		{		
			VCOInterface vcoObj = (VCOInterface)Class.forName(vcoName).newInstance();
			vcoObj.doDisplay(req, resp);
				
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
