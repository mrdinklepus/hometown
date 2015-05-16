/**
 * 
 */
package viewCO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kaleb
 *
 */
public class ExpiredSession implements VCOInterface {

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{	
		resp.sendError(985, "Session Is Expired!");
		
//		StringBuffer sb = new StringBuffer();
//		
//		PrintWriter out = resp.getWriter();
//		
//		sb.append("<div class=\"expired\">");
//		sb.append("<div style=\"margin:auto auto; position:relative; width:95% padding-top:10px;\">");
//		sb.append("<div>The Session has Expired!</div>");
//		sb.append("<div>Please login to access content</div>");
//		sb.append("<form name=\"login\">");
//		sb.append("username: <input type=\"text\" name=\"uname\" width=\"30px\"/> <br/>");
//		sb.append("password: <input type=\"password\" name=\"pass\" width=\"30px\"/> <br/>");
//		sb.append("<input type=\"hidden\" name=\"cmd\" value=\"login\"/>");
//		sb.append("<input type=\"button\" class=\"right\" value=\"Login\" onclick=\"postForm()\"/> <br/><br/>");
//		sb.append("</form>");
//		sb.append("</div>");
//		
//		sb.append("<div id=\"error\"></div>");
//		
//		sb.append("</div>");
//		
//		out.print(sb.toString());		
	}
}
