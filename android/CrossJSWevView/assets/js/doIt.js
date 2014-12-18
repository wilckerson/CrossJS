function JsDoIt()
{
	console.log("JsDoIt!");
}

function exception(){
	throw "hue";
}

function forceError(){
	var a = {};
	a.call();
}
console.log("Executou");