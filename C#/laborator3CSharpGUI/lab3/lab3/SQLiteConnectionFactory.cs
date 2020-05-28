using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab3
{
    public class SqliteConnectionFactory : ConnectionFactory
	{
		public override IDbConnection createConnection()
		{
            //Mono Sqlite Connection
            //String connectionString = "URI=E:\\oSimplaBazaDeDate\\ConcursMoto.db, Version=3";
            //return new SQLiteConnection(connectionString);

            // Windows Sqlite Connection, fisierul .db ar trebuie sa fie in directorul debug/bin
            String connectionString = "Data Source=E:\\oSimplaBazaDeDate\\ConcursMoto.db;Version=3";
            return new SQLiteConnection(connectionString);

		}
	}
}
