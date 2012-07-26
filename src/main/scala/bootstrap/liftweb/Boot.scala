package bootstrap.liftweb

import net.liftweb._
import util.Helpers._
import http._
import common.Full
import sitemap.{SiteMap,Menu}
import util.Vendor.valToVender

import com.github.masseguillaume._
import service._
import snippet.Interpreter
import security.ScalaKataSecurity

class Boot {
	def boot {

		ScalaKataSecurity.start
		KataMongo.start
		
		LiftRules.addToPackages("com.github.masseguillaume")
	
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