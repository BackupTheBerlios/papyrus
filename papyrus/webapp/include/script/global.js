function getBorwserType() {
	var browserType = "";

	if (document.layers) {browserType = "nn4"}
	if (document.all) {browserType = "ie"}
	if (window.navigator.userAgent.toLowerCase().match("gecko")) {browserType= "gecko"}

	return browserType;
}

function hide(playerName) {
	var browserType = getBorwserType();

  	if (browserType == "gecko" )
    	document.poppedLayer = eval('document.getElementById(\'' + playerName + '\')');
	else if (browserType == "ie")
    		document.poppedLayer = eval('document.all[\'' + playerName + '\']');
  		else
     		document.poppedLayer = eval('document.layers[\'' + playerName + '\']');
     		
  	document.poppedLayer.style.visibility = "hidden";
}

function show(playerName) {
	var browserType = getBorwserType();
	
  	if (browserType == "gecko" )
    	document.poppedLayer = eval('document.getElementById(\'' + playerName + '\')');
	else if (browserType == "ie")
    		document.poppedLayer = eval('document.all[\'' + playerName + '\']');
  		else
     		document.poppedLayer = eval('document.layers[\'' + playerName + '\']');
     		
  	document.poppedLayer.style.visibility = "visible";
}