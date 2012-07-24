package com.github.masseguillaume.security

import java.security._

import net.liftweb.common.LazyLoggable

/*
 * Really basic permission if you run code without
 * a source bail
 */
class ScalaKataSecurityPolicy extends Policy
{
	private val allPermissions = new Permissions
	allPermissions.add(new AllPermission)

	private val noPermissions = new Permissions

	override def getPermissions( codesource: CodeSource ) = {
		if ( codesource.getLocation == null ) noPermissions
		else allPermissions
	}

	override def getPermissions( domain: ProtectionDomain ) = {
		if ( domain.getCodeSource.getLocation == null ) noPermissions
		else allPermissions
	}
}