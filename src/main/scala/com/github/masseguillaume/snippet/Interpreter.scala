package com.github.masseguillaume.snippet

import com.twitter.util.Eval
import com.twitter.util.Eval._
import net.liftweb.util.Helpers._
import net.liftweb.http._
import net.liftweb.http.js._
import net.liftweb.http.js.JE._
import net.liftweb.http.js.JsCmds._

import scala.xml.Text

object Interpreter
{
	private val eval = new Eval()
	def evalText( text: String ) = {
		try{
			eval( text ).toString
		}
		catch
		{
			case ex: CompilerException => ex.getMessage 
		}
	}
}

class Interpret
{
	private def defaultCode = ""

	def render = "#eval [onclick]" #> {

		val a = SHtml.ajaxCall(
			MirrorValById(),
			code => {
				SetHtml("result", Text( Interpreter.evalText( code ) ) ) &
				JsRaw( "resultMirror.setValue( document.getElementById( 'result' ).value )" ) &
				JsRaw( "$('#eval').attr('disabled', '')" )
			} 
		)

		( a._1, a._2 & JsRaw("return false") ) // prevent form submit
	}

	def code = "#code *"#> {
		S.param("code") openOr defaultCode
	}
	
	def eval = "#result *"#> {
		if ( S.param("eval").isDefined ) {
			Interpreter.evalText( S.param("code") openOr defaultCode )
		}
		else {
			""
		}
	}
	
	case class MirrorValById() extends JsExp {
		def toJsCmd = 
		"""
			( function() {
				$('#eval').attr('disabled','disabled')
				codeMirror.save();
				var $code = $('#code'); 
				if ( $code.length ) {
					return $code.attr('value');
				} else {
					return null;
				}
			})()
		"""
  }
}