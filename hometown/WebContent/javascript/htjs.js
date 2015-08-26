/*
 * Example: see below for usage. 
 *  
 * If the default behavior must be cancelled
 * use the stopDefault function:
 * Ex. 
 * 	function x(event){ 
 * 		//IE
 * 		if (event == null){
 *			event = window.event;
 *		}
 * 		//Firefox, Mozilla, etc...
 *		stopDefault(event);
 * 	}
 */

function setActive(id)
{	
	if (id == null) { return false; }
	if (!document.getElementById(id)) { return false; }
	
	var items = (document.getElementById(id).parentNode).getElementsByTagName("li");
	for (var i = 0; i < items.length; i++) {
		document.getElementById(items[i].id).style.background = "#84B5E4";	
	}	
	document.getElementById(id).style.background = "#BED8F1";
}

function show(id) {
	document.getElementById(id).style.display = "block";	
}

function hide(id) {
	document.getElementById(id).style.display = "none";
}

function init() {
	//var nav = document.getElementById("navigation").getElementsByTagName("li");
	
	//for (var i = 0; i < nav.length; i++) {
	//	Object.extend(nav[i], navExtend);
	//}
}

function showExpired() {
	loadSubPageContent(new updateContent(), "login.html");
}

function showLogin() {
	loadSubPageContent(new updateContent(), "login.html");
}

function showSignup() {
	loadSubPageContent(new updateContent(),"signup.jsp");
}

function showViewAccounts() {
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=viewAccounts", "POST");
	setActive("viewAccounts");
}

function showBillPay() {
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=loadBillPay", "POST");
	setActive("billPay");
}

function showManagePayees() {
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=loadManagePayees", "POST");
}

function showSchedule() {
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=loadSchedulePayment", "POST");
}

function showTransfer() {
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=loadTransfer", "POST");
	setActive("transfer");
}

function showOrderCheck() {
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=loadChecks", "POST");
	setActive("orderCheck");
}

function showAbout() {
	loadSubPageContent(new updateContent(), "about.html");
}

function showNav() {
	if (document.getElementById("navigation") != null) {
		show("navigation");
		show("logout");
		show("updateUP");
	}
}

function hideNav() {
	hide("logout");
	hide("updateUP");
	if (document.getElementById("navigation") != null) {
		hide("navigation");
	}
}

function showAccountDetails(accountId) {
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=viewDetailedAccount&accountId=" + accountId );
	setActive("viewAccounts");
}

function payBills() {
	var params = getFormElements();
	var poststr = "cmd=payBill&bills=";
	var temp;
	
	for (var i = 0; i < params.length; i++) {
		if (i % 4 == 0 && i != 0) {
			poststr += "``";
		}
		temp = params[i][1];
		temp = temp.replace(/&/g, '');
		poststr += temp + "~~";
	}	
	
	loadSubPageContent(new updateContent(), "HomeTownServlet?", "POST", poststr);	
}

function confirmRemovePayee(payeeId) {
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=confirmRemovePayee&payeeid=" + payeeId);
}

function removePayee(payeeId) {
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=removePayee&payeeid=" + payeeId );
}

function editPayee(payeeId) {
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=loadEditPayee&payeeid=" + payeeId );
}

function removePayment(paymentId) {
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=removePayment&paymentid=" + paymentId );
}

function showUpdateUP() {
	loadSubPageContent(new updateContent(),"HomeTownServlet?" , "POST", "cmd=loadUpdateUser");
}

function logout() {
	loadSubPageContent(new updateContentLogout(), "HomeTownServlet?" , "POST", "cmd=logout");
}

var navExtend = {
	onmouseclick: function(e) { 
		alert(e);
	},
	
	onmouseover: function(e) {		
		document.getElementById(this.id).style.background = "#BED8F1";
	},
	
	onmouseout: function(e) {
		document.getElementById(this.id).style.background = "#84B5E4";
	}
};

Object.extend = function(destination, source) {
	if (destination == null) {
	    return null;
	}
	
	if (source) {
	  for (var property in source) {
	    destination[property] = source[property];
	  }
	}
	
	return destination;
}

function limitText(limitField, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} 
}

//function setcurs(){
//	document.getElementById(unametf).focus();
//}

function getFormElements() {
	var params = new Array();	
	
	var formElements = document.getElementsByTagName("form")[0];
	
	for (var i = 0 ; i < formElements.length ; i++) {
		params.push(new Array(formElements[i].name, formElements[i].value));
	}			
	return params;
}

function postForm(control) {	
	var params = getFormElements();
	var poststr;
	var valid;
	
	if (control == null) control = new updateContent();
	for (var i = 0; i < params.length - 1; i++) {		
		if (i == 0) {
			valid = encodeURI(params[i][1]);
			valid = valid.replace(/&/g, '');
			valid = valid.replace(/~~/g, '&');
			poststr = params[i][0] + "=" + valid;
		} else {	
			var temp = encodeURI(params[i][1]);
			temp = temp.replace(/&/g, '');
			temp = temp.replace(/~~/g, '&');
			poststr += "&" + params[i][0] + "=" + temp;
		}
	}
	
	loadSubPageContent(control, "HomeTownServlet?", "POST", poststr);
}

function clearForm() {
	var formElements = document.getElementsByTagName("form")[0];
	
	for (var i = 0 ; i < formElements.length - 2 ; i++) {
		formElements[i].value = "";
	}
	formElements[0].focus();
}