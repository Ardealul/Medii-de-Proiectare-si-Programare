using Google.Protobuf;
using lab3.domain;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Protobuff
{
    public class ProtoWorker : IObserver
    {
        private IServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private volatile bool connected;
        public ProtoWorker(IServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    Request request = Request.Parser.ParseDelimitedFrom(stream);
                    Response response = handleRequest(request);
                    if (response != null)
                    {
                        sendResponse(response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        private Response handleRequest(Request request)
        {
            Response response = null;
            Request.Types.Type reqType = request.Type;
            switch (reqType)
            {
                case Request.Types.Type.Login:
                    {
                        Console.WriteLine("Login request...");
                        Oficiu oficiu = ProtoUtils.getOficiu(request);
                        try
                        {
                            lock (server)
                            {
                                server.login(oficiu, this);
                            }
                            return ProtoUtils.createOkResponse();
                        }
                        catch (MyException ex)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse(ex.Message);
                        }
                    }
                case Request.Types.Type.GetOficiuByUserPass:
                    {
                        Console.WriteLine("GetOficiuByUserPass request...");
                        string username = ProtoUtils.getUsername(request);
                        string password = ProtoUtils.getPassword(request);
                        try
                        {
                            lock (server)
                            {
                                Oficiu oficiu = server.findOficiuByUserPass(username, password);
                                if (oficiu != null)
                                {
                                    return ProtoUtils.createGetOficiuByUserPassResponse(oficiu);
                                }
                                else
                                {
                                    connected = false;
                                    return ProtoUtils.createErrorResponse("Username si parola gresite!");
                                }
                            }
                        }
                        catch (MyException ex)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse("Username si parola gresite!");
                        }
                    }
                case Request.Types.Type.Logout:
                    {
                        Console.WriteLine("Logout request...");
                        Oficiu oficiu = ProtoUtils.getOficiu(request);
                        try
                        {
                            lock (server)
                            {
                                server.logout(oficiu, this);
                            }
                            connected = false;
                            return ProtoUtils.createOkResponse();
                        }
                        catch (MyException ex)
                        {
                            return ProtoUtils.createErrorResponse(ex.Message);
                        }
                    }
                case Request.Types.Type.GetAllParticipants:
                    {
                        Console.WriteLine("GetAllParticipant request...");
                        try
                        {
                            lock (server)
                            {
                                lab3.domain.Participant[] participanti = server.findAllParticipant();
                                return ProtoUtils.createGetAllParticipantResponse(participanti);
                            }
                        }
                        catch (MyException ex)
                        {
                            return ProtoUtils.createErrorResponse(ex.Message);
                        }
                    }
                case Request.Types.Type.GetAllCursa:
                    {
                        Console.WriteLine("GetAllCursa request...");
                        try
                        {
                            lock (server)
                            {
                                lab3.domain.Cursa[] curse = server.findAllCursa();
                                return ProtoUtils.createGetAllCursaResponse(curse);
                            }
                        }
                        catch (MyException ex)
                        {
                            return ProtoUtils.createErrorResponse(ex.Message);
                        }
                    }
                case Request.Types.Type.GetAllParticipantsByTeam:
                    {
                        Console.WriteLine("FindAllParticipantEchipa request...");
                        string echipa = ProtoUtils.getEchipa(request);
                        try
                        {
                            lock (server)
                            {
                                lab3.domain.Participant[] participanti = server.findAllParticipant(echipa);
                                return ProtoUtils.createGetAllParticipantResponse(participanti);
                            }
                        }
                        catch (MyException ex)
                        {
                            return ProtoUtils.createErrorResponse(ex.Message);
                        }
                    }
                case Request.Types.Type.GetEchipe:
                    {
                        Console.WriteLine("GetEchipe request...");
                        try
                        {
                            lock (server)
                            {
                                string echipe = "";
                                return ProtoUtils.createGetEchipeResponse(echipe);
                            }
                        }
                        catch (MyException ex)
                        {
                            return ProtoUtils.createErrorResponse(ex.Message);
                        }
                    }
                case Request.Types.Type.GetParticipant:
                    {
                        Console.WriteLine("GetParticipant request...");
                        string nume = ProtoUtils.getNume(request);
                        string echipa = ProtoUtils.getEchipa(request);
                        int capMotor = ProtoUtils.getCapMotor(request);
                        try
                        {
                            lock (server)
                            {
                                lab3.domain.Participant participant = server.findParticipant(nume, echipa, capMotor);
                                return ProtoUtils.createGetParticipantResponse(participant);
                            }
                        }
                        catch (MyException ex)
                        {
                            return ProtoUtils.createErrorResponse(ex.Message);
                        }
                    }

                case Request.Types.Type.GetCursa:
                    {
                        Console.WriteLine("GetCursa request...");
                        string id = ProtoUtils.getIdCursa(request);
                        try
                        {
                            lock (server)
                            {
                                lab3.domain.Cursa cursa = server.findCursa(id);
                                return ProtoUtils.createGetCursaResponse(cursa);
                            }
                        }
                        catch (MyException ex)
                        {
                            return ProtoUtils.createErrorResponse(ex.Message);
                        }
                    }

                case Request.Types.Type.AddParticipantCursa:
                    {
                        Console.WriteLine("AddParticipantCursa request...");
                        lab3.domain.ParticipantCursa participantCursa = ProtoUtils.getParticipantCursa(request);
                        try
                        {
                            lock (server)
                            {
                                server.saveParticipantCursa(participantCursa);
                            }
                            return ProtoUtils.createOkResponse();
                        }
                        catch (MyException ex)
                        {
                            return ProtoUtils.createErrorResponse(ex.Message);
                        }
                    }
                case Request.Types.Type.UpdateCursa:
                    {
                        Console.WriteLine("UpdateCursa request...");
                        string idCursa = ProtoUtils.getIdCursa(request);
                        lab3.domain.Cursa cursa = ProtoUtils.getCursa(request);
                        cursa.Id = idCursa;
                        try
                        {
                            lock (server)
                            {
                                server.deleteCursa(idCursa);
                                server.saveCursa(cursa);
                                server.submitted(server.findAllCursa());
                            }
                            return ProtoUtils.createOkResponse();
                        }
                        catch (MyException ex)
                        {
                            return ProtoUtils.createErrorResponse(ex.Message);
                        }
                    }
            }
            return response;
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            lock (stream)
            {
                response.WriteDelimitedTo(stream);
                stream.Flush();
            }
        }

        public virtual void participantCursaAdded(lab3.domain.Cursa[] curse)
        {
            Console.WriteLine("New participantCursa added...");
            try
            {
                sendResponse(ProtoUtils.createNewParticipantCursaAddedResponse());
            }
            catch (Exception e)
            {
                throw new MyException("Sending error " + e);
            }
        }
    }
}
