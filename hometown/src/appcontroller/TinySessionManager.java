/*

 * Created on Mar 31, 2005

 *

 * TODO To change the template for this generated file go to

 * Window - Preferences - Java - Code Style - Code Templates

 */

package appcontroller;

import java.util.Date;

import java.util.Hashtable;

/**
 * @author yenrab
 *
 * This class does not conform to the J2EE standards and is not
 * intended to replace the full functionallity of such sessions and their managers.
 * It is intended to be a simple way of tracking sessions that can be more secure.
 */
public class TinySessionManager 
{
	protected Hashtable sessions;
	protected static int sessionCount = 0;
	//private static TinySessionManager myInstance;

	private TinySessionManager() 
	{
		sessions = new Hashtable();
	}

	private static class ManagerSingleton 
	{
		private final static TinySessionManager myInstance = new TinySessionManager();
	}
	
	public static TinySessionManager getTinySessionManager()
	{
		return ManagerSingleton.myInstance;
	}

	/**
	 * @param anIP the IP Adress of the remote client
	 * @return aUnique
	 * 
	 * If a session object is composed of the requesting IP, 
	 * the current system time of the server, and a count of how many
	 * sessions have been generated by the session manager, then it should
	 * be unique and very difficult to predict the next value.  This shoud
	 * solve most Cookie poisoning session access issues.
	 */
	public String generateSessionString(String anIP)
	{
		sessionCount++;
		//anIP.replaceAll(".", "");
		//ipInt = ipInt*(int)Math.rint(Math.random()*13);//this line would reduct uniqueness of the result
		long curTimeCalc = new Date().getTime();
		return anIP+Integer.toString(sessionCount)+ Long.toString(curTimeCalc);
	}
	
	public void addSession(String sessionID, TinySession aSession)
	{
		System.out.println("sessionID = "+sessionID+"session = "+aSession);
		sessions.put(sessionID, aSession);
	}

	public TinySession getSession(String sessionID)
	{
		TinySession aSession = (TinySession)sessions.get(sessionID);
		//System.out.println("session num = "+sessions.size());
		//System.out.println("in getSession = "+aSession);

		if(aSession != null)
		{
			if(aSession.isExpired())
			{
				//System.out.println("session expired");
				sessions.remove(sessionID);
				aSession = null;
			}
			else
			{
				aSession.updateAccessed();
			}
		}
		return aSession;
	}

	public void removeSession(String sessionID)
	{
		sessions.remove(sessionID);
	}
}