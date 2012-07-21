$(function(){

	document.getElementById('code-wrap').makeResizable({
		handle: document.getElementById('vertical-resize-handle'),
		modifiers: {'x': null, 'y': 'height'},
		onDrag: function( el ){

			var y = $(el).height();
			var fullHeight = window.innerHeight;

			$('#result-wrap').css('height', fullHeight - y )
			
			window.codeMirror.refresh();
			window.resultMirror.refresh();
		}
	});
})