package us.literat.irc.hashbot.matcher.management;


import org.apache.camel.BeanInject;
import org.apache.camel.api.management.ManagedResource;
import us.literat.irc.hashbot.matcher.repository.MatchRepository;

import javax.management.MXBean;

/**
 * Created by wiggins on 12/26/14.
 */

public interface MatcherManagerMBean {


    public void createNewMatch(String key, String pattern);
}
