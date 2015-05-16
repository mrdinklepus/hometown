package JUnit.com.hometown.testcases;

import java.math.BigDecimal;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Test;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;


public class SchedulePayTest {

	@Test
	public void ScheduleTest() {
		
		int uid = 1;
		int payeeid = 2;
		int accid = 2;
		BigDecimal amount = new BigDecimal("50.00");
		String date = "04-MAR-2004";
		
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		
		try {
			
			jndiContext = new InitialContext();
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			businessRulesRemote.schedulePayment(uid, accid, payeeid, amount, date);
			
					
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		
	}
	
}
