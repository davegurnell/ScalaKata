package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import Helpers._

class HelloWorld {
  def howdy = "#time *" #> ( new Date toString )
}
