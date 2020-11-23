package Persitence;

import static org.junit.Assert.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
/*
    @Test
    public void contextUp() {
        MongoClientURI uri = new MongoClientURI(
                "mongodb://GeSoc:dds2020@cluster0-shard-00-00.lvoi9.mongodb.net:27017,cluster0-shard-00-01.lvoi9.mongodb.net:27017,cluster0-shard-00-02.lvoi9.mongodb.net:27017/test?ssl=true&replicaSet=atlas-r6t4sh-shard-0&authSource=admin&retryWrites=true&w=majority");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("test");
    }*/
/*	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}
	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}*/


}