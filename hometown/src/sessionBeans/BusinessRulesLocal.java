/**
 * 
 */
package sessionBeans;

import javax.ejb.Local;
import entityBeans.Person;

/**
 * @author Kaleb
 *
 */
@Local
public interface BusinessRulesLocal {
	public Person login(String username, String password);
	public Person getPerson(int userId);
	public void updatePerson(Person person);
	public void createPerson(Person person);
}
