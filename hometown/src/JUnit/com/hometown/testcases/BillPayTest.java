package JUnit.com.hometown.testcases;


import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Test;

import entityBeans.Account;
import entityBeans.Payee;
import entityBeans.Person;
import entityBeans.Personpayee;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;


public class BillPayTest {
	
	@Test
	public void billTest() {
		
		BigDecimal amount = new BigDecimal("25.00");
		int fromAccountId = 2;
		int payeeId = 3;
		
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		
		try {
			
			jndiContext = new InitialContext();
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			Account preAccount = businessRulesRemote.getAccount(fromAccountId);
			
			businessRulesRemote.payBill(fromAccountId, payeeId, amount, "Dec Bill");
						
			Account postAccount = businessRulesRemote.getAccount(fromAccountId);
			postAccount.getBalance().add(amount);
				
			assertEquals(preAccount, postAccount);
						
			
		}catch(EJBException e){
			assertEquals("The account does not exist", e.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			fail("Payment Failed.");
		}finally{
//			try{
//				businessRulesRemote.undoPay(fromAccountId, amount);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
		}
		
	}
	
	//@Test
	public void negNumBillTest() {
		
		BigDecimal amount = new BigDecimal("-24.95");
		int fromAccountId = 2;
		int payeeId = 2;
		
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		
		try {
			
			jndiContext = new InitialContext();
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			businessRulesRemote.payBill(fromAccountId, payeeId, amount, "Jan Bill");		
			
		}catch(EJBException e){
			assertEquals("Amount must be greater than zero", e.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			fail("Payment Failed.");
		}finally{
			try{
				businessRulesRemote.undoPay(fromAccountId, amount);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

	//@Test
	public void insufficientFundsBillTest() {
		
		BigDecimal amount = new BigDecimal("30000.00");
		int fromAccountId = 2;
		int payeeId = 2;
		
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		
		try {
			
			jndiContext = new InitialContext();
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			businessRulesRemote.payBill(fromAccountId, payeeId, amount, "Feb Bill");		
			
		}catch(EJBException e){
			assertEquals("Insufficient funds to complete transaction", e.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			fail("Payment Failed.");
		}finally{
			try{
				businessRulesRemote.undoPay(fromAccountId, amount);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}

