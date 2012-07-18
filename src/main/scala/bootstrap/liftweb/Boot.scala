package bootstrap.liftweb

import com.github.masseguillaume.service._
import com.github.masseguillaume.snippet.Interpreter

import net.liftweb._
import http._
import util.Helpers._

import net.liftweb.http._
import net.liftweb.common.Full
import net.liftweb.sitemap.{SiteMap,Menu}
import net.liftweb.util.Vendor.valToVender

class Boot {
	def boot {

		LiftRules.addToPackages("com.github.masseguillaume")
		
		KataMongo.start
    
		LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

		val sitemap = List( Menu( Interpreter.loc ) )
		
		LiftRules.setSiteMap(SiteMap(sitemap:_*))

		LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

		// Use HTML5 for rendering
		LiftRules.htmlProperties.default.set((r: Req) =>
	  		new Html5Properties(r.userAgent)
		)
	  
		//Show the spinny image when an Ajax call starts
    	LiftRules.ajaxStart =
      	Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    	// Make the spinny image go away when it ends
    	LiftRules.ajaxEnd =
      	Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
  	}
}