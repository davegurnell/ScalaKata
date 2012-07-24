package com.github.masseguillaume.service

import com.twitter.util.Eval
import com.twitter.util.Eval._
import net.liftweb.util.Props
import scala.xml.Elem

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
			case ex: Exception => ex.getStackTrace.foldLeft( ex.getMessage )( _ + "\n" + _) 
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