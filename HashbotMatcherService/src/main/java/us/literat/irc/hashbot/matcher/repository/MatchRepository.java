package us.literat.irc.hashbot.matcher.repository;

import org.apache.camel.BeanInject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.MorphiaIterator;
import org.mongodb.morphia.query.Query;
import us.literat.irc.hashbot.matcher.model.Match;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by wiggins on 12/20/14.
 */
public class MatchRepository {


    private Datastore mongoDb;
    public Datastore getMongoDb() {
        return mongoDb;
    }
    public void setMongoDb(Datastore mongoDb) {
        this.mongoDb = mongoDb;
    }


    public Match findByKey(String key) {
        return mongoDb.find(Match.class).field("key").equal(key).get();
    }


    public List<Match> findAll() {
        return mongoDb.find(Match.class).asList();

    }

    public void persist(Match match) {
        mongoDb.save(match);
    }

}
