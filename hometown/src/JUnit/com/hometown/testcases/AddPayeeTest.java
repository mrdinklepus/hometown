package JUnit.com.hometown.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Test;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;
import entityBeans.Account;

public class AddPayeeTest {

	@Test
	public void billTest() {
		
		int uid = 1;
		String coname = "BYU Idaho";
		String street = "500 E.";
		String city = "Terreton";
		String state = "ID";
		String zip = "83450";
		String phone = "2083568888";
		String accnum = "222222";
		
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		
		try {
			
			jndiContext = new InitialContext();
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			businessRulesRemote.addPayee(uid, coname, street, city, state, zip, phone, accnum);
			
					
		}catch(Exception ex){
			ex.printStackTrace();
			
		}

		
	}
	
}
