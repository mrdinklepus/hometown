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
   
	public HomeTownServlet() 
	{
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cmd = request.getParameter("cmd");
		System.out.println("Request is " + cmd);
		
		if (cmd.equals("login") || cmd.equals("signup"))
		{			
			HomeTownBrain.processRequest(request, response);
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
					   //System.out.println("HasCookie");
					   //System.out.println("Sessionid: " + sessionid);			       
				   }    
			   }
			}
			
			if (sessionid != null)
			{		
				aSession = tsm.getSession(sessionid);					
				if (aSession != null)
				{					
					request.setAttribute("session", aSession);			
					HomeTownBrain.processRequest(request, response);
//					response.setHeader("Cache-Control","no-store, no-cache, must-revalidate"); //HTTP 1.1
//					response.setHeader("Pragma","no-cache"); //HTTP 1.0
//					response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
				}
				else
				{
					HomeTownBrain.invalidReq(request, response);
				}
			}
			else
			{
				HomeTownBrain.invalidReq(request, response);
			}
		}
	}
					
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		String cmd = request.getParameter("cmd");
		
		if (cmd.equals("login") || cmd.equals("signup"))
		{
			HomeTownBrain.processRequest(request, response);
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
					   System.out.println("HasCookie");
					   System.out.println("Sessionid: " + sessionid);			       
				   }    
			   }
			}
			if (sessionid != null)
			{					
				aSession = tsm.getSession(sessionid);	
				
				if (aSession != null)
				{
					try
					{
						System.out.println("Session is not null");
						request.setAttribute("session", aSession);			
						HomeTownBrain.processRequest(request, response);
					}catch(Exception e){
						HomeTownBrain.processError(request, response);
					}
				}
				else
				{
					System.out.println("Session IS null");
					HomeTownBrain.invalidReq(request, response);
				}
			}
			else
			{
				System.out.println("Session Invalid");
				HomeTownBrain.invalidReq(request, response);
			}
		}
	}
 }