package repo;

import domain.Oficiu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OficiuRepository implements IOficiuRepository{
    private static final Logger logger = LogManager.getLogger(OficiuRepository.class);
    private JdbcUtils jdbcUtils;

    public OficiuRepository(Properties properties) {
        logger.info("Initializing OficiuRepository with properties: {}", properties);
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void save(Oficiu entity) {
        logger.traceEntry("Saving entity {}", entity);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into Oficiu values (?,?,?)")){
            preparedStatement.setString(1, entity.getId());
            preparedStatement.setString(2, entity.getUsername());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
    }

    @Override
    public void update(String s, Oficiu entity) {
        logger.traceEntry("Updating entity with id {}", s);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("update Oficiu set username=?, password=? where idOficiu=?")){
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, s);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(String s) {
        logger.traceEntry("Deleting entity with id {}", s);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from Oficiu where idOficiu=?")){
            preparedStatement.setString(1, s);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
    }

    @Override
    public Oficiu findOne(String s) {
        logger.traceEntry("Finding entity with id {}", s);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from Oficiu where idOficiu=?")){
            preparedStatement.setString(1, s);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    String id = resultSet.getString("idOficiu");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Oficiu oficiu = new Oficiu(username, password);
                    oficiu.setId(id);
                    logger.traceExit(oficiu);
                    return oficiu;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit("No entity with id {} found", s);
        return null;
    }

    public Oficiu findByUserPass(String username, String password){
        logger.traceEntry("Finding entity with username {} and password {}", username, password);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from Oficiu where username=? and password=?")){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    String id = resultSet.getString("idOficiu");
                    String user = resultSet.getString("username");
                    String pass = resultSet.getString("password");
                    Oficiu oficiu = new Oficiu(user, pass);
                    oficiu.setId(id);
                    logger.traceExit(oficiu);
                    return oficiu;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit("No entity with username " + username + " and password " + password);
        return null;
    }

    @Override
    public Iterable<Oficiu> findAll() {
        logger.traceEntry();
        Connection connection = jdbcUtils.getConnection();
        List<Oficiu> oficiuList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from Oficiu")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    String id = resultSet.getString("idOficiu");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Oficiu oficiu = new Oficiu(username, password);
                    oficiu.setId(id);
                    oficiuList.add(oficiu);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit(oficiuList);
        return oficiuList;
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection connection = jdbcUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as [size] from Oficiu")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    logger.traceExit(resultSet.getInt("size"));
                    return resultSet.getInt("size");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        return 0;
    }
}
