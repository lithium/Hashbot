package us.literat.irc.hashbot.links

import java.util.Date

import org.apache.camel.component.irc.IrcMessage
import org.apache.camel.{EndpointInject, Exchange, ProducerTemplate}
import com.ocpsoft.pretty.time.PrettyTime
import org.osgi.framework.{BundleActivator, BundleContext}
import org.slf4j.LoggerFactory
import us.literat.irc.hashbot.ReceivesIrcMessages
import us.literat.irc.hashbot.links.model.Link
import us.literat.irc.hashbot.links.repository.LinkRepository

import scala.beans.BeanProperty

/**
 * Created by wiggins on 12/19/14.
 */
class HashbotLinkService extends BundleActivator with ReceivesIrcMessages
{
  val log = LoggerFactory.getLogger(getClass)

  @EndpointInject(ref="ircEndpoint")
  var producer:ProducerTemplate = _

  @BeanProperty
  var linkRepo:LinkRepository = _

  @BeanProperty
  var linkLimit:Integer = 5

  @BeanProperty
  var uriRegex:String = _
  lazy val uriPattern = uriRegex.r

  @BeanProperty
  var urlQueryPrefix:String = _

  var prettyTime:PrettyTime = new PrettyTime()

  override def start(context: BundleContext): Unit = {
    context.registerService(classOf[HashbotLinkService].getName, this, null)
  }

  override def stop(context: BundleContext): Unit = {
  }

  override def receiveIrcMessage(body:String, exchange:Exchange, msg:IrcMessage): Unit = {
    val txt:String = msg.getMessage
    if (txt startsWith urlQueryPrefix+" ") {

      val (_, pattern) = txt.splitAt(5)
      val links = if (pattern.startsWith("@")) {
        linkRepo.findAllByNick(pattern.substring(1), linkLimit)
      }
      else {
        val pat = if (pattern.startsWith("/") && pattern.endsWith("/")) {
          pattern.substring(1, pattern.length-1)
        } else pattern
        linkRepo.findAllByUriRegex(pat, linkLimit)
      }
      val responseText = links.map(_.getUri).mkString("  ")
      producer.sendBody(msg.getUser.getNick+": "+(if (links.size > 0) responseText else "no matches."))
    }
    else {

      val reposts = uriPattern.findAllMatchIn(txt).map(m => {
        val postedUri = m.matched
        var link = linkRepo.findByUri(postedUri)

        if (link == null) {
          link = new Link(postedUri, msg.getUser.getNick, new Date)
          log.info("New link: " + link)
          linkRepo.persist(link)

          None
        } else {
          link.setPostCount(link.getPostCount+1)
          log.info("Reposted link: " + link)
          linkRepo.persist(link)

          Option(link)
        }
      }).toSet.filter(_.isDefined).map(_.get)

      if (reposts.size > 1) {
        producer.sendBody("Nice reposts " + msg.getUser.getNick + ".")
      } else if (reposts.size > 0) {
        val link:Link = reposts.head
        val repostMsg = if (link.getOriginalPostDate != null) {
          (if (link.getNick == msg.getUser.getNick) "You" else link.getNick)+" already posted that "+prettyTime.format(link.getOriginalPostDate)+"."
        } else ""

        producer.sendBody("Nice repost "+msg.getUser.getNick+". "+repostMsg)
      }


    }
  }
}
