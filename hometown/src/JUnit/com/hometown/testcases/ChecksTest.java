package JUnit.com.hometown.testcases;


import java.math.BigDecimal;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Test;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;


public class ChecksTest {
	
	@Test
	public void orderChecksTest() {
		
		BigDecimal amount = new BigDecimal("100.00");
		int from = 2;
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		
		try {
			
			jndiContext = new InitialContext();
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			businessRulesRemote.orderChecks(from, amount, "4");
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
