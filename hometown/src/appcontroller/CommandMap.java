package appcontroller;

import java.util.HashMap;

public class CommandMap
{
  private static HashMap<String, String> bcoMap = new HashMap<String, String>();
  private static HashMap<String, String> viewMap = new HashMap<String, String>();
	
	public CommandMap()
	{
	  // Populate business controls
		bcoMap.put("login", "businessControl.loginBCO");
		bcoMap.put("logout", "logout");
		bcoMap.put("updateUP", "businessControl.UpdateUPBCO");
		
		bcoMap.put("signup", "businessControl.SignupBCO");
		bcoMap.put("viewAccounts", "businessControl.LoadPersonBCO");
		bcoMap.put("viewDetailedAccount", "businessControl.ViewDetailedAccountBCO");
		
		bcoMap.put("loadBillPay", "businessControl.LoadPersonBCO");
		bcoMap.put("loadManagePayees", "businessControl.LoadPersonBCO");
		bcoMap.put("loadEditPayee", "businessControl.LoadEditPayeeBCO");
		bcoMap.put("loadSchedulePayment", "businessControl.LoadPersonBCO");
		bcoMap.put("loadTransfer", "businessControl.LoadPersonBCO");
		bcoMap.put("loadChecks", "businessControl.LoadPersonBCO");
		bcoMap.put("loadUpdateUser", "businessControl.LoadPersonBCO");
		
		bcoMap.put("payBill", "businessControl.PayBillBCO");
		bcoMap.put("schedulePayment", "businessControl.SchedulePaymentBCO");
		bcoMap.put("removePayment", "businessControl.RemovePaymentBCO");
		
		bcoMap.put("addPayee", "businessControl.AddPayeeBCO");
		bcoMap.put("editPayee", "businessControl.EditPayeeBCO");
		bcoMap.put("removePayee", "businessControl.RemovePayeeBCO");
		bcoMap.put("confirmRemovePayee", "businessControl.ConfirmRemovePayeeBCO");
		
		bcoMap.put("transfer", "businessControl.TransferBCO");
		bcoMap.put("confirmTransfer", "businessControl.ConfirmTransferBCO");
		
		bcoMap.put("orderChecks", "businessControl.OrderChecksBCO");
		
		// Populate View controls
		viewMap.put("login", "viewCO.ViewAccountsVCO");
		viewMap.put("logout", "viewCO.LoadDefaultVCO");
    viewMap.put("expiredSession", "viewCO.ExpiredSession");
    viewMap.put("signup", "viewCO.SignupVCO");
    viewMap.put("updateUP", "viewCO.UpdateUPVCO");
    
    viewMap.put("viewAccounts", "viewCO.ViewAccountsVCO");
    viewMap.put("viewDetailedAccount", "viewCO.ViewDetailedAccountVCO");
    
    viewMap.put("loadBillPay", "viewCO.LoadBillPayVCO");
    viewMap.put("loadManagePayees", "viewCO.LoadManagePayeesVCO");
    viewMap.put("loadEditPayee", "viewCO.LoadEditPayeeVCO");
    viewMap.put("loadTransfer", "viewCO.LoadTransferVCO");
    viewMap.put("loadUpdateUser", "viewCO.UpdateUPVCO");
    viewMap.put("loadChecks", "viewCO.LoadChecksVCO");
    
    viewMap.put("payBill", "viewCO.LoadBillPayVCO");
    viewMap.put("loadSchedulePayment", "viewCO.LoadSchedulePaymentVCO");
    viewMap.put("schedulePayment", "viewCO.LoadSchedulePaymentVCO");
    viewMap.put("removePayment", "viewCO.LoadSchedulePaymentVCO");
    
    viewMap.put("addPayee", "viewCO.LoadManagePayeesVCO");
    viewMap.put("editPayee", "viewCO.EditPayeeVCO");
    viewMap.put("removePayee", "viewCO.LoadManagePayeesVCO");
    viewMap.put("confirmRemovePayee", "viewCO.LoadManagePayeesVCO");
    
    viewMap.put("transfer", "viewCO.TransferVCO");
    viewMap.put("confirmTransfer", "viewCO.TransferVCO");
    viewMap.put("orderChecks", "viewCO.LoadChecksVCO");
    
    viewMap.put("generalerror", "viewCO.LoadErrorVCO");
	}
		
  public String getCommand(String keyval)
  {
		return bcoMap.get(keyval);	
	}
	
	public String getViewCommand(String keyval)
	{
		return viewMap.get(keyval);
	}	
}
