package businessControl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadUpdateUserBCO implements BCOInterface{

	public Object doSomething(HttpServletRequest req, HttpServletResponse resp) 
	{
		req.setAttribute("error", "");		
		return null;
	}
}
