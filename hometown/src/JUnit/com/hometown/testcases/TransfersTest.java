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
import entityBeans.Person;


public class TransfersTest {

	@Test
	public void testMakeTransfer(){
//		Person aPerson = new Person();
//			aPerson.setPersonid(1);
//		Account aAccount = new Account("C", new BigDecimal("6000.00"), "1001", aPerson);
//		Account bAccount = new Account("R", new BigDecimal("4000.00"), "50101", aPerson);
		
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		try{
			jndiContext = new InitialContext();
			
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
		
			businessRulesRemote.transfer(1, 2, new BigDecimal("250.00"), "Transfer To Jack");	
			
			Account xAccount = businessRulesRemote.getAccount(1);
			Account yAccount = businessRulesRemote.getAccount(2);
			
//			assertEquals(aAccount.getBalance(), xAccount.getBalance());
//			assertEquals(bAccount.getBalance(), yAccount.getBalance());
						
			//businessRulesRemote.transfer(50, -1, new BigDecimal("300.00"));
						
		}catch(EJBException e){
			assertEquals("The To or From account does not exist", e.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			fail("Transfer Failed.");
		}
	}
}
