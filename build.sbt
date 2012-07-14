name := "ScalaKata"

scalaVersion := "2.9.1"

seq(webSettings :_*)

scanDirectories in Compile := Nil

resolvers += "twitter.com" at "http://maven.twttr.com/"

libraryDependencies ++= {
  Seq(
    "net.liftweb"         %% "lift-webkit"              % "2.4"           % "compile->default"    withSources(),
    "net.liftweb"         %% "lift-mongodb-record"      % "2.4",
    "ch.qos.logback"      % "logback-classic"           % "1.0.3",
    "org.eclipse.jetty" % "jetty-webapp" % "8.0.4.v20111024" % "container", // For Jetty 8
    "javax.servlet"       % "servlet-api"               % "2.5"           % "provided->default",
    "com.twitter"         % "util-eval"                 % "5.0.3"                                 withSources(),
	  "com.foursquare"      %% "rogue"                    % "1.1.8"                                 intransitive(),
    "org.mortbay.jetty"   % "jetty"                     % "6.1.22"        % "test",
    "junit"               % "junit"                     % "4.8"           % "test",
    "org.specs2"          %% "specs2"                   % "1.11"          % "test"
  )
}

scalacOptions += "-deprecation"

// port in container.Configuration := 8081

//"org.mortbay.jetty"   % "jetty"                     % "6.1.26"        % "test,container",