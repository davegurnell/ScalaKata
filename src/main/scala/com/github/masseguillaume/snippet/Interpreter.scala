package com.github.masseguillaume.snippet

import com.twitter.util.Eval
import net.liftweb.util.Helpers.strToCssBindPromoter

object Interpreter
{
	private lazy val eval = new Eval()
	
	def app( text: String ):String = eval( text ).toString 
}

class Interpreter
{
	def test = "#kata-code-text *" #> {
		Interpreter.app(
		"""
			List( 1, 2, 3, 4 ) ::: List( 1, 2 )
		""")
	}
}