package us.literat.irc.hashbot;

import org.apache.camel.Exchange;
import org.apache.camel.component.irc.IrcMessage;

/**
 * Created by wiggins on 12/19/14.
 */
public interface ReceivesIrcMessages {
    void receiveIrcMessage(String body, Exchange exchange, IrcMessage message);
}
