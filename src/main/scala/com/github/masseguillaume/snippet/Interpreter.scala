package com.github.masseguillaume.snippet

import net.liftweb._
import common.{Box,Failure,Full,Empty}
import sitemap._
import sitemap.Loc._
import util._
import util.Helpers._
import http._
import js._
import JE._
import JsCmds._

import com.github.masseguillaume._
import service.KataEval
import model.Kata

import com.foursquare.rogue.Rogue._
import org.bson.types._

import scala.xml.{Text,Elem,NodeSeq}

case class KataRessource( id: String )

object Interpreter
{
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

				try
				{
					val res = Kata where ( _._id eqs new ObjectId( id ) ) fetch( 1 )
					
					res match {
						case head :: Nil => Full( Full( head ) )
						case Nil => Empty
					}	
				}
				catch {
					case e: IllegalArgumentException => Empty // not an ObjectId
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
	def render = {
		
		"#run [onclick]" #> {
			val res = SHtml.ajaxCall(
				GetCode(),
				code => run( code )
			)
			
			( res._1, res._2 & JsRaw("return false") ) // does not submit
			
		} & 
		"#code *" #> {
			S.param("code") openOr {			// Form submit /wo javascript
				kata match {
					case Full( k ) => k.code.is	// GET Url
					case _ => ""				// Index ( new Kata )
				}
			}
		}
	}
	
	def run( code: String ) = {
		def clearOutput = {
			JsRaw( """
				$("#result").html("");
			""")
		}

		def showResultWindow = {
			JsRaw( """
				$('#code-wrap').addClass('with-results')
				$('#code-wrap,#result-wrap').css('height','')
				$('#result-window').removeClass('hidden')
				codeMirror.refresh()
			""")
		}
		
		def showBoolean( value: Boolean ) = {
			JsRaw("$('#code-wrap .cm-s-ambiance').addClass('" + value.toString + "')") &
			JsRaw("""
				(function(){
				
					this.restore = function(){ 
						$('#code-wrap .cm-s-ambiance').removeClass('""" + value.toString + """','');
					}
					
					$(document)
						.click( this.restore )
						.keydown( this.restore )
					
				}());
			""")	
		}

		val handleOutput = KataEval( code ) match {

			case Full( ( result, print ) ) => {
				
				def handlePrint( print: String ) = SetHtml("console", Text( print ) )
				
				def handleResult( result: Any ) = result match {

					case b: Boolean => showBoolean( b )
					case e: NodeSeq => SetHtml( "result", e )
					case n: Unit => Noop
					case _ => SetHtml( "result", Text( result.toString ) )
				}

				handlePrint( print ) & handleResult( result )
			}
			
			case Failure( error, _, _ ) => {
				SetHtml("console", Text( error.toString ) ) &
				showBoolean( false )
			}
			case Empty => Noop
		} 

							
		def enableRun = {
			JsRaw( "$('#run').attr('disabled', '')" )						
		}
		
		def pushKataId( newKata: Kata ) = {
			JsRaw( "window.history.pushState( null, null,'/" + newKata._id + "')" )
		}

		val parent = kata match {
			case Full( k ) => {
				k._id.is
			}
			case _ => new ObjectId
		}

		val newKata = Kata
			.createRecord
			.code( code )
			.parent( parent )
			.save

		clearOutput &
		handleOutput &
		enableRun &
		showResultWindow &
		pushKataId( newKata )
	}
	
	case class GetCode() extends JsExp {
		def toJsCmd = 
		"""
			( function() {
				$('#run').attr('disabled','disabled')
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