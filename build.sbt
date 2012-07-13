import com.typesafe.startscript.StartScriptPlugin

name := "ScalaKata"

seq(StartScriptPlugin.startScriptForClassesSettings: _*)

// Add Twitter's Repository
resolvers += "twitter.com" at "http://maven.twttr.com/"

libraryDependencies ++= {
  Seq(
    "org.eclipse.jetty" % "jetty-server" % "7.5.4.v20111024" % "compile->default",
    "org.eclipse.jetty" % "jetty-servlet" % "7.5.4.v20111024" % "compile->default",
    "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
    "net.liftweb" %% "lift-webkit" % "2.4" % "compile->default" withSources(),
    "ch.qos.logback" % "logback-classic" % "1.0.1" % "compile->default",
    "com.twitter" % "util-eval" % "5.0.3" withSources(),
	"com.foursquare" %% "rogue" % "1.1.8" intransitive(),
	"net.liftweb" %% "lift-mongodb-record" % "2.4"
  )
}