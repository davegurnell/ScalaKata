import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler}
import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.server.session.SessionHandler

import net.liftweb.http.LiftFilter


object JettyLauncher extends App {
  // Slightly modified from
  // https://github.com/ghostm/lift_blank_heroku
  // to change Application to App
  // Original version was modified based on the
  // Scalatra Jetty Launcher

  val port = if(System.getenv("PORT") != null) System.getenv("PORT").toInt else 9090
  val server = new Server
  val scc = new SelectChannelConnector
  scc.setPort(port)
  server.setConnectors(Array(scc))

  val context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS)
  context.setSessionHandler(new SessionHandler())
  context.addServlet(classOf[DefaultServlet], "/");
  context.addFilter(classOf[LiftFilter], "/*", 0)
  context.setResourceBase("src/main/webapp")

  server.start
  server.join
}
