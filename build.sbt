name := "ScalaKata"

organization := "com.github.masseguillaume"

scalaVersion := "2.9.1"

resolvers ++= {
	Seq(
		"twitter.com" 			at "http://maven.twttr.com/",
		"snapshots" 			at "http://oss.sonatype.org/content/repositories/snapshots",
		"releases"				at "http://oss.sonatype.org/content/repositories/releases"
	)
}

libraryDependencies ++= {
	Seq(
		"net.liftweb"         	%% "lift-webkit"           	% "2.4"           		% "compile->default"    withSources(),
		"net.liftweb"         	%% "lift-mongodb-record"   	% "2.4",
		"ch.qos.logback"      	%  "logback-classic"       	% "1.0.3",
		"org.eclipse.jetty"		%  "jetty-webapp" 			% "8.0.4.v20111024" 	% "container",
		"javax.servlet"       	%  "servlet-api"           	% "2.5"           		% "provided->default",
		"com.twitter"         	%  "util-eval"             	% "5.3.1"                                 		withSources(),
		"com.foursquare"      	%% "rogue"                 	% "1.1.8"                                 		intransitive(),
		"org.specs2"          	%% "specs2"                	% "1.11",
		// for fun
		"org.scalaz" 			%% "scalaz-core" 			% "6.0.4",
		"org.scalatest" 		%% "scalatest" 				% "1.8"
 	)
}

seq(webSettings :_*)

scalacOptions ++= Seq("-deprecation", "-unchecked")