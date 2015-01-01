package us.literat.irc.hashbot.matcher.jmx


import org.apache.camel.BeanInject
import org.slf4j.LoggerFactory
import us.literat.irc.hashbot.matcher.management.MatcherManagerMBean
import us.literat.irc.hashbot.matcher.model.Match
import us.literat.irc.hashbot.matcher.repository.MatchRepository

import scala.beans.BeanProperty

/**
 * Created by wiggins on 12/21/14.
 */

class HashbotMatcherManager extends MatcherManagerMBean {

  @BeanProperty
  var matchRepo:MatchRepository = _

  val log = LoggerFactory.getLogger(getClass)

  def createNewMatch(key:String, pattern: String):Unit = {
//    log.debug("creating new match: "+pattern)

    val existing = matchRepo.findByKey(key)
    if (existing != null) {
      log.info("createNewMatch: key '{}' already exists", key)
      return
    }
    val m = new Match(key, pattern, "jmx")
    matchRepo.persist(m)
    log.info("Saved new match {}", m)

  }

}
