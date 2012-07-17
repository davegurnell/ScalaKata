package com.github.masseguillaume.snippet

import com.twitter.util.Eval
import com.twitter.util.Eval._

import net.liftweb._
import common._
import sitemap._
import sitemap.Loc._
import util.Helpers._
import http._
import js._
import JE._
import JsCmds._

import scala.xml.Text

case class KataRessource( id: String )

object Kata
{
	val menu = Menu.param[KataRessource](
		"Kata", "Kata", 
      id => Full( KataRessource(id) ), 
      kata => kata.id ) / * >> 
		Template( () =>
			Templates( "kata" :: Nil ) openOr <b>template not found</b>
	)
	
//	def findKata( id: String ): Box[Kata] = { }

	lazy val loc = menu.toLoc
}

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

class Interpret( kata: KataRessource )
{
	private def defaultCode = kata.id

	def render = {
		
		"#eval [onclick]" #> {
			val res = SHtml.ajaxCall(
				MirrorValById(),
				code => {
					SetHtml("result", Text( Interpreter.evalText( code ) ) ) &
					JsRaw( "resultMirror.setValue( document.getElementById( 'result' ).value )" ) &
					JsRaw( "$('#eval').attr('disabled', '')" )
				}
			)
			
			( res._1, res._2 & JsRaw("return false") )
		} &
		"#save [onclick]" #> {
			val res = SHtml.ajaxInvoke( () => {
			    val id = 10101010;
			    JsRaw("window.history.pushState( null, null,'/" + id + "')")
			})

			( res._1, res._2 & JsRaw("return false") )
		}
	}
	def code = "#code *" #> {
		S.param("code") openOr defaultCode
	}
	
	def eval = "#result *" #> {
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