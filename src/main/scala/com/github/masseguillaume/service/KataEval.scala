package com.github.masseguillaume.service

import scala.xml.Elem

import com.twitter.util.Eval
import com.twitter.util.Eval._

import net.liftweb._
import util.Props
import common.{Box,Failure,Full}

import net.liftweb.util.Props

object KataEval {

	private lazy val eval = new Eval()
	
	def apply( code: String ) : Box[ Pair[ Any, String ] ] = {

		try{
			
			val baos = new java.io.ByteArrayOutputStream
			
			// redirect IO for this request
			val result = Console.withOut( baos ) {
				eval[Any]( code )
			}
			
			Full( ( result , baos.toString ) )
		}
		catch
		{
			case ex: Exception => {
				
				val messages = ex.getStackTrace.foldLeft( ex.getMessage )( _ + "\n" + _) 
				
				Failure( messages ) 
			} 
		}
	}
	
	val timeout = Props.get( "eval.timeout" ).openOr("10000").toLong
	
	@throws(classOf[java.util.concurrent.TimeoutException])
	def evalWithin[F](timeout: Long)(f: => F): F = {

		import java.util.concurrent.{Callable, FutureTask, TimeUnit}

		  val task = new FutureTask(new Callable[F]() {
		    def call() = f
		  })

		  new Thread(task).start()

		  task.get(timeout, TimeUnit.MILLISECONDS)
	}
}