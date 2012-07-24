package com.github.masseguillaume.security

import java.security._
import net.liftweb.util.Props

object ScalaKataSecurity {

	def start = {
		
		Props.mode match {
			case Props.RunModes.Production => {
				Policy.setPolicy( new ScalaKataSecurityPolicy )
				System.setSecurityManager( new SecurityManager( ) )
			} 
			case _ => ()                                        
		}
	}
}