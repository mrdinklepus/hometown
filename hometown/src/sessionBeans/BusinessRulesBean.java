/**
 * 
 */
package sessionBeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entityBeans.Account;
import entityBeans.AccountType;
import entityBeans.Address;
import entityBeans.BankTransaction;
import entityBeans.CheckOrder;
import entityBeans.Payee;
import entityBeans.Payments;
import entityBeans.Person;
import entityBeans.PayeeAccount;
import entityBeans.Phone;
import entityBeans.PhoneType;
import entityBeans.TransactionType;

/**
 * @author MDWhite
 */
@Stateless
public class BusinessRulesBean implements BusinessRulesRemote {

//	public static final String RemoteJNDIName = BusinessRulesBean.class.getSimpleName() + "/remote"; (the OLD way)
  public static final String RemoteJNDIName = "java:app/hometown/BusinessRulesBean!sessionBeans.BusinessRulesRemote";
  
	@PersistenceContext(name="hometownejb")
	EntityManager em;
	
	/**
	 * Ultimately, we should be creating a separate POJO and populating it with the return data
	 * (rather than using the entity bean itself to pass the data back).  If we did it that way,
	 * we wouldn't have to eagerly fetch the collection data.  In a real world application,
	 * eagerly fetching all the data would be slow and memory heavy.  However, since this project
	 * is just to play around, I'm not going to spend the time to switch it.
	 */

//  LOGIN  ****************************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public Person login(String username, String password) 
	{
		TypedQuery<Person> query = em.createNamedQuery("Person.findByUserPass", Person.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
		
		return query.getSingleResult();			
	}
	
//	SIGNUP  ****************************************************************************************	
	
	/**
	 * Based on the assumption the customer has already created an account at a local branch
	 * which includes full name and phone number
	 */
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public String signup(String firstName, String lastName, String phone, String username, String pw)
	{
		System.out.println("fn " + firstName + " ln " + lastName + " ph " + phone + " un " + username + " pw " + pw);
		
		String message = "";
		Set<Phone> pset = null;
		
		try
		{
			TypedQuery<Person> query = em.createQuery("SELECT DISTINCT p from Person p" +
                                      					" WHERE UPPER(p.firstname) = :firstname " +
                                      					" AND UPPER(p.lastname) = :lastname", Person.class);
			query.setParameter("firstname", firstName.toUpperCase());
			query.setParameter("lastname", lastName.toUpperCase());
			List<Person> personlist = query.getResultList();
			if (personlist.isEmpty())
			{
				System.out.println("no person found");
				message = "The first and last name entered do not exist in our database!";
			}
			
			for (Person p : personlist)
			{
				System.out.println("Personid is " + p.getPersonid());
				if (p.getPhones() != null)
				{
					pset = p.getPhones();
					System.out.println("pset size is " + pset.size());
				}
				
				for (Phone existingPhone : pset)
				{
					System.out.println("personphone is " + existingPhone.getPhoneNumber());
					if (phone.equals(existingPhone.getPhoneNumber()))
					{
						System.out.println("phone found");
						if (p.getUsername().isEmpty())
						{
							System.out.println("updating person");
							p.setUsername(username);
							p.setPassword(pw);
							em.merge(p);
							//TODO change
							message = "suc";
							break;
						}
						else
						{
							System.out.println("Account already exists");
							message = "**An online account already exists for this customer.**";
							break;
						}
					}
					else
					{
						System.out.println("Phone numbers don't match");
						message = "**The name entered does not match the phone number.**";
					}
				}
			}
    }
    catch (Exception e)
    {
      System.out.println("phone not found.");
      e.printStackTrace();
      message = "**The name entered does not exist in our database.**";
    }
    
    if (message.isEmpty())
    {
      message = "**No match was found for the entered phone number.**";
    }
		return message;
	}

//  CREATE PERSON  **********************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public void createPerson(Person person) 
	{		
		em.persist(person);			
	}

//  UPDATE PERSON  ***********************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public void updatePerson(Person person) 
	{
		em.merge(person);
	}

//  GET ACCOUNT  **********************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public Account getAccount(int accountid)
	{		
		TypedQuery<Account> query = em.createQuery("SELECT a from Account a" +
                        				               " WHERE a.accountid = :accountid",
                        				               Account.class);
		query.setParameter("accountid", accountid);
		Account account = query.getSingleResult();
		
		return account;
	}

//  GET PERSON  ************************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public Person getPerson(int personId) 
	{
	  return em.find(Person.class, personId);
	}

//  GET ACCOUNTS  ********************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public Collection<Account> getPersonAccounts(int pid) 
	{
		TypedQuery<Account> query = em.createQuery("SELECT a from Account a" +
									                             " WHERE a.personid = :pid",
									                             Account.class);
		query.setParameter("pid", pid);
		Collection<Account> accounts = query.getResultList();
		return accounts;
	}

//  GET PERSONPAYEES  ****************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public Collection<Payee> getPersonPayees(int pid)
	{
	  Set<Payee> payees = new HashSet<>();
	  Person person = em.find(Person.class, pid);
	  for (PayeeAccount pp : person.getPayeeAccounts())
	  {
	    payees.add(pp.getPayeeAccountKey().getPayeeid());
	  }	
		return payees;
	}

// PAY BILL  ***********************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public void payBill(int fromAccountId, int payeeId, BigDecimal amount, String desc)
	{		
		Account from = em.find(Account.class, fromAccountId);
		
		if (from == null)
		{
			throw new EJBException("The account does not exist");
		}
		
		if (from.getAccountType().equals("R"))
		{
			from.debit(amount);
		}
		else
		{
			from.credit(amount);
		}
		
		Payee payee = em.find(Payee.class, payeeId);
		//Person p = from.getPersonid();
		
		Set<Payee> set = new HashSet<Payee>();
		set.add(payee);
		String desc1 = "Billpay to " + payee.getCompany();
		
		BankTransaction trans = new BankTransaction(from, amount, TransactionType.BILL_PAYMENT, desc1, desc);
		trans.setPayeeCollection(set);
		
		em.merge(from);
		em.persist(trans);		
	}

//  UNDO PAY  **********************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public void undoPay(int fromAccountId, BigDecimal amount) 
	{	
		Account from = em.find(Account.class, fromAccountId);
		from.debit(amount);
//		em.merge(from);
	}

//  ORDER CHECKS  ********************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public void orderChecks(int fromAccountId, BigDecimal amount, String checkId) 
	{
    Account from = em.find(Account.class, fromAccountId);
    if (from == null)
    {
      throw new EJBException("The account does not exist");
    }
    
    if (from.getAccountType().equals(AccountType.CREDIT))
    {
      from.debit(amount);
    }
    else
    {
      from.credit(amount);
    }
    
    CheckOrder order = new CheckOrder();
    order.placeOrder(amount, checkId, from);
    BankTransaction trans = new BankTransaction(from, amount, TransactionType.CHECK_ORDER, "Checks Ordered", null);
    
//    em.merge(from);
    em.persist(order);
    em.persist(trans);
	}

//  TRANSFER FUNDS  *******************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public void transfer(int toId, int fromId, BigDecimal amount, String desc) 
	{
	  Account toAccount = em.find(Account.class, toId);
    Account fromAccount = em.find(Account.class, fromId);
    
    if (toAccount == null || fromAccount == null)
    {
      throw new EJBException("The To or From account does not exist");
    }
    
    // Since a positive balance on a credit account works differently,
    // treat it as a special case.
    if (toAccount.getAccountType().equals(AccountType.CREDIT))
    {
      toAccount.credit(amount);
    }
    else 
    {
      toAccount.debit(amount);
    }
    
    if (fromAccount.getAccountType().equals(AccountType.CREDIT))
    {
      fromAccount.debit(amount);
    }
    else
    {
      fromAccount.credit(amount);
    }
      
    BankTransaction aBankTransaction = new BankTransaction(toAccount, fromAccount, amount,
                                                           TransactionType.TRANSFER, "Transfer", desc);
    
//    em.merge(fromAccount);
//    em.merge(toAccount);  
    em.persist(aBankTransaction);
	}

//  ADD PAYEE  *************************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public void addPayee(int uid, String coname, String street, String city, 
			                 String state, String zip, String phone, String accnum)
	{
	  // Address
		Address add = new Address(street, city, state, zip);
//		em.persist(add);

		// Phone Check
		Set<Phone> phoneCollection = new HashSet<>();
		Phone newPhone = null;
		if (!phone.isEmpty())
		{
		  System.out.println("Creating New Phone...");
		  newPhone = new Phone(phone, PhoneType.WORK);
		  phoneCollection.add(newPhone);
//			em.persist(newPhone);
		}
		
		// Add Payee		
		Payee payee = new Payee();
		payee.addPayee(coname, add, phoneCollection);
		em.persist(payee);

		PayeeAccount pp = new PayeeAccount(accnum, getPerson(uid), payee);
		em.persist(pp);
	}
	
//  UPDATE PAYEE  ***************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public void updatePayee(int payeeid, PhoneType phonetype, int ppid,
	                        String coname, String street, String city, 
			                    String state, String zip, String phone, String accnum)
	{
		Payee existingPayee = em.find(Payee.class, payeeid);
		
		Collection<Phone> existingPhone = existingPayee.getPhones();
		if (phonetype != null)
		{
		  boolean phoneAdded = false;
  		if (existingPhone != null)
  		{
  		  for (Phone p : existingPhone)
  		  {
  		    if (p.getPhonetype() == phonetype || phone.equals(p.getPhoneNumber()))
  		    {
  		      p.setPhoneNumber(phone);
  		      phoneAdded = true;
  		      break;
  		    }
  		  }
  		}
  		
  		if (!phoneAdded)
  		{
  		  // Need to add this as new
  		  Phone newPhone = new Phone(phone, phonetype);
  		  existingPhone.add(newPhone);
  		}
		}
		else
    {     
      if (!phone.isEmpty())
      {
        Phone newPhone = new Phone(phone, PhoneType.WORK);
        existingPhone.add(newPhone);
      }
    }
		
		Address add = existingPayee.getAddress();
		add.setStreet(street);
		add.setCity(city);
		add.setState(state);
		add.setZipcode(zip);
    existingPayee.setCompany(coname);
    
		PayeeAccount pp = em.find(PayeeAccount.class, ppid);
		pp.getPayeeAccountKey().setPayeeAccountNo(accnum);
	}

