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

import com.github.masseguillaume.model.Kata
import com.foursquare.rogue.Rogue._
import org.bson.types.ObjectId

import scala.xml.Text

case class KataRessource( id: String )

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
	
	val menu = Menu.param[Box[Kata]](
		"Kata", "Kata", 
      findKata, 
      encodeKata _ ) / * >> 
		Template( () => Templates( "kata" :: Nil ) openOr <b>template not found</b>
	)

	def findKata( id: String ): Box[Box[Kata]] = {
		id match {
			case "index" => Full( Empty )
			case _ => {
				
				println( id )
				
				val res = Kata where ( _._id eqs new ObjectId( id ) ) fetch( 1 )
				
				res match {
					case head :: Nil => Full( Full( head ) )
					case Nil => Empty
				}
			}
		}
	}
	
	def encodeKata( kata: Box[Kata] ): String = {
		kata match {
			case Full(k) => k._id.toString
			case _ => ""
		}
	}

	lazy val loc = menu.toLoc
}

class Interpret( kata: Box[Kata] )
{
	private def defaultCode = ""

	def render = {
		
		"#eval [onclick]" #> {
			val res = SHtml.ajaxCall(
				MirrorValById(),
				code => {
					
					val result = Interpreter.evalText( code )
					
					val parent = kata match {
						case Full( k ) => {
							k._id.is
						}
						case _ => new ObjectId
					}

					val newkata = Kata
						.createRecord
						.code( code )
						.result( result )
						.parent( parent )
						.save

					SetHtml( "result", Text( result ) ) &
					JsRaw( "resultMirror.setValue( document.getElementById( 'result' ).value )" ) &
					JsRaw( "$('#eval').attr('disabled', '')" ) &
			    	JsRaw( "window.history.pushState( null, null,'/" + newkata._id + "')" )
				}
			)
			
			( res._1, res._2 & JsRaw("return false") )
		}
	}
	

	def code = "#code *" #> {
		S.param("code") openOr {				// Form submit /wo javascript
			kata match {
				case Full( k ) => k.code.is	// GET Url
				case _ => defaultCode			// Invalid kata or Index
			}
		}
	}
	
	def eval = "#result *" #> {
		kata match {
			case Full( k ) => k.result.is
			case _ => Interpreter.evalText( 
				S.param("code") openOr defaultCode 
			)
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