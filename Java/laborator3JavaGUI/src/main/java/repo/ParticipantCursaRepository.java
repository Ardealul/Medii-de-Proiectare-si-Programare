package repo;

import javafx.util.Pair;
import domain.ParticipantCursa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantCursaRepository implements IParticipantCursaRepository {
    private static final Logger logger = LogManager.getLogger(ParticipantCursaRepository.class);
    private JdbcUtils jdbcUtils;

    public ParticipantCursaRepository(Properties properties) {
        logger.info("Initializing ParticipantCursaRepository with properties {}", properties);
        this.jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public void save(ParticipantCursa entity) {
        ParticipantCursa participantCursa = findOne(new Pair<>(entity.getId().getKey(), entity.getId().getValue()));
        if(participantCursa != null)
            throw new RepositoryException("Entity is already saved!");

        logger.info("Saving entity {}", entity);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into ParticipantCursa values (?,?)")){
            preparedStatement.setString(1, entity.getIdParticipant());
            preparedStatement.setString(2, entity.getIdCursa());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Pair<String, String> pair, ParticipantCursa entity) {
        logger.traceEntry("Updating entity with id {}", pair);
        logger.traceExit();
    }

    @Override
    public void delete(Pair<String, String> pair) {
        logger.traceEntry("Deleting entity with id {}", pair);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from ParticipantCursa where idParticipant=? and idCursa=?")){
            preparedStatement.setString(1, pair.getKey());
            preparedStatement.setString(2, pair.getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit();
    }

    @Override
    public ParticipantCursa findOne(Pair<String, String> pair) {
        logger.traceEntry("Finding entity with id {}", pair);
        Connection connection = jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from ParticipantCursa where idParticipant=? and idCursa=?")){
            preparedStatement.setString(1, pair.getKey());
            preparedStatement.setString(2, pair.getValue());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    String idParticipant = resultSet.getString("idParticipant");
                    String idCursa = resultSet.getString("idCursa");
                    ParticipantCursa participantCursa = new ParticipantCursa(idParticipant, idCursa);
                    participantCursa.setId(new Pair<>(idParticipant, idCursa));
                    logger.traceExit(participantCursa);
                    return participantCursa;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit("No entity with id {} found", pair);
        return null;
    }

    @Override
    public Iterable<ParticipantCursa> findAll() {
        logger.traceEntry();
        Connection connection = jdbcUtils.getConnection();
        List<ParticipantCursa> participantCursaList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from ParticipantCursa")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    String idParticipant = resultSet.getString("idParticipant");
                    String idCursa = resultSet.getString("idCursa");
                    ParticipantCursa participantCursa = new ParticipantCursa(idParticipant, idCursa);
                    participantCursa.setId(new Pair<>(idParticipant, idCursa));
                    participantCursaList.add(participantCursa);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("database error..." + e);
        }
        logger.traceExit(participantCursaList);
        return participantCursaList;
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection connection = jdbcUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as [size] from ParticipantCursa")){
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
