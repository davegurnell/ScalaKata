package bootstrap
package liftweb

import net.liftweb.http.LiftRules
import net.liftweb.sitemap.{SiteMap,Menu}

class Boot {
  def boot {
    LiftRules.addToPackages("code")
		
    LiftRules.setSiteMap(SiteMap(Menu("Home") / "index"))

    LiftRules.enableContainerSessions = false
    LiftRules.statelessReqTest.append { case _ => true }

    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
  }
}
