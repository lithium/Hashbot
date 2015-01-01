package us.literat.irc.hashbot.matcher

import java.util
import java.util.Date
import org.apache.camel.{BeanInject, ProducerTemplate, Exchange}
import org.apache.camel.component.irc.IrcMessage
import org.osgi.framework.{BundleContext, BundleActivator}
import org.slf4j.LoggerFactory
import us.literat.irc.hashbot.matcher.model.MatchHit
import us.literat.irc.hashbot.matcher.repository.MatchRepository
import us.literat.irc.hashbot.support.ReceivesIrcMessages
import collection.JavaConversions._
import scala.beans.BeanProperty

/**
 * Created by wiggins on 12/20/14.
 */
class HashbotMatcherService extends ReceivesIrcMessages
{
  @BeanProperty
  var matchRepo: MatchRepository = _

  val log = LoggerFactory.getLogger(getClass)


  override def receiveIrcMessage(message: IrcMessage, exchange: Exchange, endpoint: ProducerTemplate): Unit = {
    if (!message.getMessageType.equals("PRIVMSG")) {
      return
    }
    val txt = message.getMessage
    val nick = message.getUser.getNick

    val allMatches = matchRepo.findAll()

    allMatches.foreach(m => {
      val count = m.getPattern.r.findAllMatchIn(txt).size
      log.debug("found "+count+" matches for: "+m)
      if (count > 0) {
        if (m.getMatchHits == null) {
          m.setMatchHits(new util.ArrayList[MatchHit])
        }
        val hit = m.getMatchHits.find(_.getNick == nick)
        if (hit.isDefined) {
          hit.get.setLastTime(new Date)
          hit.get.incrementCount()
        } else {
          m.getMatchHits.add(new MatchHit(nick))
        }
        matchRepo.persist(m)
      }

    })

  }
}
