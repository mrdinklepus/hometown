package businessControl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BCOInterface 
{
	public Object doSomething(HttpServletRequest req, HttpServletResponse resp);
}
