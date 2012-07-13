package bootstrap
package liftweb

import net.liftweb.http._
import net.liftweb.http.LiftRulesMocker.toLiftRules
import net.liftweb.sitemap.{SiteMap,Menu}
import net.liftweb.util.Vendor.valToVender

class Boot {
  def boot {
    LiftRules.addToPackages("code")
    LiftRules.addToPackages("com.github.masseguillaume")
    
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

    val sitemap = List( Menu("Home") / "index" )
	LiftRules.setSiteMap(SiteMap(sitemap:_*))

    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    
    // Use HTML5 for rendering
	LiftRules.htmlProperties.default.set((r: Req) =>
	  new Html5Properties(r.userAgent))  
  }
}
