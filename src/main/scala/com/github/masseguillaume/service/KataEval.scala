package com.github.masseguillaume.service

import com.twitter.util.Eval
import com.twitter.util.Eval._

import net.liftweb.util.Props

object KataEval {

	private lazy val eval = new Eval()
	
	def apply( code: String ) = {
		try{

			// redirect IO for this request
			val baos = new java.io.ByteArrayOutputStream
			Console.withOut( baos ) {

				eval( code ).toString match {
					case "()" => ""
					case other => other + "\n"
				}

			} + baos.toString
		}
		catch
		{
			case ex: Exception => ex.getMessage 
		}
	}
}