/*

 * Created on Mar 31, 2005

 *

 * TODO To change the template for this generated file go to

 * Window - Preferences - Java - Code Style - Code Templates

 */

package appcontroller;

import java.util.Date;

import java.util.Enumeration;

import java.util.Hashtable;

/**
 * @author yenrab
 */
public class TinySession
{
	Hashtable attributes;
	long creationTime;
	long lastAccessTime;
	int maxInactiveInterval;
	boolean valid;
	String sessionID;

	public TinySession(String aSessionID)
	{
		sessionID = aSessionID;
		attributes = new Hashtable();
		creationTime = new Date().getTime();
		lastAccessTime = creationTime;
		maxInactiveInterval = 1000*60*1000;  //This time is in milliseconds (1000 minutes for now)
		valid = true;
	}

	public void updateAccessed()
	{
		lastAccessTime = new Date().getTime();
	}

	public long getCreationTime() 
	{
		return creationTime;
	}

	public String getSessionID()
	{
		return sessionID;
	}
	
	public void setSessionID(String sessionID) 
	{
		this.sessionID = sessionID;
	}

	public long getLastAccessedTime() 
	{
		return lastAccessTime;
	}

	public void setMaxInactiveInterval(int aInt) 
	{
		maxInactiveInterval = aInt;
		System.out.println("setting maxInactiveInterval: "+maxInactiveInterval);
	}

	public int getMaxInactiveInterval() 
	{
		return maxInactiveInterval;
	}

	public Object getAttribute(String aAttName) 
	{
		return attributes.get(aAttName);
	}

	public Enumeration getAttributeNames() 
	{
		return attributes.keys();
	}

	public void setAttribute(String aAttName, Object aAttribute) 
	{
		attributes.put(aAttName, aAttribute);
	}

	public void removeAttribute(String aAtt) 
	{
		attributes.remove(aAtt);
	}
	
	public boolean isExpired()
	{
		System.out.println("lastAccess = "+this.lastAccessTime+" max = "+this.maxInactiveInterval);
		System.out.println("invalid time = "+(this.lastAccessTime + this.maxInactiveInterval));
		Date curDate = new Date();
		System.out.println("curtime = "+curDate.getTime());
		
		if (this.lastAccessTime + (long)this.maxInactiveInterval < curDate.getTime())
		{
			return true;
		}
		return false;
	}
}