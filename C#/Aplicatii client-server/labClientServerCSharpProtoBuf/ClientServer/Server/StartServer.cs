using lab3.repo;
using Networking;
using Protobuff;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Server
{
    class StartServer
    {
        static void Main(string[] args)
        {
            OficiuRepository oficiuRepository = new OficiuRepository();
            ParticipantRepository participantRepository = new ParticipantRepository();
            CursaRepository cursaRepository = new CursaRepository();
            ParticipantCursaRepository participantCursaRepository = new ParticipantCursaRepository();

            IServices serviceImpl = new ServerImpl(oficiuRepository, participantRepository, cursaRepository, participantCursaRepository);

            //SerialServer server = new SerialServer("127.0.0.1", 55555, serviceImpl);
            ProtoServer server = new ProtoServer("127.0.0.1", 55555, serviceImpl);
            server.Start();

            Console.WriteLine("Server started...");
            Console.ReadLine();
        }
    }

    public class SerialServer: ConcurrentServer
    {
        private IServices server;
        private ClientWorker worker;
        public SerialServer(string host, int port, IServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }

    public class ProtoServer : ConcurrentServer
    {
        private IServices server;
        private ProtoWorker worker;
        public ProtoServer(string host, int port, IServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("ProtoServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ProtoWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
