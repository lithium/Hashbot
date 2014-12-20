package us.literat.irc.hashbot.dispatch

import org.apache.camel.component.irc.IrcMessage
import org.apache.camel.{EndpointInject, Exchange, ProducerTemplate}
import org.osgi.framework.BundleContext
import org.slf4j.LoggerFactory
import us.literat.irc.hashbot.support.ReceivesIrcMessages

import scala.beans.BeanProperty

/**
 * Created by wiggins on 12/14/14.
 */

class HashbotDispatcher
{
  val log = LoggerFactory.getLogger(getClass)

  @BeanProperty
  var bundleContext:BundleContext = _

  @BeanProperty
  var nicksToIgnore:String = _
  lazy val ignoredNicks = nicksToIgnore.split(',').toSet

  @EndpointInject(ref="ircEndpoint")
  var producer:ProducerTemplate = _

  def receiveMessage(body:String, exchange:Exchange): Unit = {
    val msg:IrcMessage = exchange.getIn.getBody(classOf[IrcMessage])

     log.debug(msg.toString)

     if (!ignoredNicks.contains(msg.getUser.getNick)) {

       val filter = "(objectClass=" + classOf[ReceivesIrcMessages].getName + ")";
       val providers = bundleContext.getAllServiceReferences(null, filter)

       if (providers != null) {
         providers.foreach(provider => {
           val service = bundleContext.getService(provider).asInstanceOf[ReceivesIrcMessages]
           service.receiveIrcMessage(msg, exchange, producer)

         })
       }

     }

 }

}
