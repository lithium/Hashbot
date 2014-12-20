package us.literat.irc.hashbot.support;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.irc.IrcMessage;

/**
 * Created by wiggins on 12/19/14.
 */
public interface ReceivesIrcMessages {
    void receiveIrcMessage(IrcMessage message, Exchange exchange, ProducerTemplate endpoint);
}
