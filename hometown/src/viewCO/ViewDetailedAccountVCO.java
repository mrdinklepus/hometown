package viewCO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionBeans.BusinessRulesBean;
import sessionBeans.BusinessRulesRemote;

import appcontroller.TinySession;
import entityBeans.Account;
import entityBeans.Banktransaction;
import entityBeans.Person;

public class ViewDetailedAccountVCO implements VCOInterface {

	public void doDisplay(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{	
		List<Banktransaction> list1 = new ArrayList<Banktransaction>();
		int counter = -1;
		int elem = -1;
		
		try 
		{	
			//if we throw an error, how do we catch it and use it?
			Account account = (Account)req.getAttribute("reqObject");
			Set<Banktransaction> trans1 = account.getBanktransactionCollection();
			Set<Banktransaction> trans2 = account.getBanktransactionCollection2();
			
			System.out.println("before Merge");
			trans1.addAll(trans2);
			System.out.println("AFTER Merge");
			System.out.println(trans1.size());
			
			for(Iterator it = trans1.iterator(); it.hasNext();) 
			{	
				Banktransaction bt = (Banktransaction)it.next();
				Banktransaction bt2 = null;
				
				if (counter == -1)
				{
					if (bt != null)
					{
						list1.add(bt);
					}
					elem++;
					counter++;
				}
				else 
				{
					int i = 0;
					while (i == 0)
					{	
						bt2 = (Banktransaction)list1.get(counter);
						if ((bt.getTransactionid() < bt2.getTransactionid()) && (counter == 0))
						{
							list1.add(counter, bt);
							elem++;
							i++;
						}						
						else if (bt.getTransactionid() < bt2.getTransactionid()) 
						{
							int c = counter - 1;
							Banktransaction bt3 = (Banktransaction)list1.get(c);
							
							if (bt.getTransactionid() < bt3.getTransactionid())
							{	
								counter--;
							}
							else 
							{
								list1.add(counter, bt);
								elem++;
								i++;
							}
						}						
						else if ((bt.getTransactionid() > bt2.getTransactionid()) && (counter == elem))
						{	
							list1.add(bt);
							elem++;
							i++;
						}
						else if (bt.getTransactionid() >= bt2.getTransactionid())
						{
							int c = counter + 1;
							Banktransaction bt3 = (Banktransaction)list1.get(c);
							
							if (bt.getTransactionid() >= bt3.getTransactionid())
							{
								counter++;
							}
							else 
							{
								list1.add(c, bt);
								elem++;
								i++;
							}
						}	
					}
				}
			}
			for(Iterator it = list1.iterator(); it.hasNext();) 
			{	 
				Banktransaction bt = (Banktransaction)it.next();	
			}
			System.out.println("List contains " + list1.size() + " elements");
			
		}catch(Exception ex){
			ex.printStackTrace();			
		}		
		req.setAttribute("list", list1);
				
		try
		{
			req.getRequestDispatcher("WEB-INF/accountdetails.jsp").forward(req, resp);
		
		}catch (Exception e){
			e.printStackTrace();
		}
	}		
}