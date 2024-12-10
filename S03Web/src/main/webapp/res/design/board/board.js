
function selectOption(n,v){
	if(n.type == "radio"){
		for (i=0;i<n.length;i++)
		 if(n[i].value==v)
			   n[i].checked = true;
	}
	else if(n.type == "select-one"){
		for (i=0;i<n.options.length;i++)
		 if(n.options[i].value==v)
			   n.options[i].selected = true;
	}
}
