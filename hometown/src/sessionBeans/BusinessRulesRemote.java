/**
 * 
 */
package sessionBeans;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import entityBeans.Account;
import entityBeans.Banktransaction;
import entityBeans.Payee;
import entityBeans.Person;
import entityBeans.Personpayee;

/**
 * @author Kaleb
 *
 */

@Remote
public interface BusinessRulesRemote {
	public Person login(String username, String password);
	public String signup(String fn, String ln, String ph, String un, String pw);
	public Person getPerson(int userId);
	public void updatePerson(Person person);
	public void createPerson(Person person);
	public Set<Account>  getPersonAccounts(int userId);	
	public void transfer(int to, int from, BigDecimal amount, String desc);
	public Account getAccount(int id);
	public Person getPersonPayee(int userId);
	public void payBill(int fromAccountId, int payeeId, BigDecimal amount, String desc);
	public void undoPay(int fromAccountId, BigDecimal amount);
	public void orderChecks(int fromAccountId, BigDecimal amount, String checkId);
	public Person createUser(String accountNumber, String userid, String pw);
	public void addPayee(int uid, String coname, String street, String city, 
			String state, String zip, String phone, String accnum);
	public Person removePayee(int uid, int payeeid);
	public void schedulePayment(int personid, int from, int payeeid, BigDecimal amount, String date);
	public String removePayment(int personid, int paymentid);
	public List<Banktransaction> getTransactions(Account accountid);
	public Payee getpayeebyid(int payeeid);
	public void updatePayee(int payeeid, int addid, int phoneid, int ppid, String coname, String street, String city, 
			String state, String zip, String phone, String accnum);
	public String updateUser(int uid, String un, String pw1);
}
