package businessControl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import appcontroller.TinySession;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Person;

public class SchedulePaymentBCO implements BCOInterface {
	
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
		req.setAttribute("error", "");
		req.setAttribute("remsuc", "");
				
		int from = Integer.parseInt(req.getParameter("accountid"));
		int payeeid = Integer.parseInt(req.getParameter("payeeid"));		
		String a = req.getParameter("amt");
		String date = req.getParameter("date");
		BigDecimal amount = null;
		
		Person person = null;
		Context jndiContext;
		
		String isvalid = validate(a, date);
				
		try
		{			
			jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			if (isvalid.compareTo("isValid") != 0)
			{
				req.setAttribute("error", isvalid);
			}
			else
			{
				amount = new BigDecimal(a);
				businessRulesRemote.schedulePayment(uid, from, payeeid, amount, date);
				req.setAttribute("error", "suc");
			}
			person = businessRulesRemote.getPersonPayee(uid);
			
		}catch(Exception e){
			req.setAttribute("error", "jndierror");
			e.printStackTrace();
		}		
		return person;
	}
	
	public String validate(String am, String date)
	{
		String ret = "";
		boolean test = isParsableToBig(am);
		if (test)
		{
			boolean test2 = validateDate(date);
			if (test2)
			{
				ret = "isValid";
			}
			else
			{
				System.out.println("Invalid date");
				ret = "**Unable to Process Payment.  Please enter a valid date.  (Example: 22-Jan-2008)**";
			}
		}
		else
		{
			System.out.println("Invalid amount");
			ret = "**Unable to Process Payment.  Please enter a valid amount.**";
		}	
		return ret;
	}
	
	public boolean isParsableToBig(String i)
	{
		try
		{
			BigDecimal a = new BigDecimal(i);
			return true;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
	}

	//checks the date to make sure it is a valid date and not in the past.	 
	public static boolean validateDate(String dateStr){
       
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
		Date testDate = null;
		boolean allowPast = false;
		try
		{
			System.out.println("trying parse");
			testDate = df.parse(dateStr);
		}
		catch (ParseException e)
		{
			// invalid date format
			System.out.println("badParse");
			return false;
		}
		if (!allowPast)
		{
			// initialise the calendar to midnight to prevent 
			// the current day from being rejected
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			System.out.println("AllowedPast");
			
			if (cal.getTime().after(testDate)) {
				System.out.println("Calenderis False");
				return false;
			}
		}
		// now test for legal values of parameters
		if (!df.format(testDate).equals(dateStr)) {
			System.out.println(df.format(testDate) + "legal test has failed " + dateStr);
			return false;
		}
		System.out.println("legal test has passed");
		return true;
	}
}
