package us.literat.irc.hashbot.links.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;


/**
 * Created by wiggins on 12/15/14.
 */

@Entity("links")
public class Link {

  @Id
  private String uri;
  private String nick;
  private Date originalPostDate;
  private Integer postCount=1;

    public Link() {  }

    public Link(String uri, String nick, Date originalPostDate) {
        this.uri = uri;
        this.nick = nick;
        this.originalPostDate = originalPostDate;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Date getOriginalPostDate() {
        return originalPostDate;
    }

    public void setOriginalPostDate(Date originalPostDate) {
        this.originalPostDate = originalPostDate;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    @Override
    public String toString() {
        return "Link{" +
            "uri='" + uri + '\'' +
            ", nick='" + nick + '\'' +
            ", originalPostDate=" + originalPostDate +
            ", postCount=" + postCount +
            '}';
    }

}
