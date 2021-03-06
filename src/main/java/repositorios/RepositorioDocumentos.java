package repositorios;


import com.google.gson.Gson;
import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import jdk.internal.org.objectweb.asm.util.TraceMethodVisitor;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDocumentos {

    public RepositorioDocumentos(){};

    public void crearTransaccion(Transaccion transaccion){
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.baeldung.morphia");
        Datastore datastore = morphia.createDatastore(new MongoClient("localhost",27017), "GeSoc");
        datastore.ensureIndexes();

        datastore.save(transaccion);
    }

    public Object documentos(String tipo, String operacion) {
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.baeldung.morphia");
        Datastore datastore = morphia.createDatastore(new MongoClient("localhost",27017), "GeSoc");
        datastore.ensureIndexes();
        List<Transaccion> transacciones = new ArrayList<>();

        Gson gson = new Gson();

        if (tipo.equals("todos") && operacion.equals("todos")){
            transacciones = datastore.createQuery(Transaccion.class).find().toList();

        }else if(tipo.equals("todos")){
            transacciones = datastore.createQuery(Transaccion.class)
                    .field("operacion")
                    .contains(operacion)
                    .find()
                    .toList();

        }else if(operacion.equals("todos")){
            transacciones = datastore.createQuery(Transaccion.class)
                    .field("documento")
                    .contains(tipo)
                    .find()
                    .toList();

        }else{
            transacciones = datastore.createQuery(Transaccion.class)
                    .field("operacion")
                    .contains(operacion)
                    .field("documento")
                    .contains(tipo)
                    .find()
                    .toList();
        }

        return gson.toJson(transacciones);
    }

    /*
    Morphia morphia = new Morphia();
morphia.mapPackage("com.baeldung.morphia");
Datastore datastore = morphia.createDatastore(new MongoClient("localhost",27017), "library");
datastore.ensureIndexes();
    */
}
