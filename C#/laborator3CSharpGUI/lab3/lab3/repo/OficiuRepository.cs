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
    public class OficiuRepository : ICrudRepository<string, Oficiu>
    {
        private static readonly ILog log = LogManager.GetLogger("OficiuRepository");
        public OficiuRepository(){ log.Info("Initializing OficiuRepository"); }

        public void delete(string id)
        {
            log.InfoFormat("Deleting entity with id {0}", id);
            IDbConnection connection = DBUtils.getConnection();

            using (var command  = connection.CreateCommand())
            {
                command.CommandText = "delete from Oficiu where idOficiu=@id";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                var result = command.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No entity deleted!");
            }
        }

        public IEnumerable<Oficiu> findAll()
        {
            IDbConnection connection = DBUtils.getConnection();
            IList<Oficiu> oficiuList = new List<Oficiu>();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select idOficiu, username, password from Oficiu";

                using (var result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        int id = result.GetInt32(0);
                        string username = result.GetString(1);
                        string password = result.GetString(2);
                        Oficiu oficiu = new Oficiu(username, password)
                        {
                            Id = id.ToString()
                        };
                        oficiuList.Add(oficiu);
                    }
                }
            }

            return oficiuList;
        }

        public Oficiu findOne(string id)
        {
            log.InfoFormat("Finding entity with id {0}", id);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select username, password from Oficiu where idOficiu=@id";
                IDbDataParameter paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        string username = result.GetString(0);
                        string password = result.GetString(1);
                        Oficiu oficiu = new Oficiu(username, password)
                        {
                            Id = id
                        };
                        log.InfoFormat("Exiting with entity found {0}", oficiu);
                        return oficiu;
                    }
                }
            }

            log.InfoFormat("No entity with id {0} found", id);
            return null;
        }

        public Oficiu findOne(string user, string pass)
        {
            log.InfoFormat("Finding entity with username {0} and password {1}", user, pass);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText =
                    "select idOficiu, username, password from Oficiu where username=@u and password=@p";
                IDbDataParameter paramUser = command.CreateParameter();
                paramUser.ParameterName = "@u";
                paramUser.Value = user;
                command.Parameters.Add(paramUser);

                IDbDataParameter paramPass = command.CreateParameter();
                paramPass.ParameterName = "@p";
                paramPass.Value = pass;
                command.Parameters.Add(paramPass);

                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        string idOficiu = result.GetInt32(0).ToString();
                        string username = result.GetString(1);
                        string password = result.GetString(2);
                        Oficiu oficiu = new Oficiu(username, password)
                        {
                            Id = idOficiu
                        };
                        log.InfoFormat("Exiting with entity found {0}", oficiu);
                        return oficiu;
                    }
                }
            }

            log.InfoFormat("No entity with username {0} and password {1}", user, pass);
            return null;
        }

        public void save(Oficiu entity)
        {
            log.InfoFormat("Saving entity {0}", entity);
            IDbConnection connection = DBUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "insert into Oficiu values (@idOficiu, @username, @password)";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@idOficiu";
                paramId.Value = entity.Id;
                command.Parameters.Add(paramId);

                var paramUser = command.CreateParameter();
                paramUser.ParameterName = "@username";
                paramUser.Value = entity.Username;
                command.Parameters.Add(paramUser);

                var paramPass = command.CreateParameter();
                paramPass.ParameterName = "@password";
                paramPass.Value = entity.Password;
                command.Parameters.Add(paramPass);

                var result = command.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No entity added!");
            }
        }
    }
}
