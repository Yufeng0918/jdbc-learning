package lecture07.mongo;

import com.mongodb.*;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 * Created by ribbo on 7/6/2017.
 */
public class MongoDBTest {

    @Test
    public void testDatabase() {
        Mongo mongo = null;
        try {
            mongo = new Mongo("localhost", 27017);
            for (String name : mongo.getDatabaseNames()) {
                System.out.println("db name: " + name);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCollection() {
        Mongo mongo;
        try {
            mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("test");

            for (String name : db.getCollectionNames()) {
                System.out.println("collection name: " + name);
            }

            DBCollection collection = db.getCollection("userinfo");
            DBCursor cursor = collection.find();

            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
