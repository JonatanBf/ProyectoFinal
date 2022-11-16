package com.example.ProyectoIntegrador.daos;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionH2 {

    private final static String odontologos = "jdbc:h2:~/ProyectoIntegrador;INIT=RUNSCRIPT FROM 'Odontologos.sql'";

    private final static String pacientes = "jdbc:h2:~/ProyectoIntegrador;INIT=RUNSCRIPT FROM 'Pacientes.sql'";

    private final static String turnos = "jdbc:h2:~/ProyectoIntegrador;INIT=RUNSCRIPT FROM 'Turnos.sql'";
    public static java.sql.Connection urlOdontologos() throws SQLException {
        return DriverManager.getConnection(odontologos);
    }
    public static java.sql.Connection urlPacientes() throws SQLException {
        return DriverManager.getConnection(pacientes);
    }

    public static java.sql.Connection urlTurnos() throws SQLException {
        return DriverManager.getConnection(turnos);
    }
}
