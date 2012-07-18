package com.github.masseguillaume.service

import net.liftweb.util.Props
import net.liftweb.mongodb.{MongoDB, MongoIdentifier, MongoAddress, MongoHost}
import com.mongodb.{Mongo, MongoOptions, ServerAddress}

object KataMongo extends MongoIdentifier
{
	override def jndiName = "scala_mongo"
	
	private var mongo: Option[Mongo] = None
	
	def start = {
		
		val address = Props.get( "mongo.address" ).openOr("localhost")
		val port = Props.get( "mongo.port" ).openOr("27017").toInt
		val database = Props.get( "mongo.database" ).openOr("scalakata")

    	mongo = Some( new Mongo( new ServerAddress( address, port ) ) )
		
		Props.mode match {
			case Props.RunModes.Production => {
				
				val username = Props.get( "mongo.username" ).openOr("")
				val password = Props.get( "mongo.password" ).openOr("")
				
				MongoDB.defineDbAuth(
				   KataMongo,
				   mongo.get,
				   database,
				   username,
				   password
				 )
			}
			case _ => {

				MongoDB.defineDb(
				   KataMongo,
				   mongo.get,
				   database
				 )
			}
		}			
	}
	
	def stop = {
		mongo.foreach(_.close)
    	MongoDB.close
    	mongo = None
	}
}
