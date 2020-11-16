package repositorios;


import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import jdk.internal.org.objectweb.asm.util.TraceMethodVisitor;

public class RepositorioDocumentos {

    public RepositorioDocumentos(){};

    public void crearTransaccion(Transaccion transaccion){
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.baeldung.morphia");
        Datastore datastore = morphia.createDatastore(new MongoClient("localhost",27017), "GeSoc");
        datastore.ensureIndexes();

        datastore.save(transaccion);
    }

    /*
    Morphia morphia = new Morphia();
morphia.mapPackage("com.baeldung.morphia");
Datastore datastore = morphia.createDatastore(new MongoClient("localhost",27017), "library");
datastore.ensureIndexes();
    */
}
