using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data;
using lab3;
using Microsoft.Data.Sqlite;
using System.Data.SQLite;

namespace lab3.repo
{
    public static class DBUtils
    {
        private static IDbConnection instance = null;

        public static IDbConnection getConnection()
        {
            if(instance == null || instance.State == ConnectionState.Closed)
            {
                instance = getNewConnection();
                instance.Open();
            }
            return instance;
        }

        private static IDbConnection getNewConnection()
        {
            //return SqliteConnectionFactory.getInstance().createConnection();
            //String connectionString = "Data Source=E:\\oSimplaBazaDeDate\\ConcursMoto.db;Version=3";
            String connectionString =
                System.Configuration.ConfigurationManager.ConnectionStrings["CursaDB"].ConnectionString;
            Console.WriteLine(connectionString);
            return new SQLiteConnection(connectionString);
        }
    }
}
