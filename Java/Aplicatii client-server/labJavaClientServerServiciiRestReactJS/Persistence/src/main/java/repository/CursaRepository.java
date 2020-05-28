package repository;

import model.Cursa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CursaRepository implements ICursaRepository {
    private static final Logger logger = LogManager.getLogger(CursaRepository.class);
    private JdbcUtils jdbcUtils;

    public CursaRepository(Properties properties) {
        logger.info("Initializing CursaRepository with properties {}", properties);
        this.jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void save(Cursa entity) {
        logger.traceEntry("Saving entity {}", entity);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into Cursa values (?,?,?)")){
            preparedStatement.setString(1, entity.getId());
            preparedStatement.setInt(2, entity.getNrPersoane());
            preparedStatement.setInt(3, entity.getCapacitateMotor());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
    }

    @Override
    public void update(String s, Cursa entity) {
        logger.traceEntry("Updating entity with id {}", s);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("update Cursa set nrPersoane=?, capacitateMotor=? where idCursa=?")){
            preparedStatement.setInt(1, entity.getNrPersoane());
            preparedStatement.setInt(2, entity.getCapacitateMotor());
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

        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from Cursa where idCursa=?")){
            preparedStatement.setString(1, s);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
    }

    @Override
    public Cursa findOne(String s) {
        logger.info("Finding entity with id {}", s);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from Cursa where idCursa=?")){
            preparedStatement.setString(1, s);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    String id = resultSet.getString("idCursa");
                    Integer nrPersoane = resultSet.getInt("nrPersoane");
                    Integer capacitateMotor = resultSet.getInt("capacitateMotor");
                    Cursa cursa = new Cursa(nrPersoane, capacitateMotor);
                    cursa.setId(id);
                    logger.traceExit(cursa);
                    return cursa;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }

        logger.traceExit("No entity with id {} found", s);
        return null;
    }

    @Override
    public Iterable<Cursa> findAll() {
        logger.traceEntry();
        Connection connection = jdbcUtils.getConnection();
        List<Cursa> cursaList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from Cursa")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    String id = resultSet.getString("idCursa");
                    Integer nrPersoane = resultSet.getInt("nrPersoane");
                    Integer capacitateMotor = resultSet.getInt("capacitateMotor");
                    Cursa cursa = new Cursa(nrPersoane, capacitateMotor);
                    cursa.setId(id);
                    cursaList.add(cursa);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
        return cursaList;
    }

    public Iterable<Cursa> findAll(Integer capMotor){
        logger.traceEntry();
        Connection connection = jdbcUtils.getConnection();
        List<Cursa> cursaList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from Cursa where capacitateMotor=?")){
            preparedStatement.setInt(1, capMotor);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    String id = resultSet.getString("idCursa");
                    Integer nrPersoane = resultSet.getInt("nrPersoane");
                    Integer capacitateMotor = resultSet.getInt("capacitateMotor");
                    Cursa cursa = new Cursa(nrPersoane, capacitateMotor);
                    cursa.setId(id);
                    cursaList.add(cursa);
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
        return cursaList;
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as [size] from Cursa")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    logger.traceExit(resultSet.getInt("size"));
                    return resultSet.getInt("size");
                }
            }
        }
        catch (SQLException e){
            logger.error(e);
            System.out.println("database error..." + e);
        }
        return 0;
    }
}
