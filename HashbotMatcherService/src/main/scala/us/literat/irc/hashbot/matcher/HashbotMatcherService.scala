package us.literat.irc.hashbot.matcher

import java.util.Date
import org.apache.camel.{BeanInject, ProducerTemplate, Exchange}
import org.apache.camel.component.irc.IrcMessage
import org.osgi.framework.{BundleContext, BundleActivator}
import us.literat.irc.hashbot.matcher.model.MatchHit
import us.literat.irc.hashbot.matcher.repository.MatchRepository
import us.literat.irc.hashbot.support.ReceivesIrcMessages
import collection.JavaConversions._

/**
 * Created by wiggins on 12/20/14.
 */
class HashbotMatcherService extends ReceivesIrcMessages
{
  @BeanInject
  val matchRepo: MatchRepository = _

  override def receiveIrcMessage(message: IrcMessage, exchange: Exchange, endpoint: ProducerTemplate): Unit = {
    if (!message.getMessageType.equals("PRIVMSG")) {
      return
    }
    val txt = message.getMessage
    val nick = message.getUser.getNick

    val allMatches = matchRepo.findAll()

    allMatches.foreach(m => {
      val count = m.getPattern.r.findAllMatchIn(txt).size
      if (count > 0) {
        val hit = m.getMatchHits.find(_.getNick == nick).getOrElse(new MatchHit(nick))
        hit.setLastTime(new Date)
        matchRepo.persist(m)
      }

    })

  }
}
