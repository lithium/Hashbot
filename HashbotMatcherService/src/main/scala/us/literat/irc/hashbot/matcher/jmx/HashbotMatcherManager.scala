package us.literat.irc.hashbot.matcher.jmx


import org.slf4j.LoggerFactory
import us.literat.irc.hashbot.matcher.management.MatcherManagerMBean

/**
 * Created by wiggins on 12/21/14.
 */

class HashbotMatcherManager extends MatcherManagerMBean {
  val log = LoggerFactory.getLogger(getClass)

  def createNewMatch(pattern: String) = {
    log.debug("creating new match: "+pattern)

  }

}
