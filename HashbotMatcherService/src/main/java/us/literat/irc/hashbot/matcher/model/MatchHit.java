package us.literat.irc.hashbot.matcher.model;

import org.mongodb.morphia.annotations.Embedded;

import java.util.Date;

/**
 * Created by wiggins on 12/20/14.
 */

@Embedded
public class MatchHit {
    private String nick;
    private Integer count;
    private Date lastTime;
    private Date firstTime;

    public MatchHit() {}

    public MatchHit(String nick) {
        this.nick = nick;
        this.count = 1;
        this.firstTime = new Date();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    @Override
    public String toString() {
        return "MatchHit{" +
                "nick='" + nick + '\'' +
                ", count=" + count +
                ", lastTime=" + lastTime +
                ", firstTime=" + firstTime +
                '}';
    }
}
