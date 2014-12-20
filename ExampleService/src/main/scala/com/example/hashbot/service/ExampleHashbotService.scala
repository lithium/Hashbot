package com.example.hashbot.service

import org.apache.camel.{ProducerTemplate, Exchange}
import org.apache.camel.component.irc.IrcMessage
import org.slf4j.LoggerFactory
import us.literat.irc.hashbot.support.ReceivesIrcMessages

/**
 * Created by wiggins on 12/19/14.
 */
class ExampleHashbotService extends ReceivesIrcMessages
{
  val log = LoggerFactory.getLogger(getClass)

  override def receiveIrcMessage(message: IrcMessage, exchange: Exchange, endpoint: ProducerTemplate): Unit = {
    if (message.getMessageType().equals("PRIVMSG")) {
      log.info("<"+message.getUser.getNick+"> "+message.getMessage)
      endpoint.sendBody("interesting "+message.getUser.getNick+", tell me more about "+message.getMessage)
    } else {
      log.info(message.toString)
    }
  }
}
