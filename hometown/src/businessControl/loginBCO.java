package businessControl;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entityBeans.Person;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;

/**
 * @author Kaleb
 *
 */
public class loginBCO implements BCOInterface
{
  public Object doSomething(HttpServletRequest req, HttpServletResponse resp)
  {
    String username = req.getParameter("uname");
    String password = req.getParameter("pass");
    
    Person person = null;
    
    try
    {
      BusinessRulesRemote businessRulesRemote = (BusinessRulesRemote)InitialContext
          .doLookup(BusinessRulesBean.RemoteJNDIName);
      person = businessRulesRemote.login(username, password);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return person;
  }
}
