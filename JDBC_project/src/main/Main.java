package main;

import connection.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Hello JDBC");
        ConnectionFactory connectionFactory = createConnectionFactory();
        //ITT vannak a menűpontok, ezt kell menűsíteni
        try(Connection connection = connectionFactory.getConnection()){
            new Main().run(connection);
        }
    }

    private void run(Connection connection) {

    }

    private static ConnectionFactory createConnectionFactory() throws IOException {
        Properties dbProperies = new Properties ();
        dbProperies.load(Main.class.getResourceAsStream("/database.properties"));

        ConnectionFactory connectionFactory = new ConnectionFactory(dbProperies);
        return connectionFactory;
    }
}
