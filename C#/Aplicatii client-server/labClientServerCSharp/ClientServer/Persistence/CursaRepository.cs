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
    public class CursaRepository : ICrudRepository<string, Cursa>
    {
        private static readonly ILog log = LogManager.GetLogger("CursaRepository");
        public CursaRepository() { log.Info("Initializing CursaRepository"); }

        public void delete(string id)
        {
            log.InfoFormat("Deleting entity with id {0}", id);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "delete from Cursa where idCursa=@id";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                var result = command.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No entity deleted!");
            }
        }

        public IEnumerable<Cursa> findAll()
        {
            IDbConnection connection = DBUtils.getConnection();
            IList<Cursa> cursaList = new List<Cursa>();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select idCursa, nrPersoane, capacitateMotor from Cursa";

                using (var result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        int id = result.GetInt32(0);
                        int nrPersoane = result.GetInt32(1);
                        int capMotor = result.GetInt32(2);
                        Cursa cursa = new Cursa(nrPersoane, capMotor)
                        {
                            Id = id.ToString()
                        };
                        cursaList.Add(cursa);
                    }
                }
            }

            return cursaList;
        }

        public IEnumerable<Cursa> findAll(int capMotor)
        {
            IDbConnection connection = DBUtils.getConnection();
            IList<Cursa> cursaList = new List<Cursa>();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select idCursa, nrPersoane from Cursa where capacitateMotor=@cm";

                IDbDataParameter paramCapMotor = command.CreateParameter();
                paramCapMotor.ParameterName = "@cm";
                paramCapMotor.Value = capMotor;
                command.Parameters.Add(paramCapMotor);

                using (var result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        int id = result.GetInt32(0);
                        int nrPersoane = result.GetInt32(1);
                        Cursa cursa = new Cursa(nrPersoane, capMotor)
                        {
                            Id = id.ToString()
                        };
                        cursaList.Add(cursa);
                    }
                }
            }

            return cursaList;
        }

        public Cursa findOne(string id)
        {
            log.InfoFormat("Finding entity with id {0}", id);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select nrPersoane, capacitateMotor from Cursa where idCursa=@id";
                IDbDataParameter paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        int nrPersoane = result.GetInt32(0);
                        int capMotor = result.GetInt32(1);
                        Cursa cursa = new Cursa(nrPersoane, capMotor)
                        {
                            Id = id
                        };
                        log.InfoFormat("Exiting with entity found {0}", cursa);
                        return cursa;
                    }
                }
            }

            log.InfoFormat("No entity with id {0} found", id);
            return null;
        }

        public void save(Cursa entity)
        {
            log.InfoFormat("Saving entity {0}", entity);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "insert into Cursa values (@idCursa, @nrPersoane, @capacitateMotor)";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@idCursa";
                paramId.Value = entity.Id;
                command.Parameters.Add(paramId);

                var paramNrPers = command.CreateParameter();
                paramNrPers.ParameterName = "@nrPersoane";
                paramNrPers.Value = entity.NrPersoane;
                command.Parameters.Add(paramNrPers);

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
