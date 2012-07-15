package com.github.masseguillaume.service

object MongoService extends BaseService
{
	import net.liftweb.mongodb.{MongoDB, DefaultMongoIdentifier,  MongoAddress, MongoHost}
	import com.mongodb.{Mongo, MongoOptions, ServerAddress}
	
	protected def internalInit() {
		Option( System.getenv("MONGOHQ_URL") ) match {
			case Some( uri ) => {
              
				MongoURIParser( uri ) match {
					case Some( parsedUri ) => {
						
						import scala.collection.JavaConversions._
						import scala.collection.mutable.ListBuffer
						
						val servers = ListBuffer( parsedUri.servers: _* )
						
						MongoDB.defineDbAuth(
					      DefaultMongoIdentifier,
					      new Mongo( servers ),
					      parsedUri.databaseName,
					      parsedUri.username,
					      parsedUri.password
					    )
					}
					case None => { }
				}
			}
			case None => {
				logger.error( "[MongoDB]: url not defined" )
			}
		}
	}
	
	protected def internalStop() { }

	case class MongoURI( 
		username: String, 
		password: String, 
		servers: List[ServerAddress], 
		databaseName: String )
		
	import scala.util.parsing.combinator.Parsers
	import scala.util.parsing.combinator.RegexParsers
	
	object MongoURIParser extends RegexParsers
	{
		def apply( uri: String ): Option[MongoURI] = {
			MongoURIParser.parseAll( MongoURIParser.uri, uri ) match {
				case MongoURIParser.Success( parsedUri, _ ) => {
					Some( parsedUri )
				}
				case MongoURIParser.NoSuccess( why, _ ) => {
					logger.error( "[MongoDB]: uri(" + uri + ") not valid:(" + why + ")" )
					None
				}
			}
		}
		
		def uri: Parser[MongoURI] = {
			"mongodb://" ~ username ~ ":" ~ password ~ "@" ~ servers ~ "/" ~ database ^^ {
				case "mongodb://" ~ username ~ ":" ~ password ~ "@" ~ servers ~ "/"	~ database => {
					MongoURI( username, password, servers, database )
				}
			}
		}
		
		def servers: Parser[List[ServerAddress]] = {
			rep1sep( url ~ ":" ~ port, "," ) ^^ { servers =>
				servers.map{
					case url ~ ":" ~ port => new ServerAddress( url, port )
				}
			}
		}
		
		def username: Parser[String] = word
		def password: Parser[String] = word
		
		def url: Parser[String] = """(\w|\.)*""".r
      	def port: Parser[Int] = number ^^ { x => x.toInt }
		
		def database: Parser[String] = word
		
		def word = """\w*""".r
		def number = """\d+""".r
	}
}