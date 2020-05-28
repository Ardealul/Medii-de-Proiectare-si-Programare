package repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static final Logger logger = LogManager.getLogger(JdbcUtils.class);
    private Properties properties;
    private Connection connection = null;

    public JdbcUtils(Properties properties) {
        this.properties = properties;
    }

    private Connection getNewConnection(){
        logger.traceEntry();
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String pass = properties.getProperty("jdbc.pass");
        logger.info("trying to connect to database {}", url);
        logger.info("user: {}", user);
        logger.info("pass: {}", pass);
        Connection connection = null;
        try {
            if(user != null && pass != null)
                connection = DriverManager.getConnection(url, user, pass);
            else
                connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("error getting connection..." + e);
        }
        return connection;
    }

    public Connection getConnection(){
        logger.traceEntry();
        try {
            if(connection == null || connection.isClosed())
                connection = getNewConnection();
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
        return connection;
    }
}
