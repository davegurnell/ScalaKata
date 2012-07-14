package com.github.masseguillaume.service

object MongoDB extends BaseService {

	//import com.mongodb.{DBAddress, Mongo}
	
	protected def internalInit() {
		Option(System.getenv("MONGOHQ_URL")) match {	// specific to mongohq
			case Some(s) => {
				/*
					mongodb://<user>:<pass>@hatch.mongohq.com:10034/app003132345
				*/
			}
			
			case None => {
				
			}
		}
	}
	
	protected def internalStop() { }
}