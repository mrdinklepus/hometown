/**
 * 
 */
package JUnit.com.hometown.testcases;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;




import org.junit.Test;

import entityBeans.Account;
import entityBeans.AccountType;
import entityBeans.Person;
import entityBeans.PayeeAccount;
import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;

/**
 * @author Kaleb
 *
 */
public class BusinessRulesTest {
	/**
	 * Test method for {@link sessionBeans.BusinessRulesBean#login(java.lang.String, java.lang.String)}.
	 */
	//@Test
	public void testLogin() {
		Person expectedPerson = new Person();
		//expectedPerson.setPersonid("1");
		expectedPerson.setLastname("Flintstone");
		expectedPerson.setFirstname("Fred");
		expectedPerson.setMiddlename("F");
		expectedPerson.setUsername("flintstonef");
		expectedPerson.setPassword("pebbles");
		
		Context jndiContext;
		try{			
			jndiContext = new InitialContext();
		
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			Person actualPerson = businessRulesRemote.login("flintstonef", "pebbles");
			System.out.println(actualPerson.getFullname());
			assertEquals(actualPerson, expectedPerson);
			
		}catch(Exception e){
			fail("login failed: exception = " + e.toString());
		}		
	}
	
	//@Test
	public void testGetPerson(){
		Person expectedPerson = new Person();
		//expectedPerson.setPersonid("1");
		expectedPerson.setLastname("Flintstone");
		expectedPerson.setFirstname("Fred");
		expectedPerson.setMiddlename("F");
		expectedPerson.setUsername("flintstonef");
		expectedPerson.setPassword("pebbles");
		
		Context jndiContext;
		try{
			jndiContext = new InitialContext();
			
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			//Person person = businessRulesRemote.getPerson("flintstonef");
//			System.out.println(person.getFullname());								
//			assertEquals(person, expectedPerson);
			
		}catch(Exception e){
			fail("getPerson failed: exception = " + e.toString());
		}
	}
	
	//@Test
	public void testCreatePerson(){
		Person aPerson = new Person();
		//aPerson.setPersonid("8");
		aPerson.setFirstname("Kaleb");
		aPerson.setMiddlename("Keith");
		aPerson.setLastname("Scholes");
		aPerson.setUsername("scholesk");
		
		
		Context jndiContext;
		try{
			aPerson.setPassword("kaleb");
			jndiContext = new InitialContext();
			
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			businessRulesRemote.createPerson(aPerson);		
			
//			Person person = businessRulesRemote.getPerson("scholesk");
//			assertEquals(aPerson,person);
//			
		}catch(Exception e){
			fail("createPerson failed: exception = " + e.toString());
		}
		
	}	
	
	//@Test
	public void testUpdatePerson(){
		Context jndiContext;
		try{
			jndiContext = new InitialContext();
			
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
//			Person person = businessRulesRemote.getPerson("scholesk");	
//			person.setPassword("hellothereisasdf");			
//			businessRulesRemote.updatePerson(person);
//			assertEquals(person.getPassword(), "hello");	
			
		}catch(Exception e){
			e.printStackTrace();
			assertEquals("Person password is Invalid",e.getMessage());
		}
	}
	
	//@Test
//	public void testGetPersonPayees(){
//		HashMap<String, PersonPayeeAccount> map = new HashMap<String, PersonPayeeAccount>();
//		
//		PersonPayeeAccount aPersonPayee = new PersonPayeeAccount();
//			aPersonPayee.setPersonpayeeid(4);
//			aPersonPayee.setPayeeaccountno("83715712");
//		
//		PersonPayeeAccount bPersonPayee = new PersonPayeeAccount();
//			bPersonPayee.setPersonpayeeid(2);
//			bPersonPayee.setPayeeaccountno("793135");	
//		
//		PersonPayeeAccount cPersonPayee = new PersonPayeeAccount();
//			cPersonPayee.setPersonpayeeid(3);
//			cPersonPayee.setPayeeaccountno("775313");	
//		
//			
//		map.put("4", aPersonPayee);
//		map.put("2", bPersonPayee);
//		map.put("3", cPersonPayee);
//			
//		Context jndiContext;
//		
//		
//		try{
//			jndiContext = new InitialContext();
//			
//			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
//			//Set<Personpayee> payees = businessRulesRemote.getPersonPayee("flintstonef");
//			
//			//assertEquals(payees.size(), 3);
//				
//			//Iterator<Personpayee> it = payees.iterator();
//			
////			while(it.hasNext()){
////				Personpayee pp = (Personpayee)it.next();
////				Personpayee local = map.get(pp.getPersonpayeeid());
////				assertEquals(local, pp);
////			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void testMakeTransfer()
	{
		Person aPerson = new Person();
		aPerson.setPersonid(1);
		Set<Person> persons = new HashSet<>();
		persons.add(aPerson);
		Account aAccount = new Account(AccountType.CHECKING, new BigDecimal("6000.00"), "1001", persons);
		Account bAccount = new Account(AccountType.CREDIT, new BigDecimal("4000.00"), "50101", persons);
		
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		
		try
		{
			jndiContext = new InitialContext();
			
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
		
			businessRulesRemote.transfer(1, 2, new BigDecimal("1000.00"), "Transfer to Jill");	
			
			Account xAccount = businessRulesRemote.getAccount(1);
			Account yAccount = businessRulesRemote.getAccount(2);
			
			assertEquals(aAccount.getBalance(), xAccount.getBalance());
			assertEquals(bAccount.getBalance(), yAccount.getBalance());
						
			businessRulesRemote.transfer(50, -1, new BigDecimal("300.00"), "Transfer to Jen");
						
		}catch(EJBException e){
			assertEquals("The To or From account does not exist", e.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			fail("Transfer Failed.");
		}finally{
			try{
				businessRulesRemote.transfer(2,1, new BigDecimal("1000.00"), "Transfer to Jeff");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testMakeTransferInsufficientFunds(){
		Context jndiContext;
		
		try{
			jndiContext = new InitialContext();
			
			BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			businessRulesRemote.transfer(14, 15, new BigDecimal("6000.00"), "Transfer to John");	
		}catch(Exception e){
			assertEquals("Insufficient funds to complete transaction", e.getMessage());
		}
	}
			
}
