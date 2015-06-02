package JUnit.com.hometown.testcases;

import java.util.Iterator;
import java.util.List;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Test;

import entityBeans.Account;
import entityBeans.BankTransaction;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;


public class RemovePayeeTest {
	
	//@Test
	public void RemoveTest() {
		
		int uid = 2;
		int payeeId = 5000;
		
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		
		try {
			
			jndiContext = new InitialContext();
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			businessRulesRemote.removePayee(uid, payeeId);
			
					
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		
	}

	//@Test
	public void listOrder() {
		
		int uid = 2;
		
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		List<BankTransaction> list1 = new ArrayList<BankTransaction>();
		int counter = -1;
		int elem = -1;
		
		try {
			
			jndiContext = new InitialContext();
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			
			Account account = businessRulesRemote.getAccount(uid);
			Set<BankTransaction> trans1 = account.getTransactionsIn();
			Set<BankTransaction> trans2 = account.getTransactionsOut();
			
			System.out.println("before Merge");
			trans1.addAll(trans2);
			System.out.println("AFTER Merge");
			System.out.println(trans1.size());
			
			for(Iterator it = trans1.iterator(); it.hasNext();) {
				
				System.out.println("NEW BT");
				BankTransaction bt = (BankTransaction)it.next();
				
				System.out.println(bt.getTransactionid());
				BankTransaction bt2 = null;
				
				if (counter == -1){
					System.out.println("list1 is null");
					if (bt != null)
						list1.add(bt);
					System.out.println("element added");
					elem++;
					counter++;
				}
				else {
					int i = 0;
					while (i == 0){
						
						bt2 = (BankTransaction)list1.get(counter);
						System.out.println("This is bt2! " + bt2.getTransactionid());
						if ((bt.getTransactionid() < bt2.getTransactionid()) && (counter == 0)){
						
							list1.add(counter, bt);
							System.out.println("ELEMENT ADDED FIRST");
							elem++;
							i++;
						}
						
						else if (bt.getTransactionid() < bt2.getTransactionid()) {
							int c = counter - 1;
							BankTransaction bt3 = (BankTransaction)list1.get(c);
							
							if (bt.getTransactionid() < bt3.getTransactionid()){	
								counter--;
							}
							else {
								list1.add(counter, bt);
								System.out.println("This is bt3! " + bt3.getTransactionid());
								System.out.println("ELEMENT ADDED BEFORE");
								elem++;
								i++;
							}
						}
						
						else if ((bt.getTransactionid() > bt2.getTransactionid()) && (counter == elem)){
							
								list1.add(bt2);
								System.out.println("ELEMENT ADDED LAST");
								elem++;
								i++;
							}
						
						else if (bt.getTransactionid() >= bt2.getTransactionid()){
							int c = counter + 1;
							BankTransaction bt3 = (BankTransaction)list1.get(c);
							if (bt.getTransactionid() >= bt3.getTransactionid()){
								counter++;
							}
							else {
								list1.add(c, bt);
								System.out.println("This is bt3! " + bt3.getTransactionid());
								System.out.println("ELEMENT ADDED AFTER");
								elem++;
								i++;
							}
						}	
					}
				}
			}
			System.out.println("OUT OF FIRST LOOP");
			
			Iterator it = list1.iterator(); 
			for(int c = 0; c < 15; c++) {
				 
				BankTransaction bt = (BankTransaction)it.next();
				System.out.println(bt.getTransactionid());
				
				
			}
			
			
			
			
					
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		
	}
	
	@Test
	public void testlist() {
	
		int uid = 2;
		
		
		Context jndiContext;
		BusinessRulesRemote businessRulesRemote = null;
		
		try {
			
			jndiContext = new InitialContext();
			businessRulesRemote = (BusinessRulesRemote)jndiContext.lookup(BusinessRulesBean.RemoteJNDIName);
			Account a = businessRulesRemote.getAccount(2);
			
			List<BankTransaction> list = businessRulesRemote.getTransactions(a);
			
			for (Iterator it = list.iterator(); it.hasNext();){
				BankTransaction bt = (BankTransaction)it.next();
				System.out.println(bt.getTransactionid());
			}
			
					
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		
		
		
	}
	
}
