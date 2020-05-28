package repository;

import model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantRepository implements IParticipantRepository {
    private static final Logger logger = LogManager.getLogger(ParticipantRepository.class);
    private JdbcUtils jdbcUtils;

    public ParticipantRepository(Properties properties) {
        logger.info("Initializing ParticipantRepository with properties {}", properties);
        this.jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void save(Participant entity) {
        logger.info("Saving entity {}", entity);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into Participant values (?,?,?,?)")){
            preparedStatement.setString(1, entity.getId());
            preparedStatement.setString(2, entity.getNume());
            preparedStatement.setString(3, entity.getEchipa());
            preparedStatement.setInt(4, entity.getCapacitateMotor());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
    }

    @Override
    public void update(String s, Participant entity) {
        logger.traceEntry("Updating entity with id {}", s);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("update Participant set nume=?, echipa=?, capacitateMotor=? where idParticipant=?")){
            preparedStatement.setString(1, entity.getNume());
            preparedStatement.setString(2, entity.getEchipa());
            preparedStatement.setInt(3, entity.getCapacitateMotor());
            preparedStatement.setString(4, entity.getId());
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

        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from Participant where idParticipant=?")){
            preparedStatement.setString(1, s);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
    }

    @Override
    public Participant findOne(String s) {
        logger.traceEntry("Finding entity with id {}", s);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from Participant where idParticipant=?")){
            preparedStatement.setString(1, s);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    String id = resultSet.getString("idParticipant");
                    String nume = resultSet.getString("nume");
                    String echipa = resultSet.getString("echipa");
                    Integer capacitateMotor = resultSet.getInt("capacitateMotor");
                    Participant participant = new Participant(nume, echipa, capacitateMotor);
                    participant.setId(id);
                    logger.traceExit(participant);
                    return participant;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit("No entity with id {} found", s);
        return null;
    }

    public Participant findOneByNumeEchipaCapMotor(String n, String ech, Integer cap){
        logger.traceEntry("Finding entity with name " + n + ", team " + ech + ", capacitateMotor " + cap.toString());
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from Participant where nume=? and echipa=? and capacitateMotor=?")){
            preparedStatement.setString(1, n);
            preparedStatement.setString(2, ech);
            preparedStatement.setInt(3, cap);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    String id = resultSet.getString("idParticipant");
                    String nume = resultSet.getString("nume");
                    String echipa = resultSet.getString("echipa");
                    Integer capacitateMotor = resultSet.getInt("capacitateMotor");
                    Participant participant = new Participant(nume, echipa, capacitateMotor);
                    participant.setId(id);
                    logger.traceExit(participant);
                    return participant;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit("No entity with name " + n + ", team " + ech + ", capacitateMotor " + cap.toString());
        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        logger.traceEntry();
        Connection connection = jdbcUtils.getConnection();
        List<Participant> participantList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from Participant")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    String id = resultSet.getString("idParticipant");
                    String nume = resultSet.getString("nume");
                    String echipa = resultSet.getString("echipa");
                    Integer capacitateMotor = resultSet.getInt("capacitateMotor");
                    Participant participant = new Participant(nume, echipa, capacitateMotor);
                    participant.setId(id);
                    participantList.add(participant);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit(participantList);
        return participantList;
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection connection = jdbcUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as [size] from Participant")){
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