//  REMOVE PAYEE  ***************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public Person removePayee(int uid, int payeeid)
	{
//		Query query = em.createQuery("SELECT p from Person p " +
//				                         "LEFT JOIN FETCH p.personpayeeCollection " +
//				                         "WHERE p.personid = :userId");
//		query.setParameter("userId", uid);
		Person person = em.find(Person.class,  uid);
		Collection<PayeeAccount> pp = person.getPayeeAccounts();
		
		for (PayeeAccount ppayee : pp) 
		{
			Payee payee = ppayee.getPayeeAccountKey().getPayeeid();
			if (payee.getPayeeid() == payeeid)	
			{
				pp.remove(ppayee);
				em.remove(ppayee);
				
				try
				{
					Query query = em.createQuery("DELETE FROM Payments p" +
												                " WHERE p.payeeid = :payeeId");
					query.setParameter("payeeId", payee);
					query.executeUpdate();
				}catch (NoResultException ex){
					ex.printStackTrace();
				}		
				break;
			}			
		}
		em.merge(person);
		return person;
	}

//  SCHEDULE PAYMENT  ***************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public void schedulePayment(int personid, int fromac, int payeeid, BigDecimal amount, String date)
	{	
		Person person = em.find(Person.class, personid);
		Account from = em.find(Account.class, fromac);
		Payee payee = em.find(Payee.class, payeeid);
		
		Payments payment = new Payments(person, from, payee, amount, date);		
		em.persist(payment);				
	}

