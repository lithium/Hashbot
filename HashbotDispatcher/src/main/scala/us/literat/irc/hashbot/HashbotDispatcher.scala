package us.literat.irc.hashbot


import org.apache.camel.{Exchange, ProducerTemplate, EndpointInject}
import org.apache.camel.component.irc.IrcMessage
import org.osgi.framework.BundleContext
import org.slf4j.LoggerFactory
import scala.beans.BeanProperty

/**
 * Created by wiggins on 12/14/14.
 */

class HashbotDispatcher {
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

     if (!ignoredNicks.contains(msg.getUser.getNick)) {

        val filter = "(objectClass="+classOf[ReceivesIrcMessages].getName+")"
        val serviceReferences = bundleContext.getAllServiceReferences(null, filter)
        if (serviceReferences != null) {
          serviceReferences.foreach(ref => {
            val service = bundleContext.getService(ref).asInstanceOf[ReceivesIrcMessages]
            service.receiveIrcMessage(body, exchange, msg)
          })
        }

     }

 }
}
