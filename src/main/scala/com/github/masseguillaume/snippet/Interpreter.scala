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

	def render = "type=submit [onclick]" #> {

		val a = SHtml.ajaxCall(
			MirrorValById("kata-code"),
			code => {
				SetHtml("kata-result", Text( Interpreter.evalText( code ) ) ) &
				JsRaw( "kataResult.setValue(document.getElementById('kata-result').value)" )
			} 
		)

		( a._1, a._2 & JsRaw("return false") )
	}

	def code = "#kata-code *"#> {
		S.param("code") openOr defaultCode
	}
	
	def eval = "#kata-result *"#> {
		if ( S.param("eval").isDefined ) {
			Interpreter.evalText( S.param("code") openOr defaultCode )
		}
		else {
			""
		}
	}
	
	case class MirrorValById(id: String) extends JsExp {
		def toJsCmd = 
		"""
			( function() {
				kataCode.save(); 
				if ( document.getElementById(""" + id.encJs + """) ) {
					return document.getElementById(""" + id.encJs + """).value;
				} else {
					return null;
				}
			})()
		"""
  }
}