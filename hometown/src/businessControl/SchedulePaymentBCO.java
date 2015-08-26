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

public class SchedulePaymentBCO implements BCOInterface
{
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		TinySession aSession = (TinySession) req.getAttribute("session"); 				
		int uid = Integer.parseInt(aSession.getAttribute("personid").toString());
				
		int from = Integer.parseInt(req.getParameter("accountid"));
		int payeeid = Integer.parseInt(req.getParameter("payeeid"));		
		String a = req.getParameter("amt");
		String dateString = req.getParameter("date");
		
		Person person = null;
		
		boolean isValidInput = true;
		Date date = validateDate(dateString);
		if (date == null)
		{
		  System.out.println("Invalid date");
		  req.setAttribute("error", "**Unable to Process Payment.  Please enter a valid date.  (Example: 22-Jan-2008)**");
      isValidInput = false;
		}
		else if (!validateAmount(a))
		{
		  System.out.println("Invalid amount");
		  req.setAttribute("error", "**Unable to Process Payment.  Please enter a valid amount.**");
		  isValidInput = false;
		}
				
		try
		{			
		  Context jndiContext = new InitialContext();
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			if (isValidInput)
			{
			  BigDecimal amount = new BigDecimal(a);
				person = businessRulesRemote.schedulePayment(uid, from, payeeid, amount, date);
				req.setAttribute("success", "Thank You!  Your payment has been scheduled.");
			}
			else
			{
			  person = businessRulesRemote.getPerson(uid);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "jndierror";
		}		
		return person;
	}
	
	public boolean validateAmount(String amount)
	{
		if (!isParsableToBig(amount))
		{
			return false;
		}	
		return true;
	}
	
	public boolean isParsableToBig(String i)
	{
		try
		{
			BigDecimal a = new BigDecimal(i);
			return true;
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
	}

	/**
	 * Checks the date to make sure it is a valid date and not in the past
	 */
	public static Date validateDate(String dateStr)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
		Date testDate = null;
		
		try
		{
			System.out.println("trying parse");
			testDate = df.parse(dateStr);
		}
		catch (ParseException e)
		{
			// invalid date format
			System.out.println("badParse");
			return null;
		}
		
		// initialize the calendar to midnight to prevent 
		// the current day from being rejected
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		System.out.println("AllowedPast");
		
		if (cal.getTime().after(testDate))
		{
			System.out.println("Calenderis False");
			return null;
		}
		
		// now test for legal values of parameters
		if (!df.format(testDate).equals(dateStr))
		{
			System.out.println(df.format(testDate) + "legal test has failed " + dateStr);
			return null;
		}
		
		System.out.println("legal test has passed");
		return testDate;
	}
}
