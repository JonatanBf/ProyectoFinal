package com.example.ProyectoIntegrador.daos;

import com.example.ProyectoIntegrador.entidades.Odontologo;
import com.example.ProyectoIntegrador.entidades.Paciente;
import com.example.ProyectoIntegrador.entidades.Turnos;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.PacienteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAOH2 implements Dao<Turnos>{

    private static final Logger logger = LogManager.getLogger(TurnoDAOH2.class);
    private final static String INSERT = "INSERT INTO Turnos (id_Odontologo, id_Paciente, fecha) VALUES (?, ?, ?);";
    private final static String SELECT_ALL = "SELECT * FROM Turnos;";
    private final static String SELECT = "SELECT * FROM Turnos WHERE id = ?;";
    private final static String UPDATE = "UPDATE Turnos SET id_Odontologo= ? , id_Paciente = ?, Fecha = ?  WHERE id = ?;";
    private final static String DELETE = "DELETE FROM Turnos WHERE id = ?;";

    @Override
    public void agregar(Turnos turnos) {
        try {
            var c = ConnectionH2.urlTurnos();
            var st = c.prepareStatement(INSERT);


            var Odontologo = turnos.getId_Odontologo();
            var Paciente = turnos.getId_Paciente();

            var oService = new OdontologoService(new OdontologoDAOH2());
            var pService = new PacienteService(new PacienteDAOH2());

            if (oService.buscarPorId(Odontologo)== null) {
            }if (pService.buscarPorId(Paciente)== null){
            }else {

                st.setInt(1,Odontologo);
                st.setInt(2,Paciente);
                st.setDate(3,Date.valueOf(turnos.getFecha()));

                st.execute();
                logger.info("Se agrego correctamente: " + " || Id_Odontologo: "+ turnos.getId_Odontologo()+ " || Id_Paciente: "+turnos.getId_Odontologo()+ " || Fecha: "+turnos.getFecha());
            }
        }catch (SQLException e) {
            logger.error("Metodo Agregar: "+ e.getMessage());
        }

    }

    @Override
    public List<Turnos> listar() {
        List<Turnos> turnosList = new ArrayList<>();
        try {
            var c = ConnectionH2.urlTurnos();
            var st = c.prepareStatement(SELECT_ALL);
            var rset = st.executeQuery();
            while (rset.next()) {
                var id = rset.getInt(1);
                var odont = rset.getInt(2);
                var pacnt = rset.getInt(3);
                var fechaA = rset.getDate(4);
                logger.info("Id: "+id+" || id_odontologo: "+odont+" || id_paciente "+pacnt+" || Fecha: "+fechaA);
                turnosList.add(new Turnos(id, odont, pacnt, fechaA.toLocalDate()));
            }
        } catch (SQLException e) {
            logger.error("Metodo listar: " + e.getMessage());
        }

        if (turnosList.size() == 0) {
            logger.debug("La lista Turnos se encuentra vacia");
        }

        return turnosList;
    }

    @Override
    public void modificar(Turnos turnos, int id) {
        try {
            var c = ConnectionH2.urlTurnos();
            var st = c.prepareStatement(UPDATE);
            st.setInt(1, turnos.getId_Odontologo());
            st.setInt(2, turnos.getId_Paciente());
            st.setDate(3, Date.valueOf(turnos.getFecha()));
            st.setInt(4,id);
            st.executeUpdate();
            logger.info("Se modifico correctamente el Turno con ID:"+id);
        } catch (SQLException e) {
            logger.error("ID inexistente");
            logger.error("Metodo Modificar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        try {
            var c = ConnectionH2.urlTurnos();
            var select = c.prepareStatement(SELECT);
            select.setInt(1,id);
            var deleteId = select.executeQuery();
            if (deleteId != null){
                while(deleteId.next()){

                    var odontologo = deleteId.getInt(2);
                    var paciente = deleteId.getInt(3);
                    var fecha = deleteId.getDate(4);
                    logger.info("Se ha borrado correctamente el ID: "+id);
                    logger.info("Id_Odontologo: "+ odontologo+ " || Id_Paciente: "+ paciente+ " || Fecha: "+fecha);
                }
                var delete = c.prepareStatement(DELETE);
                delete.setInt(1,id);

                delete.execute();
            }else{
                logger.error("No existe el Turno con ID: "+id);
            }
        } catch (SQLException e) {
            logger.error("Metodo Eliminar: " + e.getMessage());
        }
    }

    @Override
    public Turnos buscarPorId(int id) {
        try {
            var c = ConnectionH2.urlTurnos();
            var buscar = c.prepareStatement(SELECT);
            buscar.setInt(1, id);
            var result = buscar.executeQuery();
            if ( result != null){
                if (result.next()) {
                    var odontologo = result.getInt(2);
                    var paciente = result.getInt(3);
                    var fecha = result.getDate(4);
                    var turnos = new Turnos(id, odontologo, paciente, fecha.toLocalDate());
                    logger.info("Turno encontrado");
                    logger.info("ID :"+id +" || Id_Odontologo: "+ odontologo+ " || Id_Paciente: "+ paciente+ " || Fecha: "+fecha);
                    return turnos;
            }else {
                    logger.error("No existe el Turno con ID: "+id);
                }
            }
        } catch (SQLException e) {
            logger.error("Metodo buscar por ID: " + e.getMessage());
        }
        return null;
    }
}
