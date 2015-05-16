package viewCO;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VCOInterface {
	
	public void doDisplay(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
