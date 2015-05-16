function toggle(id)
{	
	var obj = document.getElementById(id);
	var totalSpanId = id + "total";
	
	if (obj.style.display == "none"){
		(document.getElementById(id).parentNode).getElementsByTagName("div")[0].style.background = "url(images/collapse.gif) top right no-repeat";
		document.getElementById(totalSpanId).innerHTML = "Total: $" + findAccountTotal(id);
		obj.style.display = "block";
		
	}else{
		(document.getElementById(id).parentNode).getElementsByTagName("div")[0].style.background = "url(images/expand.gif) top right no-repeat";
		document.getElementById(totalSpanId).innerHTML = "Total: $" + findAccountTotal(id);
		obj.style.display = "none";		
		
	}
}

function findAccountTotal(id)
{	
	var accountTotal = 0;
	
	var main = document.getElementById(id).getElementsByTagName("div");
	
	for(var i = 0; i < main.length; i++){
		var temp = main[i].getElementsByTagName("li");
			var val = parseFloat(((temp[1].innerHTML).replace("$","")).replace(",",""));						
			accountTotal += val;	
	}
	
	return accountTotal;	
}