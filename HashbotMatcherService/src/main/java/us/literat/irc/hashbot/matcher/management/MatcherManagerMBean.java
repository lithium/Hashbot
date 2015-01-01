package us.literat.irc.hashbot.matcher.management;


import org.apache.camel.api.management.ManagedResource;

/**
 * Created by wiggins on 12/26/14.
 */

public interface MatcherManagerMBean {


    public void createNewMatch(String pattern);
}
