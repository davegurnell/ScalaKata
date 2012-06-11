package com.github.masseguillaume.snippet

import com.twitter.util.Eval
import com.twitter.util.Eval.CompilerException
import net.liftweb.util.Helpers.strToCssBindPromoter
import net.liftweb.http.S
import com.twitter.util.Eval.CompilerException

object Interpreter
{
	private lazy val eval = new Eval()

	def evalText( text: String ) = eval( text ).toString 
}

class Interpreter
{
	private def defaultCode = "1 == true"
	
	def code = "#kata-code *"#> {
		S.param("code") openOr defaultCode
	}
	
	def eval = "#kata-result *"#> {
		try{
			Interpreter.evalText( S.param("code") openOr defaultCode )
		}
		catch
		{
			case ex: CompilerException => ex.getMessage 
		}
	}
}