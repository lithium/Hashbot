package us.literat.irc.hashbot.links.repository

import java.util.regex.Pattern

import org.mongodb.morphia.Datastore
import us.literat.irc.hashbot.links.model.Link

import scala.beans.BeanProperty
import scala.collection.JavaConversions._


/**
 * Created by wiggins on 12/15/14.
 */
class LinkRepository {

  @BeanProperty
  var linksDb:Datastore = _

  val ModelClass = classOf[Link]

  def createIfNeeded(): Unit = { }

  def findByUri(uri:String): Link = {
    linksDb.find(classOf[Link]).field("_id").contains(uri).get
  }


  def findAllByNick(nick:String, limit:Integer=0):Seq[Link] = {
    linksDb.find(classOf[Link]).field("nick").equal(nick).limit(limit).order("-originalPostDate").toSeq
  }

  def findAllByUriRegex(pattern:String, limit:Integer=0):Seq[Link] = {
    linksDb.find(classOf[Link]).filter("_id", Pattern.compile(pattern)).limit(limit).order("-originalPostDate").toSeq
  }

  def persist(link:Link): Unit = {
    val foo = linksDb.save(link, null)
  }



}
