using lab3.domain;
using Networking.dto;
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

namespace Networking
{
    public class ClientWorker: IObserver
    {
        private IServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;
        public ClientWorker(IServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
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
                    object request = formatter.Deserialize(stream);
                    object response = handleRequest((Request)request);
                    if (response != null)
                    {
                        sendResponse((Response)response);
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
            if(request is LoginRequest)
            {
                Console.WriteLine("Login request...");
                LoginRequest loginRequest = (LoginRequest)request;
                OficiuDTO oficiuDTO = loginRequest.OficiuDTO;
                Oficiu oficiu = DTOUtils.getFromDTO(oficiuDTO);
                try
                {
                    lock(server)
                    {
                        server.login(oficiu, this);
                    }
                    return new OkResponse();
                }
                catch(MyException ex)
                {
                    connected = false;
                    return new ErrorResponse(ex.Message);
                }
            }

            if(request is LogoutRequest)
            {
                Console.WriteLine("Logout request...");
                LogoutRequest logoutRequest = (LogoutRequest)request;
                OficiuDTO oficiuDTO = logoutRequest.OficiuDTO;
                Oficiu oficiu = DTOUtils.getFromDTO(oficiuDTO);
                try
                {
                    lock (server)
                    {
                        server.logout(oficiu, this);
                    }
                    connected = false;
                    return new OkResponse();
                }
                catch(MyException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if (request is FindOficiuByUserPassRequest)
            {
                Console.WriteLine("FindOficiuByUserPass request...");
                FindOficiuByUserPassRequest findRequest = (FindOficiuByUserPassRequest)request;
                string username = findRequest.Username;
                string password = findRequest.Password;
                try
                {
                    lock (server)
                    {
                        Oficiu oficiu = server.findOficiuByUserPass(username, password);
                        if (oficiu != null)
                        {
                            OficiuDTO oficiuDTO = DTOUtils.getDTO(oficiu);
                            return new FindOficiuByUserPassResponse(oficiuDTO);
                        }
                        else
                        {
                            connected = false;
                            return new ErrorResponse("Username si parola gresite!");
                        }
                    }
                }
                catch(MyException ex)
                {
                    connected = false;
                    return new ErrorResponse("Username si parola gresite!");
                }
            }

            if(request is FindAllCursaRequest)
            {
                Console.WriteLine("FindAllCursa request...");
                try
                {
                    lock (server)
                    {
                        Cursa[] curse = server.findAllCursa();
                        CursaDTO[] cursaDTOs = DTOUtils.getDTO(curse);
                        return new FindAllCursaResponse(cursaDTOs);
                    }
                }
                catch(MyException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if(request is FindAllParticipantRequest)
            {
                Console.WriteLine("FindAllParticipant request...");
                try
                {
                    lock (server)
                    {
                        Participant[] participanti = server.findAllParticipant();
                        ParticipantDTO[] participantDTOs = DTOUtils.getDTO(participanti);
                        return new FindAllParticipantResponse(participantDTOs);
                    }
                }
                catch (MyException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if (request is FindAllParticipantEchipaRequest)
            {
                Console.WriteLine("FindAllParticipantEchipa request...");
                FindAllParticipantEchipaRequest findRequest = (FindAllParticipantEchipaRequest)request;
                string echipa = findRequest.Echipa;
                try
                {
                    lock (server)
                    {
                        Participant[] participanti = server.findAllParticipant(echipa);
                        ParticipantDTO[] participantDTOs = DTOUtils.getDTO(participanti);
                        return new FindAllParticipantEchipaResponse(participantDTOs);
                    }
                }
                catch (MyException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if(request is FindParticipantRequest)
            {
                Console.WriteLine("FindParticipant request...");
                FindParticipantRequest findRequest = (FindParticipantRequest)request;
                string nume = findRequest.Nume;
                string echipa = findRequest.Echipa;
                int capMotor = findRequest.CapMotor;
                try
                {
                    lock (server)
                    {
                        Participant participant = server.findParticipant(nume, echipa, capMotor);
                        ParticipantDTO participantDTO = DTOUtils.getDTO(participant);
                        return new FindParticipantResponse(participantDTO);
                    }
                }
                catch (MyException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if(request is FindCursaRequest)
            {
                Console.WriteLine("FindCursa request...");
                FindCursaRequest findRequest = (FindCursaRequest)request;
                string id = findRequest.Id;
                try
                {
                    lock (server)
                    {
                        Cursa cursa = server.findCursa(id);
                        CursaDTO cursaDTO = DTOUtils.getDTO(cursa);
                        return new FindCursaResponse(cursaDTO);
                    }
                }
                catch (MyException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if(request is SaveParticipantCursaRequest)
            {
                Console.WriteLine("SaveParticipantCursa request...");
                SaveParticipantCursaRequest saveRequest = (SaveParticipantCursaRequest)request;
                ParticipantCursaDTO participantCursaDTO = saveRequest.ParticipantCursaDTO;
                ParticipantCursa participantCursa = DTOUtils.getFromDTO(participantCursaDTO);
                try
                {
                    lock (server)
                    {
                        server.saveParticipantCursa(participantCursa);
                    }
                    return new OkResponse();
                }
                catch (MyException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if(request is DeleteCursaRequest)
            {
                Console.WriteLine("DeleteCursa request...");
                DeleteCursaRequest deleteRequest = (DeleteCursaRequest)request;
                string id = deleteRequest.Id;
                try
                {
                    lock (server)
                    {
                        server.deleteCursa(id);
                    }
                    return new OkResponse();
                }
                catch(MyException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if(request is SaveCursaRequest)
            {
                Console.WriteLine("SaveCursa request...");
                SaveCursaRequest saveRequest = (SaveCursaRequest)request;
                CursaDTO cursaDTO = saveRequest.CursaDTO;
                Cursa cursa = DTOUtils.getFromDTO(cursaDTO);
                try
                {
                    lock (server)
                    {
                        server.saveCursa(cursa);
                    }
                    return new OkResponse();
                }
                catch (MyException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            if (request is NotifyRequest)
            {
                Console.WriteLine("Notify request...");
                NotifyRequest notifyRequest = (NotifyRequest)request;
                CursaDTO[] cursaDTOs = notifyRequest.CursaDTOs;
                Cursa[] curse = DTOUtils.getFromDTO(cursaDTOs);
                try
                {
                    lock (server)
                    {
                        server.submitted(curse);
                    }
                    return new OkResponse();
                }
                catch (MyException ex)
                {
                    return new ErrorResponse(ex.Message);
                }
            }

            return response;
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            formatter.Serialize(stream, response);
            stream.Flush();
        }

        public virtual void participantCursaAdded(Cursa[] curse)
        {
            Console.WriteLine("New participantCursa added...");
            CursaDTO[] cursaDTOs = DTOUtils.getDTO(curse);
            try
            {
                sendResponse(new NewParticipantCursaResponse(cursaDTOs));
            }
            catch (Exception e)
            {
                throw new MyException("Sending error " + e);
            }
        }
    }
}
