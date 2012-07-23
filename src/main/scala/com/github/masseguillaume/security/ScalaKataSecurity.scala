package com.github.masseguillaume.security

import java.security._

object ScalaKataSecurity {

	def start = {
		Policy.setPolicy( new ScalaKataSecurityPolicy )
		System.setSecurityManager( new SecurityManager( ) )
	}
}