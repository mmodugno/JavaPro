package server;

import Vinculador.ListaVaciaExcepcion;
import egreso.MontoSuperadoExcepcion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import usuarios.CreationError;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@FunctionalInterface
public interface WithTransaction<E> {
    E method(Request req, Response res, EntityManager em) throws CloneNotSupportedException, ParseException, ListaVaciaExcepcion, IOException, MontoSuperadoExcepcion, SQLException, CreationError, ClassNotFoundException;
}
