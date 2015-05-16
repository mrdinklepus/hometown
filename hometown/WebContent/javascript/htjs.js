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

var isNavVisible = false;

function setActive(id)
{	
	if (id == null){ return false; }
	if (!document.getElementById(id)){ return false; }
	
	var items = (document.getElementById(id).parentNode).getElementsByTagName("li");
	for(var i = 0; i < items.length; i++){
		document.getElementById(items[i].id).style.background = "#84B5E4";	
	}	
	document.getElementById(id).style.background = "#BED8F1";
}

function show(id)
{
	document.getElementById(id).style.display = "block";	
}

function hide(id)
{
	document.getElementById(id).style.display = "none";
}

function init()
{	
	hide("logout");	
	hide("uUP");

	var nav = document.getElementById("navigation").getElementsByTagName("li");
	
	for(var i = 0; i < nav.length; i++){
		Object.extend(nav[i],navExtend);
	}
	//document.login.unameb.focus();
}

function showExpired()
{
	loadSubPageContent(new updateContent(), "login.html");
	//document.getElementById("error").innerHTML = data;	
}

function showLogin()
{
	show("waiting");
	loadSubPageContent(new updateContent(), "login.html");
}

function showSignup()
{
	show("waiting");
	loadSubPageContent(new updateContent(),"signup.jsp");
}

//function showRemovePayee(payid)
//{
//	show("waiting");
//	loadSubPageContent(new updateContent(),"WEB-INF/confirmRemPayee.jsp");
//}

function showViewAccounts()
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=viewAccounts", "POST");
	setActive("viewAccounts");
}

function showBillPay()
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=loadBillPay", "POST");
	setActive("billPay");
}

function showManagePayees()
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=loadManagePayees", "POST");
}

function showSchedule()
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=loadSchedulePayment", "POST");
}

function showTransfer()
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=loadTransfer", "POST");
	setActive("transfer");
	//document.getElementById("foc").focus();
}

function showOrderCheck()
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?cmd=loadChecks", "POST");
	setActive("orderCheck");
}

function showAbout()
{
	show("waiting");
	loadSubPageContent(new updateContent(), "about.html");
}

function showNav()
{
	show("navigation");
	show("logout");
	show("uUP");
}

function showAccountDetails(accountId)
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=viewDetailedAccount&accountId=" + accountId );
	setActive("viewAccounts");
}

function getFormElements(){
	var params = new Array();	
	
	var formElements = document.getElementsByTagName("form")[0];
	
	for (var i = 0 ; i < formElements.length ; i++){
		params.push(new Array(formElements[i].name, formElements[i].value));
	}			
	return params;
}

function postForm(control){	
	var params = getFormElements();
	var poststr;
	var valid;
	
	if (control == null) control = new updateContent();
	for(var i = 0; i < params.length - 1; i++){		
		if (i == 0){
			valid = encodeURI(params[i][1]);
			valid = valid.replace(/&/g, '');
			valid = valid.replace(/~~/g, '&');
			poststr = params[i][0] + "=" + valid;
		}else{	
			var temp = encodeURI(params[i][1]);
			temp = temp.replace(/&/g, '');
			temp = temp.replace(/~~/g, '&');
			poststr += "&" + params[i][0] + "=" + temp;
		}
	}
	show("waiting");
	loadSubPageContent(control, "HomeTownServlet?", "POST", poststr);
}

function clearForm(){
	var formElements = document.getElementsByTagName("form")[0];
	
	for (var i = 0 ; i < formElements.length - 2 ; i++){
		formElements[i].value = "";
	}
	formElements[0].focus();
	hide("waiting");
}

function payBills(){
	var params = getFormElements();
	var poststr = "cmd=payBill&bills=";
	var temp;
	
	for (var i = 0; i < params.length; i++){
		if (i % 4 == 0 && i != 0){
			poststr += "``";
		}
		temp = params[i][1];
		temp = temp.replace(/&/g, '');
		poststr += temp + "~~";
	}	
	
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?", "POST", poststr);	
}

function confirmRemovePayee(payeeId)
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=confirmRemovePayee&payeeid=" + payeeId);
}

function removePayee(payeeId)
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=removePayee&payeeid=" + payeeId );
}

function editPayee(payeeId)
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=loadEditPayee&payeeid=" + payeeId );
}

function removePayment(paymentId)
{
	show("waiting");
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=removePayment&paymentid=" + paymentId );
}

function showUpdateUP()
{
	show("waiting");
	loadSubPageContent(new updateContent(),"HomeTownServlet?" , "POST", "cmd=loadUpdateUser");
}

function logout()
{
	loadSubPageContent(new updateContent(), "HomeTownServlet?" , "POST", "cmd=logout");
	hide("logout");
	hide("uUP");
}

var navExtend = {
	onmouseclick: function(e){ 
		alert(e);
	},
	
	onmouseover: function(e){		
		document.getElementById(this.id).style.background = "#BED8F1";
	},
	
	onmouseout: function(e){
		document.getElementById(this.id).style.background = "#84B5E4";
	}
	
};

Object.extend = function(destination, source) {
	if(destination == null)
	    return null;
	
	if(source)
	for (var property in source)
	        destination[property] = source[property];
	
	return destination;
}

function limitText(limitField, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} 
}

function setcurs(){
	document.getElementById(unameb).focus();
}

//function doClick(buttonName, e)
//{//Was going to use this for pressing enter but didn't need to
//	var key;
//	
//	if(window.event)
//		key = window.event.keyCode;
//	else
//		key = e.which;
//
//	if (key == 13)
//    {
//        //Get the button the user wants to have clicked
//        var btn = document.getElementById(buttonName);
//        if (btn != null)
//        { //If we find the button click it
//            btn.click();
//            event.keyCode = 0
//        }
//    }	
//}