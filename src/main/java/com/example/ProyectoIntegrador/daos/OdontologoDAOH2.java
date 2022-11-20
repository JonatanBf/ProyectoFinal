package com.example.ProyectoIntegrador.daos;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOH2 implements Dao<Odontologo> {

    private static final Logger logger = LogManager.getLogger(OdontologoDAOH2.class);
    private final static String INSERT = "INSERT INTO Odontologos (nombre, apellido, matricula) VALUES (?, ?, ?);";
    private final static String SELECT_ALL = "SELECT * FROM Odontologos;";
    private final static String SELECT = "SELECT * FROM Odontologos WHERE id = ?;";
    private final static String UPDATE = "UPDATE Odontologos SET nombre = ?, apellido = ?, matricula= ? WHERE id = ?;";
    private final static String DELETE = "DELETE FROM Odontologos WHERE id = ?;";

    @Override
    public void agregar(Odontologo odontologo) {
        try {
            var c = ConnectionH2.urlOdontologos();
            var st = c.prepareStatement(INSERT);
            st.setString(1, odontologo.getNombre());
            st.setString(2, odontologo.getApellido());
            st.setString(3, odontologo.getMatricula());
            st.execute();
            logger.info("Se agrego correctamente: " + odontologo.getApellido() +" "+odontologo.getNombre()+" || Matricula: "+ odontologo.getMatricula());
        }catch (SQLException e) {
            logger.error("Metodo Agregar: "+ e.getMessage());
        }
    }

    @Override
    public List<Odontologo> listar() {
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            var c = ConnectionH2.urlOdontologos();
            var st = c.prepareStatement(SELECT_ALL);
            var rset = st.executeQuery();
            while (rset.next()) {
                var id = rset.getInt(1);
                var nombre = rset.getString(2);
                var apellido = rset.getString(3);
                var matricula = rset.getString(4);
                logger.info("Id: "+id+" || Nombre: "+nombre+" || Apellido: "+apellido+" || Matricula: "+matricula);
                odontologos.add(new Odontologo(id, nombre, apellido, matricula));
            }
        } catch (SQLException e) {
            logger.error("Metodo listar: " + e.getMessage());
        }

        if (odontologos.size() == 0) {
            logger.info("La lista Odontologos se encuentra vacia");
        }

        return odontologos;
    }

    @Override
    public void modificar(Odontologo odontologo, int id) {
        try {
            var c = ConnectionH2.urlOdontologos();
            var st = c.prepareStatement(UPDATE);
            st.setString(1, odontologo.getNombre());
            st.setString(2, odontologo.getApellido());
            st.setString(3, odontologo.getMatricula());
            st.setInt(4,id);
            st.executeUpdate();
            logger.info("Se modifico correctamente el Odontologo con ID: "+id);
        } catch (SQLException e) {
            logger.error("ID inexistente");
            logger.error("Metodo Modificar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        try {
            var c = ConnectionH2.urlOdontologos();
            var select = c.prepareStatement(SELECT);
            select.setInt(1,id);
            var deleteId = select.executeQuery();
            if ( deleteId != null){
                while(deleteId.next()){

                    var nombre = deleteId.getString(2);
                    var apellido = deleteId.getString(3);
                    var matricula = deleteId.getString(4);
                    logger.info("Se ha borrado correctamente el ID: "+id);
                    logger.info("Nombre: "+ nombre+ " || Apellido: "+ apellido+ " || Matricula: "+matricula);
                }

                var delete = c.prepareStatement(DELETE);
                delete.setInt(1,id);

                delete.execute();
            }else{
                logger.error("No existe el Odontologo con ID: "+id);
            }

        } catch (SQLException e) {
            logger.error("Metodo Eliminar: " + e.getMessage());
        }

    }

    @Override
    public Odontologo buscarPorId(int id) {
        try {
            var c = ConnectionH2.urlOdontologos();
            var buscar = c.prepareStatement(SELECT);
            buscar.setInt(1, id);
            var result = buscar.executeQuery();
            if ( result != null){
                if (result.next()) {
                var nombre = result.getString(2);
                var apellido = result.getString(3);
                var matricula = result.getString(4);
                logger.info("Odontologo encontrado");
                var Odont = new Odontologo(id, nombre, apellido, matricula);
                logger.info("Id: "+id+" || Nombre: "+ nombre+ " || Apellido: "+ apellido+ " || Matricula: "+matricula);
                return Odont;
                }else {
                    logger.error("No existe el Odontologo con ID: "+id);
                }

            }
        } catch (SQLException e) {
            logger.error("Metodo buscar por ID: " + e.getMessage());
        }
        return null;
    }
}
