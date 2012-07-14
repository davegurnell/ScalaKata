package com.github.masseguillaume.service

import net.liftweb.common.LazyLoggable
import net.liftweb.http.LiftRulesMocker.toLiftRules
import net.liftweb.http.LiftRules

// from: https://github.com/heralight/Lift-MongoDb-Crudify
trait BaseService extends LazyLoggable {

	private var _state = false

	/**
	  * Service state, true if active
	  */
	def state() = _state

	final def init(): Unit = {
		internalInit()
		LiftRules.unloadHooks.append( () => stop() )
		_state = true
		logger.info( "Launched" )
	}

	private final def stop(): Unit = {
		if ( !_state ) return
		internalStop()
		_state = false
		logger.info( "Stopped" )
	}

	protected def internalInit(): Unit
	protected def internalStop(): Unit
}