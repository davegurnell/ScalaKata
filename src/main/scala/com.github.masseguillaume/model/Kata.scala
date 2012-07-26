package com.github.masseguillaume.model

import net.liftweb._
import record.field._
import mongodb.record._
import field._

import com.github.masseguillaume.service.KataMongo

class Kata extends MongoRecord[Kata] with MongoId[Kata]
{
	def meta = Kata

	object parent extends ObjectIdField( this )
	object code extends StringField( this, 5000 )
}

object Kata extends Kata with MongoMetaRecord[Kata]
{
	override def mongoIdentifier = KataMongo
}