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
    public class ParticipantRepository : ICrudRepository<string, Participant>
    {
        private static readonly ILog log = LogManager.GetLogger("ParticipantRepository");
        public ParticipantRepository() { log.Info("Initializing ParticipantRepository"); }
        public void delete(string id)
        {
            log.InfoFormat("Deleting entity with id {0}", id);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "delete from Participant where idParticipant=@id";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                var result = command.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No entity deleted!");
            }
        }

        public IEnumerable<Participant> findAll()
        {
            IDbConnection connection = DBUtils.getConnection();
            IList<Participant> participantList = new List<Participant>();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select idParticipant, nume, echipa, capacitateMotor from Participant";

                using (var result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        int id = result.GetInt32(0);
                        string nume = result.GetString(1);
                        string echipa = result.GetString(2);
                        int capMotor = result.GetInt32(3);
                        Participant participant = new Participant(nume, echipa, capMotor)
                        {
                            Id = id.ToString()
                        };
                        participantList.Add(participant);
                    }
                }
            }

            return participantList;
        }

        public IEnumerable<Participant> findAll(string echipa)
        {
            IDbConnection connection = DBUtils.getConnection();
            IList<Participant> participantList = new List<Participant>();

            using (var command = connection.CreateCommand())
            {
                command.CommandText =
                    "select idParticipant, nume, capacitateMotor from Participant where echipa=@echipa";

                IDbDataParameter paramEchipa = command.CreateParameter();
                paramEchipa.ParameterName = "@echipa";
                paramEchipa.Value = echipa;
                command.Parameters.Add(paramEchipa);

                using (var result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        int id = result.GetInt32(0);
                        string nume = result.GetString(1);
                        int capMotor = result.GetInt32(2);
                        Participant participant = new Participant(nume, echipa, capMotor)
                        {
                            Id = id.ToString()
                        };
                        participantList.Add(participant);
                    }
                }
            }
            return participantList;
        }

        public Participant findOne(string id)
        {
            log.InfoFormat("Finding entity with id {0}", id);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select nume, echipa, capacitateMotor from Participant where idParticipant=@id";
                IDbDataParameter paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        string nume = result.GetString(0);
                        string echipa = result.GetString(1);
                        int capMotor = result.GetInt32(2);
                        Participant participant = new Participant(nume, echipa, capMotor)
                        {
                            Id = id
                        };
                        log.InfoFormat("Exiting with entity found {0}", participant);
                        return participant;
                    }
                }
            }

            log.InfoFormat("No entity with id {0} found", id);
            return null;
        }

        public Participant findOne(string n, string ech, int capM)
        {
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText =
                    "select idParticipant from Participant where nume=@n and echipa=@e and capacitateMotor=@cm";
                IDbDataParameter paramNume = command.CreateParameter();
                paramNume.ParameterName = "@n";
                paramNume.Value = n;
                command.Parameters.Add(paramNume);

                IDbDataParameter paramEchipa = command.CreateParameter();
                paramEchipa.ParameterName = "@e";
                paramEchipa.Value = ech;
                command.Parameters.Add(paramEchipa);

                IDbDataParameter paramCapM = command.CreateParameter();
                paramCapM.ParameterName = "@cm";
                paramCapM.Value = capM;
                command.Parameters.Add(paramCapM);

                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        int id = result.GetInt32(0);
                        Participant participant = new Participant(n, ech, capM)
                        {
                            Id = id.ToString()
                        };
                        log.InfoFormat("Exiting with entity found {0}", participant);
                        return participant;
                    }
                }
            }
            return null;
        }

        public void save(Participant entity)
        {
            log.InfoFormat("Saving entity {0}", entity);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "insert into Participant values (@idParticipant, @nume, @echipa, @capacitateMotor)";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@idParticipant";
                paramId.Value = entity.Id;
                command.Parameters.Add(paramId);

                var paramNume = command.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = entity.Nume;
                command.Parameters.Add(paramNume);

                var paramEchipa = command.CreateParameter();
                paramEchipa.ParameterName = "@echipa";
                paramEchipa.Value = entity.Echipa;
                command.Parameters.Add(paramEchipa);

                var paramCapM = command.CreateParameter();
                paramCapM.ParameterName = "@capacitateMotor";
                paramCapM.Value = entity.CapacitateMotor;
                command.Parameters.Add(paramCapM);

                var result = command.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No entity added!");
            }
        }
    }
}
