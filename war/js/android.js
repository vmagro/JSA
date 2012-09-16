var ua = navigator.userAgent.toLowerCase();
var isAndroid = ua.indexOf("android") > -1; //&& ua.indexOf("mobile");

if (isAndroid) {
	//all requests coming from the native wrapper app have a ua of socal-jsa-web-app, so we don't need to check for that here
	window.location = 'https://play.google.com/store/apps/details?id=org.socal.jsa.mobile.android';
}
$(document).ready(function(){
	var isAndroidApp = ua.indexOf("socal-jsa-web-app") > -1;
	if(isAndroidApp){
		$("#header").remove();
	}
});
