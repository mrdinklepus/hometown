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
import entityBeans.Banktransaction;
import entityBeans.Checkorder;
import entityBeans.Payee;
import entityBeans.Payments;
import entityBeans.Person;
import entityBeans.Personpayee;
import entityBeans.Phone;
import entityBeans.TransactionType;

/**
 * @author MDWhite
 */
@Stateless
public class BusinessRulesBean implements BusinessRulesRemote {

//	public static final String RemoteJNDIName = BusinessRulesBean.class.getSimpleName() + "/remote";
//	public static final String LocalJNDIName = BusinessRulesBean.class.getSimpleName() + "/local";
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
		Query query = em.createQuery("SELECT p FROM Person p" +
									" WHERE p.userid = :userid AND p.password = :password");
		query.setParameter("userid", username);
		query.setParameter("password", password);
		
		return (Person)query.getSingleResult();			
	}
	
//	SIGNUP  ****************************************************************************************	
	
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
			
			Iterator it1 = personlist.iterator();
			while (it1.hasNext())
			{			
				Person p = (Person)it1.next();
				System.out.println("Personid is " + p.getPersonid());
				if (p.getPhoneCollection() != null)
				{
					pset = p.getPhoneCollection();
					System.out.println("pset size is " + pset.size());
				}
				
				Iterator it2 = pset.iterator();
				while (it2.hasNext())
				{
					Phone existingPhone = (Phone)it2.next();
					System.out.println("personphone is " + existingPhone.getPhone());
					if (phone.equals(existingPhone.getPhone()))
					{
						System.out.println("phone found");
						if (p.getUserid().isEmpty())
						{
							System.out.println("updating person");
							p.setUserid(username);
							p.setPassword(pw);
							em.merge(p);
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
    
    if (message.equals(""))
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
		Query query = em.createQuery("SELECT DISTINCT a from Account a " +
				"LEFT JOIN FETCH a.banktransactionCollection " +
				"LEFT JOIN FETCH a.banktransactionCollection2 " +
				"WHERE a.accountid = :accountid");
		query.setParameter("accountid", accountid);
		Account account = (Account)query.getSingleResult();	
		
		return account;
	}

//  GET PERSON  ************************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public Person getPerson(int personId) 
	{		
		Query query = em.createQuery("SELECT DISTINCT p from Person p " +
		"LEFT JOIN FETCH p.personpayeeCollection " +
		"LEFT JOIN FETCH p.accountCollection " +
		"WHERE p.personid = :pid");
		
		query.setParameter("pid", personId);
		Person person = (Person)query.getSingleResult();		
		System.out.println(person.getFullname());
		
		return person;
	}

//  GET PERSON ACCOUNTS  ********************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public Set<Account> getPersonAccounts(int pid) 
	{
		Query query = em.createQuery("SELECT DISTINCT p from Person p " +
									"LEFT JOIN FETCH p.accountCollection " +
									"WHERE p.personid = :pid");
		query.setParameter("pid", pid);
		Person person = (Person)query.getSingleResult();
		
		Set<Account> accounts = person.getAccountCollection();
		return accounts;
	}

//  GET PERSONPAYEE  ****************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public Person getPersonPayee(int pid)
	{
		Query query = em.createQuery("SELECT DISTINCT p from Person p " +
									"LEFT JOIN FETCH p.personpayeeCollection " +
									"LEFT JOIN FETCH p.accountCollection " +
									"LEFT JOIN FETCH p.paymentCollection " +
									"WHERE p.personid = :pid");
		query.setParameter("pid", pid);
		Person person = (Person)query.getSingleResult();			
		return person;
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
		
		if (from.getAccounttype().equals("R"))
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
		
		Banktransaction trans = new Banktransaction(from, amount, TransactionType.BILL_PAY, desc1, desc);
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
		em.merge(from);	
	}

//  ORDER CHECKS  ********************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public void orderChecks(int fromAccountId, BigDecimal amount, String checkId) 
	{	
	  Checkorder checks = new Checkorder();
    
    Account from = em.find(Account.class, fromAccountId);
    if (from == null)
    {
      throw new EJBException("The account does not exist");
    }
    
    if (from.getAccounttype().equals(AccountType.CREDITCARD))
    {
      from.debit(amount);
    }
    else
    {
      from.credit(amount);
    }
    
    checks.placeOrder(amount, checkId, from, from.getPersonid());
    
    Banktransaction trans = new Banktransaction(from, amount, TransactionType.CHECK_ORDER, "Checks Ordered", null);
    
//    em.merge(from);
    em.persist(checks);
    em.persist(trans);
	}

//  CREATE USER  ******************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public Person createUser(String accountNumber, String userid, String pw) 
	{
		Query query = em.createQuery("SELECT DISTINCT a from Account a " +
				"LEFT JOIN FETCH a.personId " +
				"WHERE accountNumber = :accountNum");
		query.setParameter("accountNum", accountNumber);
		
		Account a = (Account)query.getSingleResult();
		if (a == null)
		{
			throw new EJBException("The account does not exist");
		}
		Person person = a.getPersonid();
		person.setUserid(userid);
		person.setPassword(pw);
		
		em.merge(person);
		return person;
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
    if (toAccount.getAccounttype().equals(AccountType.CREDITCARD))
    {
      toAccount.credit(amount);
    }
    else 
    {
      toAccount.debit(amount);
    }
    
    if (fromAccount.getAccounttype().equals(AccountType.CREDITCARD))
    {
      fromAccount.debit(amount);
    }
    else
    {
      fromAccount.credit(amount);
    }
      
    Banktransaction aBankTransaction = new Banktransaction(toAccount, fromAccount, amount,
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
		
//Address Check
		Address add = null;
		System.out.println("This is the Street!" + street);
		try
		{
			Query query2 = em.createQuery("SELECT DISTINCT a from Address a " +
										"WHERE street1 = :street and city = :city" );
			query2.setParameter("street", street);
			query2.setParameter("city", city);
			//query2.setParameter("state", state);
			System.out.println("query");
			add = (Address)query2.getSingleResult();
		}catch (NoResultException ex){
			System.out.println("createNew");
			add = new Address(street, city, state, zip);				
		}
		em.persist(add);			

//Phone Check
		Phone ph = null;
		if (phone.compareTo("") != 0)
		{
			System.out.println("Phone is not blank " + phone);	
			try
			{
				Query query = em.createQuery("SELECT DISTINCT p from Phone p " +
											"WHERE phone = :phoneNum");
				query.setParameter("phoneNum", phone);				
				ph = (Phone)query.getSingleResult();				
			}
			catch (NoResultException ex){
				ph = new Phone();
				ph.createPhone(phone);				
			}			
			em.persist(ph);				
		}
		
//Add Payee		
		Payee payee = new Payee();
		
		if (ph != null)
		{
			payee.addPayee(coname, add, ph);
		}
		else
		{
			payee.addPayeenophone(coname, add);
		}
		em.persist(payee);		
		
		Query query = em.createQuery("SELECT DISTINCT p from Person p " +
				"LEFT JOIN FETCH p.personpayeeCollection " +
				"LEFT JOIN FETCH p.accountCollection " +
				"WHERE p.personid = :pid");
		query.setParameter("pid", uid);
		Person p = (Person)query.getSingleResult();
		
		Personpayee pp = new Personpayee();
		pp.createPP(accnum, p, payee);
		em.persist(pp);			
	}
	
//  UPDATE PAYEE  ***************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public void updatePayee(int payeeid, int addid, int phoneid, int ppid, String coname, String street, String city, 
			String state, String zip, String phone, String accnum)
	{	
		Payee p1 = em.find(Payee.class, payeeid);
		Address add = em.find(Address.class, addid);
		Personpayee pp1 = em.find(Personpayee.class, ppid);
		Phone ph1 = null;
		Phone ph = null;
		int i = 0;
		
		if (phoneid != 0)
		{
			System.out.println("Phone id is " + phoneid);
			ph1 = em.find(Phone.class, phoneid);
			
			if (phone.compareTo("") == 0)
			{		
				try
				{
					System.out.println("running query");
					Query query1 = em.createQuery("SELECT DISTINCT p from Payee p " +
												"WHERE p.phoneid = :phoneId " +
												"AND p.payeeid != :payid");
					query1.setParameter("phoneId", ph1);
					query1.setParameter("payid", payeeid);
					query1.getSingleResult();	
					System.out.println("Another Payee has phone number");
					p1.setPhoneid(ph);
				}catch (NoResultException ex){
					System.out.println("Removing Phone number");
					p1.setPhoneid(ph);
					em.merge(p1);
					em.remove(ph1);
				}				
			}
			else
			{					
				ph1.setPhone(phone);
				em.merge(ph1);
			}
		}
		else
		{			
			if (phone.compareTo("") != 0)
			{
				ph1 = new Phone();
				ph1.createPhone(phone);
				em.persist(ph1);
				i = 1;
			}
		}
		add.setStreet1(street);
		add.setCity(city);
		add.setState(state);
		add.setZipcode(zip);
		em.merge(add);
		
		if (i != 1)
		{
			p1.setCompany(coname);
		}
		else
		{
			p1.setCompany(coname);
			p1.setPhoneid(ph1);
		}
		em.merge(p1);
		    
		pp1.setPayeeaccountno(accnum);
		em.merge(pp1);		
	}

//  REMOVE PAYEE  ***************************************************************************
	
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public Person removePayee(int uid, int payeeid)
	{
		Collection pp;
		boolean test = false;
		Query query = em.createQuery("SELECT DISTINCT p from Person p " +
				"LEFT JOIN FETCH p.personpayeeCollection " +
				"WHERE p.personid = :userId");
		query.setParameter("userId", uid);
		Person person = (Person)query.getSingleResult();			
		
		pp = person.getPersonpayeeCollection();
		for (Iterator iterator = pp.iterator(); iterator.hasNext();) 
		{
			Personpayee ppayee = (Personpayee) iterator.next();
			Payee payee = ppayee.getPayeeid();
			if (payee.getPayeeid() == payeeid)	
			{
				pp.remove(ppayee);
				System.out.println("payee id is " + payee.getPayeeid());
				em.remove(ppayee);
				
				try
				{
					Query query1 = em.createQuery("SELECT DISTINCT p from Payments p " +
												"WHERE p.payeeid = :payeeId");
					query1.setParameter("payeeId", payee);
					query1.getSingleResult();	
					test = true;
					
				}catch (NoResultException ex){
					ex.printStackTrace();
				}
				if (test == false)
				{
					System.out.println("Removing payee from database");
					Phone ph = payee.getPhoneid();
					Address add = payee.getAddressid();
					em.remove(payee);
					if (ph != null)
					{
						em.remove(ph);
					}
					em.remove(add);
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
		
		Payments payment = new Payments();
		payment.schedulePay(person, from, payee, amount, date);
		
		em.persist(payment);				
	}

//  REMOVE PAYMENT  ****************************************************************************
	
	@Override
	public String removePayment(int uid, int paymentid) 
	{		
		String co = "";
		Query query = em.createQuery("SELECT DISTINCT p from Person p " +
				"LEFT JOIN FETCH p.paymentCollection " +
				"WHERE p.personid = :userId");
		query.setParameter("userId", uid);
		Person person = (Person)query.getSingleResult();
		
		for(Iterator i = person.getPaymentCollection().iterator(); i.hasNext();)
		{
			Payments payment = (Payments)i.next();
			
			if (payment.getPaymentid() == paymentid)
			{
				person.getPaymentCollection().remove(payment);
				em.remove(payment);
				co = payment.getPayeeid().getCompany();
				break;
			}
		}
		em.merge(person);
		return co;
	}

//  GET TRANSACTIONS  ********************************************************************************
	
	public List<Banktransaction> getTransactions(Account accountid)
	{	
		Query query = em.createQuery("SELECT DISTINCT b from Banktransaction b " +
				"LEFT JOIN FETCH b.fromaccountid " +
				"LEFT JOIN FETCH b.toaccountid " +
				"WHERE b.fromaccountid = :acc or b.toaccountid = :acc " +
				"ORDER BY transactionid asc"); 
		query.setParameter("acc", accountid);
		List<Banktransaction> list1 = (List)query.getResultList();
		
		return list1;
	}

//  GET PAYEE BY ID  ********************************************************************************
	
	public Payee getpayeebyid(int payeeid)
	{
		Query query = em.createQuery("SELECT DISTINCT p from Payee p " +
				"LEFT JOIN FETCH p.addressid " +
				"LEFT JOIN FETCH p.phoneid " +
				"WHERE p.payeeid = :payeeid");
		query.setParameter("payeeid", payeeid);
		Payee payee = (Payee)query.getSingleResult();
		
		//Payee payee = em.find(Payee.class, payeeid);
		return payee;
	}
	
//	UPDATE USER  ********************************************************************************	
	
	public String updateUser(int uid, String un, String pw1)
	{
		String message = "";
		try
		{
			Person person = em.find(Person.class, uid);
			if (!un.equals(null))
			{
				Query query = em.createQuery("SELECT DISTINCT p from Person p " +
						"WHERE p.userid = :un");
				query.setParameter("un", un);
				ArrayList p = (ArrayList)query.getResultList();
				if (p.isEmpty())
				{
					person.setUserid(un);
					person.setPassword(pw1);
					em.merge(person);
					message = "suc";
				}
				else
				{
					message = "**Username is already used.  Please try a different username.**";
				}
			}
			else
			{
				person.setPassword(pw1);
				em.merge(person);
				message = "suc";
			}
		}catch (Exception e){
			System.out.println("Error");
		}
		return message;
	}
}
