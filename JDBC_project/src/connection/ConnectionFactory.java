package connection;

import java.util.Properties;
import static org.apache.commons.lang3.Validate.notNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String KEY_DB_URL = "db.url";
    private static final String KEY_DB_PASSWORD = "db.password";
    private static final String KEY_DB_USER = "db.user";

    private String connectionUrl;
    private Properties connectionProperties = new Properties();

    public ConnectionFactory (Properties properties) {
        this.connectionUrl = notNull(properties.getProperty(KEY_DB_URL));
        this.connectionProperties.setProperty("user", notNull(properties.getProperty(KEY_DB_USER)));
        this.connectionProperties.setProperty("password", notNull(properties.getProperty(KEY_DB_PASSWORD)));

    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(connectionUrl, connectionProperties);
    }
}
