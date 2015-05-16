package appcontroller;

import java.util.HashMap;

public class CommandMap {
	
	public static String getCommand(String keyval) {

	HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", "businessControl.loginBCO");
		map.put("signup", "businessControl.SignupBCO");
		//map.put("createUser", "businessControl.CreateUserBCO");
		map.put("viewAccounts", "businessControl.ViewAccountsBCO");
		map.put("viewDetailedAccount", "businessControl.ViewDetailedAccountBCO");
		map.put("loadBillPay", "businessControl.LoadBillPayBCO");
		map.put("payBill", "businessControl.PayBillBCO");
		map.put("loadSchedulePayment", "businessControl.LoadSchedulePaymentBCO");
		map.put("schedulePayment", "businessControl.SchedulePaymentBCO");
		map.put("loadManagePayees", "businessControl.LoadManagePayeesBCO");
		map.put("addPayee", "businessControl.AddPayeeBCO");
		map.put("loadEditPayee", "businessControl.LoadEditPayeeBCO");
		map.put("editPayee", "businessControl.EditPayeeBCO");
		map.put("removePayee", "businessControl.RemovePayeeBCO");
		map.put("confirmRemovePayee", "businessControl.ConfirmRemovePayeeBCO");
		map.put("loadTransfer", "businessControl.LoadTransferBCO");
		map.put("transfer", "businessControl.TransferBCO");
		map.put("confirmTransfer", "businessControl.ConfirmTransferBCO");
		map.put("loadChecks", "businessControl.LoadChecksBCO");
		map.put("orderChecks", "businessControl.OrderChecksBCO");
		map.put("logout", "businessControl.LogoutBCO");
		map.put("removePayment", "businessControl.RemovePaymentBCO");
		map.put("updateUP", "businessControl.UpdateUPBCO");
		map.put("loadUpdateUser", "businessControl.LoadUpdateUserBCO");
		String mappedval = map.get(keyval);
		
		return mappedval;	
	}
	
	public static String getViewCommand(String keyval) {

	HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", "viewCO.ViewAccountsVCO");
		map.put("signup", "viewCO.SignupVCO");
		//map.put("createUser", "viewCO.CreateUserVCO");
		map.put("viewAccounts", "viewCO.ViewAccountsVCO");
		map.put("viewDetailedAccount", "viewCO.ViewDetailedAccountVCO");
		map.put("loadBillPay", "viewCO.LoadBillPayVCO");
		map.put("payBill", "viewCO.LoadBillPayVCO");
		map.put("loadSchedulePayment", "viewCO.LoadSchedulePaymentVCO");
		map.put("schedulePayment", "viewCO.LoadSchedulePaymentVCO");
		map.put("loadManagePayees", "viewCO.LoadManagePayeesVCO");
		map.put("addPayee", "viewCO.LoadManagePayeesVCO");
		map.put("loadEditPayee", "viewCO.LoadEditPayeeVCO");
		map.put("editPayee", "viewCO.EditPayeeVCO");
		map.put("removePayee", "viewCO.LoadManagePayeesVCO");
		map.put("confirmRemovePayee", "viewCO.LoadManagePayeesVCO");
		map.put("loadTransfer", "viewCO.LoadTransferVCO");
		map.put("transfer", "viewCO.TransferVCO");
		map.put("confirmTransfer", "viewCO.TransferVCO");
		map.put("loadChecks", "viewCO.LoadChecksVCO");
		map.put("orderChecks", "viewCO.LoadChecksVCO");
		map.put("logout", "viewCO.LoadDefaultVCO");
		map.put("expiredSession", "viewCO.ExpiredSession");
		map.put("removePayment", "viewCO.LoadSchedulePaymentVCO");
		map.put("updateUP", "viewCO.UpdateUPVCO");
		map.put("loadUpdateUser", "viewCO.UpdateUPVCO");
		map.put("error", "viewCO.LoadErrorVCO");
		
		String mappedval = map.get(keyval);
		return mappedval;
	}	
}
