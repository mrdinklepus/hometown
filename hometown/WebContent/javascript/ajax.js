function loadSubPageContent(control, url, type, params)
{	
	errorControl = new updateError();	
	var request = null;
	
	if (type == null) type = "GET";

	//For IE
	if (!window.XMLHttpRequest)
	{
		window.XMLHttpRequest = function (){
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	
	try { request = new XMLHttpRequest(); } catch(e) { return; 	}
	
	request.onreadystatechange = function(){		
		if (request.readyState == 4 && request.status == 200)
		{
			control.update(request.responseText);
			showNav();	
		}
		else if(request.status == 985)
		{ 
			if (request.statusText != "Session Is Expired!")
			{
				errorControl.update(request.statusText);
				clearForm();
			}
			else
			{
				expired();
			}
		}
		else if(request.readyState == 400)
		{
			control.update("There was a server error");
		}
	}
	
	request.open( type , url, true );
	
	if( type== "POST" && params != null )
	{
		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");		
	}
	request.send( params );					
}

function updateContent()
{
	this.update = function(data){
		document.getElementById("content").innerHTML = data;			
	}
}

function updateError()
{	
	this.update = function(data){		
		document.getElementById("error").innerHTML = data;
	}
	hide("error1");
}

function expired()
{	
	showLogin();
	hide("logout");
	hide("uUP");
	show("error1");
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