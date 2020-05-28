using lab3.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using log4net;
using System.Data;

namespace lab3.repo
{
    public class ParticipantCursaRepository : ICrudRepository<Tuple<string, string>, ParticipantCursa>
    {
        private static readonly ILog log = LogManager.GetLogger("ParticipantCursaRepository");
        public ParticipantCursaRepository() { log.Info("Initializing ParticipantCursaRepository"); }
        public void delete(Tuple<string, string> id)
        {
            log.InfoFormat("Deleting entity with id [{0};{1}]", id.Item1, id.Item2);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "delete from ParticipantCursa where idParticipant=@idP and idCursa=@idC";

                var paramIdP = command.CreateParameter();
                paramIdP.ParameterName = "@idP";
                paramIdP.Value = id.Item1;
                command.Parameters.Add(paramIdP);

                var paramIdC = command.CreateParameter();
                paramIdC.ParameterName = "@idC";
                paramIdC.Value = id.Item2;
                command.Parameters.Add(paramIdC);

                var result = command.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No entity deleted!");
            }
        }

        public IEnumerable<ParticipantCursa> findAll()
        {
            IDbConnection connection = DBUtils.getConnection();
            IList<ParticipantCursa> participantCursaList = new List<ParticipantCursa>();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select idParticipant, idCursa from ParticipantCursa";

                using (var result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        int idP = result.GetInt32(0);
                        int idC = result.GetInt32(1);
                        ParticipantCursa participantCursa = new ParticipantCursa(idP.ToString(), idC.ToString())
                        {
                            Id = new Tuple<string, string>(idP.ToString(), idC.ToString())
                        };
                        participantCursaList.Add(participantCursa);
                    }
                }
            }

            return participantCursaList;
        }

        public ParticipantCursa findOne(Tuple<string, string> id)
        {
            log.InfoFormat("Finding entity with id [{0};{1}]", id.Item1, id.Item2);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText =
                    "select idParticipant, idCursa from ParticipantCursa where idParticipant=@idP and idCursa=@idC";
                IDbDataParameter paramIdP = command.CreateParameter();
                paramIdP.ParameterName = "@idP";
                paramIdP.Value = id.Item1;
                command.Parameters.Add(paramIdP);

                IDbDataParameter paramIdC = command.CreateParameter();
                paramIdC.ParameterName = "@idC";
                paramIdC.Value = id.Item2;
                command.Parameters.Add(paramIdC);

                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        int idP = result.GetInt32(0);
                        int idC = result.GetInt32(1);
                        ParticipantCursa participantCursa = new ParticipantCursa(idP.ToString(), idC.ToString())
                        {
                            Id = new Tuple<string, string>(idP.ToString(), idC.ToString())
                        };
                        log.InfoFormat("Exiting with entity found {0}", participantCursa);
                        return participantCursa;
                    }
                }
            }

            log.InfoFormat("No entity with id [{0};{1}] found", id);
            return null;
        }

        public void save(ParticipantCursa entity)
        {
            if(findOne(entity.Id) != null)
            {
                throw new Exception("Entity already saved!");
            }

            log.InfoFormat("Saving entity {0}", entity);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "insert into ParticipantCursa values (@idParticipant, @idCursa)";

                var paramIdP = command.CreateParameter();
                paramIdP.ParameterName = "@idParticipant";
                paramIdP.Value = entity.Id.Item1;
                command.Parameters.Add(paramIdP);

                var paramIdC = command.CreateParameter();
                paramIdC.ParameterName = "@idCursa";
                paramIdC.Value = entity.Id.Item2;
                command.Parameters.Add(paramIdC);

                var result = command.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No entity added!");
            }
        }
    }
}