//  REMOVE PAYMENT  ****************************************************************************
	
	@Override
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public String removePayment(int uid, int paymentid) 
	{		
		String company = "";
		Person person = em.find(Person.class, uid);
		
		Collection<Payments> personPayments = person.getScheduledPayments();
		for (Payments payment : personPayments)
		{
			if (payment.getPaymentid() == paymentid)
			{
			  personPayments.remove(payment);
//				em.remove(payment);
				company = payment.getPayeeid().getCompany();
				break;
			}
		}
		return company;
	}

//  GET TRANSACTIONS  ********************************************************************************
	
	public List<BankTransaction> getTransactions(Account accountid)
	{	
		TypedQuery<BankTransaction> query = em.createQuery("SELECT b FROM Banktransaction b " +
                                                			 "WHERE b.fromaccountid = :acc OR b.toaccountid = :acc " +
                                                			 "ORDER BY transactionid asc", BankTransaction.class); 
		query.setParameter("acc", accountid);
		List<BankTransaction> list1 = query.getResultList();
		
		return list1;
	}

//  GET PAYEE BY ID  ********************************************************************************
	
	public Payee getpayeebyid(int payeeid)
	{
	  Payee payee = em.find(Payee.class, payeeid);
		return payee;
	}
	
//	UPDATE USER  ********************************************************************************	
	
	public String updateUser(int uid, String userName, String pw)
	{
		String message = "";
		try
		{
			Person person = em.find(Person.class, uid);
			if (userName != null && !userName.isEmpty())
			{
				TypedQuery<Person> query = em.createQuery("SELECT p from Person p " +
						                                      "WHERE p.username = :uname", Person.class);
				query.setParameter("uname", userName);
				Collection<Person> personsWithUserid = query.getResultList();
				if (personsWithUserid.isEmpty())
				{
					person.setUsername(userName);
					person.setPassword(pw);
					message = "suc";
				}
				else
				{
					message = "** Username is already taken.  Please try a different username. **";
				}
			}
			else
			{
				person.setPassword(pw);
				message = "suc";
			}
		} catch (Exception e) {
		  e.printStackTrace();
		}
		return message;
	}
}
