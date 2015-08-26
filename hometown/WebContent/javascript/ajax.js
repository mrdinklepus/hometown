function loadSubPageContent(control, url, type, params)
{	
	errorControl = new updateError();	
	var request = null;
	
	if (type == null) type = "GET";

	// For IE
	if (!window.XMLHttpRequest)
	{
		window.XMLHttpRequest = function (){
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	
	try { request = new XMLHttpRequest(); } catch(e) { return; }
	
	show("waiting");
	
	request.onreadystatechange = function() {		
		if (request.readyState == 4 && request.status == 200) {
			// Turn off the waiting display
			hide("waiting");
			control.update(request.responseText);
			
			/* For the browser back button, store the state (or for this - the entire html chunk).
			 * Obviously there are a lot of problems with this, not to mention security vulnerabilities,
			 * but for our intents and purposes here, just store the html so we can go back and forth.
			 * Ideally, we would just save the url, and all the inputs that we had to get there, then just refresh
			 * the page.  But again - I'm not taking the time to cover every aspect of this website */
			if (request.responseText.search("error") == -1) {
				history.pushState(request.responseText, null, null);
			}
		} else if (request.status == 985) {
			// Turn off the waiting display
			hide("waiting");
			
			if (request.statusText != "Session Is Expired!") {
				errorControl.update(request.statusText);
				//clearForm();
			} else {
				//clearForm();
				expired();
				errorControl.update("**Your session is expired.  Please Login again.**");
			}
		} else if (request.readyState == 400) {
			// Turn off the waiting display
			hide("waiting");
			control.update("There was a server error");
		}
	}
	
	request.open( type , url, true );
	
	if ( type == "POST" && params != null ) {
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");		
	}
	
	request.send( params );
}

function updateContent() {
	this.update = function(data) {
		document.getElementById("content").innerHTML = data;
		showNav();
	}
}

function updateContentLogout() {
	this.update = function(data) {
		document.getElementById("content").innerHTML = data;
		hideNav();
	}
}

function updateContentSignup() {
	this.update = function(data) {
		document.getElementById("content").innerHTML = data;
		hideNav();
	}
}

function updateError()
{	
	this.update = function(data) {		
		document.getElementById("error").innerHTML = data;
	}
}

function expired()
{	
	showLogin();
	hideNav();
}

window.onpopstate = function(event) {
	if (window.history.state == null) { // This means it's page load (for Chrome?)
        return;
    }
	document.getElementById("content").innerHTML = event.state;
	showNav();
}

/*Stops the default behavior of an event*/
function stopDefault(event)
{
	if (event)
	{
		if(event.preventDefault)
		{
			event.preventDefault();
		}
		event.returnValue = false;
	}
}