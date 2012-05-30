package code
package snippet

import java.util.Date

import net.liftweb.util.Helpers.strToCssBindPromoter

class HelloWorld {
  def howdy = "#time *" #> ( new Date toString )
}