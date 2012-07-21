http://www.sitepoint.com/html5-full-screen-api/#fbid=IA6gTswX7yy

var pfx = ["webkit", "moz", "ms", "o", ""];
function RunPrefixMethod(obj, method, args) {
	var p = 0, m, t;
	while (p < pfx.length && !obj[m]) {
		m = method;
		if (pfx[p] == "") {
			m = m.substr(0,1).toLowerCase() + m.substr(1);
		}
		m = pfx[p] + m;
		t = typeof obj[m];
		if (t != "undefined") {
			pfx = [pfx[p]];
			return (t == "function" ? obj[m]( args ) : obj[m]);
		}
		p++;
	}
}

$(function(){

	var el = document.getElementById("content");  
	el.onkeydown = function( e ) {

		toogleFullscren = function() {

			if( RunPrefixMethod( el, "FullScreen" ) 
			|| RunPrefixMethod( el, "IsFullScreen" ) ) {

				RunPrefixMethod( el, "CancelFullScreen" );
			}
			else {
				RunPrefixMethod( el, "RequestFullScreen", Element.ALLOW_KEYBOARD_INPUT );
			}  
		}

		if( e.keyCode == 122 												// F11 (windows) 
			|| ( e.shiftKey && e.metaKey && e.keyCode == 70 ) ) { // or Command + Shift + F (mac)

			e.preventDefault();

			toogleFullscren();
		}
	}
	
})