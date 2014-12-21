package us.literat.irc.hashbot.matcher.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;
import java.util.List;

/**
 * Created by wiggins on 12/20/14.
 */
@Entity
public class Match {
    @Id
    private String key;

    private String pattern;
    private String createdBy;
    private Date createdOn;
    private List<MatchHit> matchHits;

    public Match() {}

    public Match(String key, String pattern, String createdBy) {
        this.key = key;
        this.pattern = pattern;
        this.createdBy = createdBy;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public List<MatchHit> getMatchHits() {
        return matchHits;
    }

    public void setMatchHits(List<MatchHit> matchHits) {
        this.matchHits = matchHits;
    }

    @Override
    public String toString() {
        return "Match{" +
                "key='" + key + '\'' +
                ", pattern='" + pattern + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", matchHits=" + matchHits +
                '}';
    }
}
