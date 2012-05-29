package bootstrap
package liftweb

import net.liftweb.common.Loggable

import net.liftweb.util.Helpers._
import net.liftweb.http.Html5Properties
import net.liftweb.http.LiftRules
import net.liftweb.http.Req

import net.liftweb.sitemap.SiteMap
import net.liftweb.sitemap.Menu

class Boot {
  def boot {
    LiftRules.addToPackages("code")
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))
		
    LiftRules.setSiteMap(SiteMap(Menu("Home") / "index"))

    LiftRules.enableContainerSessions = false
    LiftRules.statelessReqTest.append { case _ => true }

    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    
    // Use HTML5 for rendering
	LiftRules.htmlProperties.default.set((r: Req) =>
	  new Html5Properties(r.userAgent))  
  }
}
