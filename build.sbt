import com.typesafe.startscript.StartScriptPlugin

name := "Lift Hello World"

seq(StartScriptPlugin.startScriptForClassesSettings: _*)

libraryDependencies ++= {
  Seq(
    "org.eclipse.jetty" % "jetty-server" % "7.5.4.v20111024" % "compile->default",
    "org.eclipse.jetty" % "jetty-servlet" % "7.5.4.v20111024" % "compile->default",
    "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
    "net.liftweb" %% "lift-webkit" % "2.4" % "compile->default",
    "ch.qos.logback" % "logback-classic" % "1.0.1" % "compile->default"
  )
}
