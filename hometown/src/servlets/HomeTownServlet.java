package servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.HomeTownBrain;
import appcontroller.TinySession;
import appcontroller.TinySessionManager;

/**
 * Servlet implementation class for Servlet: HomeTownServlet
 *
 */
 public class HomeTownServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet 
 {
   static final long serialVersionUID = 1L;
   private HomeTownBrain brain = new HomeTownBrain();
   
	public HomeTownServlet() 
	{
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	  handleRequest(request, response);
	}
					
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		handleRequest(request, response);
	}
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	  String cmd = request.getParameter("cmd");
    
    if (cmd.equals("login") || cmd.equals("signup"))
    {
      brain.processRequest(request, response);
    }
    else
    {   
      TinySessionManager tsm = TinySessionManager.getTinySessionManager();
      TinySession aSession = null;
      
      String sessionid = null;
      Cookie[] cookieArray = request.getCookies();
      if (cookieArray != null)
      {     
         for (int i =0; i< cookieArray.length; i++)
         {          
           Cookie c = cookieArray[i];
  
           if (c.getName().equals("1234"))
           {           
             sessionid = c.getValue();         
           }    
         }
      }
      
      if (sessionid != null)
      {         
        aSession = tsm.getSession(sessionid); 
        
        if (aSession != null)
        {
          request.setAttribute("session", aSession);      
          brain.processRequest(request, response);
//          response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
        }
        else
        {
          brain.invalidReq(request, response);
        }
      }
      else
      {
        brain.invalidReq(request, response);
      }
    }
	}
}
 
