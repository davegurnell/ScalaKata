package com.github.masseguillaume.security

import java.security._
import java.io.FilePermission

import net.liftweb.common.LazyLoggable

/*
 * Really basic permission if you run code without
 * a source bail
 */
class ScalaKataSecurityPolicy extends Policy
{
	private val websitePermissions = new Permissions
	websitePermissions.add(new AllPermission)

	private val scriptPermissions = new Permissions
	scriptPermissions.add( new FilePermission("-","read") ) // all read
	
	override def getPermissions( codesource: CodeSource ) = {
		if ( codesource.getLocation == null ) scriptPermissions
		else websitePermissions
	}

	override def getPermissions( domain: ProtectionDomain ) = {
		if ( domain.getCodeSource.getLocation == null ) scriptPermissions
		else websitePermissions
	}
}