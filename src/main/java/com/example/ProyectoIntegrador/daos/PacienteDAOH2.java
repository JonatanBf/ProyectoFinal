package com.example.ProyectoIntegrador.daos;

import com.example.ProyectoIntegrador.entidades.Paciente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOH2 implements Dao<Paciente>{

    private static final Logger logger = LogManager.getLogger(PacienteDAOH2.class);
    private final static String INSERT = "INSERT INTO Pacientes (nombre, apellido, domicilio, dni, fechaAlta) VALUES (?, ?, ?, ?, ?);";
    private final static String SELECT_ALL = "SELECT * FROM Pacientes;";
    private final static String SELECT = "SELECT * FROM Pacientes WHERE id = ?;";
    private final static String UPDATE = "UPDATE Pacientes SET nombre = ?, apellido = ?, domicilio = ? , dni = ?, fechaAlta=? WHERE id = ?;";
    private final static String DELETE = "DELETE FROM Pacientes WHERE id = ?;";
    @Override
    public void agregar(Paciente paciente) {
        try {
            var c = ConnectionH2.urlPacientes();
            var st = c.prepareStatement(INSERT);
            st.setString(1, paciente.getNombre());
            st.setString(2, paciente.getApellido());
            st.setString(3, paciente.getDomicilio());
            st.setString(4, paciente.getDni());
            st.setDate(5, Date.valueOf(paciente.getFechaAlta()));
            st.execute();
            logger.info("Se agrego correctamente: " + paciente.getApellido() +" "+paciente.getNombre()+" || Domicilio: "+paciente.getDomicilio()+" || DNI: "+paciente.getDni()+ " || FechaAlta: "+ paciente.getFechaAlta());
        }catch (SQLException e) {
            logger.error("Metodo Agregar: "+ e.getMessage());
        }
    }

    @Override
    public List<Paciente> listar() {
        List<Paciente> pacientes = new ArrayList<>();
        try {
            var c = ConnectionH2.urlPacientes();
            var st = c.prepareStatement(SELECT_ALL);
            var rset = st.executeQuery();
            while (rset.next()) {
                var id = rset.getInt(1);
                var nombre = rset.getString(2);
                var apellido = rset.getString(3);
                var domicilio = rset.getString(4);
                var dni = rset.getString(5);
                var fechaAlta = rset.getDate(6);
                logger.info("Id: "+id+" || Nombre: "+nombre+" || Apellido: "+apellido+" || Domicilio: "+domicilio+" || DNI: "+dni+" || FechaAlta: "+fechaAlta);
                pacientes.add(new Paciente(id, nombre, apellido, domicilio,dni, fechaAlta.toLocalDate()));
            }
        } catch (SQLException e) {
            logger.error("Metodo listar: " + e.getMessage());
        }

        if (pacientes.size() == 0) {
            logger.debug("La lista Pacientes se encuentra vacia");
        }

        return pacientes;
    }

    @Override
    public void modificar(Paciente paciente, int id) {
        try {
            var c = ConnectionH2.urlPacientes();
            var st = c.prepareStatement(UPDATE);
            st.setString(1, paciente.getNombre());
            st.setString(2, paciente.getApellido());
            st.setString(3, paciente.getDomicilio());
            st.setString(4, paciente.getDni());
            st.setDate(5, Date.valueOf(paciente.getFechaAlta()));
            st.setInt(6,id);
            st.executeUpdate();
            logger.info("Se modifico correctamente el Paciente con ID:"+id);
        } catch (SQLException e) {
            logger.error("ID inexistente");
            logger.error("Metodo Modificar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        try {
            var c = ConnectionH2.urlPacientes();
            var select = c.prepareStatement(SELECT);
            select.setInt(1,id);
            var deleteId = select.executeQuery();
            if (deleteId != null){
                while(deleteId.next()){

                    var nombre = deleteId.getString(2);
                    var apellido = deleteId.getString(3);
                    var domicilio = deleteId.getString(4);
                    var dni = deleteId.getString(5);
                    var fechaAlta = deleteId.getDate(6);
                    logger.info("Se ha borrado correctamente el ID: "+id);
                    logger.info("Nombre: "+ nombre+ " || Apellido: "+ apellido+ " || Domicilio: "+domicilio+" || DNI: "+dni+ " || FechaAlta: "+fechaAlta);
                }
                var delete = c.prepareStatement(DELETE);
                delete.setInt(1,id);

                delete.execute();
            }else{
                logger.error("No existe el Paciente con ID: "+id);
            }

        } catch (SQLException e) {
            logger.error("Metodo Eliminar: " + e.getMessage());
        }

    }

    @Override
    public Paciente buscarPorId(int id) {
        try {
            var c = ConnectionH2.urlPacientes();
            var buscar = c.prepareStatement(SELECT);
            buscar.setInt(1, id);
            var result = buscar.executeQuery();
            if ( result != null){
                if (result.next()) {
                    var nombre = result.getString(2);
                    var apellido = result.getString(3);
                    var domicilio = result.getString(4);
                    var dni = result.getString(5);
                    var fechaAlta = result.getDate(6);
                    logger.info("Paciente encontrado");
                    var paciente = new Paciente(id, nombre, apellido, domicilio,dni, fechaAlta.toLocalDate());
                    logger.info("Id: "+id+" || Nombre: "+ nombre+ " || Apellido: "+ apellido+ " || Domicilio: "+domicilio+" || DNI: "+dni+" || FechaAlta: "+fechaAlta);
                    return paciente;
                }else {
                    logger.error("No existe el Paciente con ID: "+id);
                }
            }
        } catch (SQLException e) {
            logger.error("No existe el Paciente con ID: "+id);
            logger.error("Metodo buscar por ID: " + e.getMessage());
        }
        return null;
    }
}
