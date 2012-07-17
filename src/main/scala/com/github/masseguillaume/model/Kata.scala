package com.github.masseguillaume.model

import net.liftweb._
import record.field._
import mongodb.record._
import field._

class Kata private() 
	extends MongoRecord[Kata] 
	with MongoId[Kata]
{
	def meta = Kata
	
	object in extends StringField( this, 5000 )
	object out extends StringField( this, 500 )
}

object Kata extends Kata with MongoMetaRecord[Kata]
{
	
}