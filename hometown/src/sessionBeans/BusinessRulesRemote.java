/**
 * 
 */
package sessionBeans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import entityBeans.Account;
import entityBeans.BankTransaction;
import entityBeans.Payee;
import entityBeans.Person;
import entityBeans.PayeeAccount;
import entityBeans.PhoneType;

/**
 * @author Kaleb
 *
 */

@Remote
public interface BusinessRulesRemote {
	public Person login(String username, String password);
	public String signup(String firstname, String lastname, String phone, String username, String pw);
	public Person getPerson(int userId);
	public void updatePerson(Person person);
	public void createPerson(Person person);
	public Collection<Account> getPersonAccounts(int userId);	
	public void transfer(int to, int from, BigDecimal amount, String desc);
	public Account getAccount(int id);
	public Collection<Payee> getPersonPayees(int userId);
	public void payBill(int fromAccountId, int payeeId, BigDecimal amount, String desc);
	public void undoPay(int fromAccountId, BigDecimal amount);
	public void orderChecks(int personId, int fromAccountId, BigDecimal amount, String checkId);
	public void addPayee(int uid, String companyname, String street, String city, 
			                 String state, String zip, String phone, String accnum);
	public Person removePayee(int uid, int payeeid);
	public Person schedulePayment(int personid, int from, int payeeid, BigDecimal amount, Date date);
	public String removePayment(int personid, int paymentid);
	public List<BankTransaction> getTransactions(Account accountid);
	public Payee getpayeebyid(int payeeid);
	public Payee updatePayee(int payeeid, int personid, PhoneType phonetype, String origaccnum,
	                        String companyname, String street, String city, 
	                        String state, String zip, String phone, String accnum);
	public String updateUser(int uid, String username, String pw);
}
