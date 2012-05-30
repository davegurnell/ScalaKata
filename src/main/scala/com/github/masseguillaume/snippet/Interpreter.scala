package com.github.masseguillaume.snippet

import scala.tools.nsc.interpreter.IMain

import net.liftweb.util.Helpers.strToCssBindPromoter

class Interpreter
{
	def test = "#test *" #> {
		val compiler = new IMain
		
		val res = compiler.interpret( "val a = List(1,2,3,4)" )
		
		res.toString()
	}
}