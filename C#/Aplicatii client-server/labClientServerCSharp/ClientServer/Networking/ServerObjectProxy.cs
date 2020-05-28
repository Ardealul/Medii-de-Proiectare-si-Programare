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
    public class ServerProxy: IServices
    {
        private string host;
        private int port;

        private IObserver client;

        private NetworkStream stream;

        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;
        public ServerProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        public virtual void login(Oficiu oficiu, IObserver client)
        {
            //initializeConnection();
            OficiuDTO oficiuDTO = DTOUtils.getDTO(oficiu);
            sendRequest(new LoginRequest(oficiuDTO));
            Response response = readResponse();
            if (response is OkResponse)
            {
                this.client = client;
                return;
            }
            if(response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                closeConnection();
                throw new MyException(error.Message);
            }
        }

        public virtual void logout(Oficiu oficiu, IObserver client)
        {
            OficiuDTO oficiuDTO = DTOUtils.getDTO(oficiu);
            sendRequest(new LogoutRequest(oficiuDTO));
            Response response = readResponse();
            closeConnection();
            if (response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                throw new MyException(error.Message);
            }
        }

        public virtual Oficiu findOficiuByUserPass(string username, string password)
        {
            initializeConnection();
            try
            {
                sendRequest(new FindOficiuByUserPassRequest(username, password));
                Response response = readResponse();
                if (response is ErrorResponse)
                {
                    ErrorResponse error = (ErrorResponse)response;
                    throw new MyException(error.Message);
                }
                FindOficiuByUserPassResponse findResponse = (FindOficiuByUserPassResponse)response;
                OficiuDTO oficiuDTO = findResponse.OficiuDTO;
                Oficiu oficiu = DTOUtils.getFromDTO(oficiuDTO);
                return oficiu;
            }
            catch(MyException ex)
            {
                closeConnection();
                throw new MyException(ex.Message);
            }
        }

        public Cursa[] findAllCursa()
        {
            sendRequest(new FindAllCursaRequest());
            Response response = readResponse();
            if(response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                throw new MyException(error.Message);
            }
            FindAllCursaResponse findResponse = (FindAllCursaResponse)response;
            CursaDTO[] cursaDTOs = findResponse.CursaDTOs;
            Cursa[] curse = DTOUtils.getFromDTO(cursaDTOs);
            return curse;
        }

        public Participant[] findAllParticipant()
        {
            sendRequest(new FindAllParticipantRequest());
            Response response = readResponse();
            if(response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                throw new MyException(error.Message);
            }
            FindAllParticipantResponse findResponse = (FindAllParticipantResponse)response;
            ParticipantDTO[] participantDTOs = findResponse.ParticipantDTOs;
            Participant[] participanti = DTOUtils.getFromDTO(participantDTOs);
            return participanti;
        }

        public Participant[] findAllParticipant(string echipa)
        {
            sendRequest(new FindAllParticipantEchipaRequest(echipa));
            Response response = readResponse();
            if(response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                throw new MyException(error.Message);
            }
            FindAllParticipantEchipaResponse findResponse = (FindAllParticipantEchipaResponse)response;
            ParticipantDTO[] participantDTOs = findResponse.ParticipantDTOs;
            Participant[] participants = DTOUtils.getFromDTO(participantDTOs);
            return participants;
        }

        public Participant findParticipant(string nume, string echipa, int capMotor)
        {
            sendRequest(new FindParticipantRequest(nume, echipa, capMotor));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                throw new MyException(error.Message);
            }
            FindParticipantResponse findResponse = (FindParticipantResponse)response;
            ParticipantDTO participantDTO = findResponse.ParticipantDTO;
            Participant participant = DTOUtils.getFromDTO(participantDTO);
            return participant;
        }

        public Cursa findCursa(string id)
        {
            sendRequest(new FindCursaRequest(id));
            Response response = readResponse();
            if(response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                throw new MyException(error.Message);
            }
            FindCursaResponse findResponse = (FindCursaResponse)response;
            CursaDTO cursaDTO = findResponse.CursaDTO;
            Cursa cursa = DTOUtils.getFromDTO(cursaDTO);
            return cursa;
        }

        public void saveParticipantCursa(ParticipantCursa participantCursa)
        {
            ParticipantCursaDTO participantCursaDTO = DTOUtils.getDTO(participantCursa);
            sendRequest(new SaveParticipantCursaRequest(participantCursaDTO));
            Response response = readResponse();
            if (response is OkResponse)
            {
                return;
            }
            if (response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                throw new MyException(error.Message);
            }
        }

        public void saveCursa(Cursa cursa)
        {
            CursaDTO cursaDTO = DTOUtils.getDTO(cursa);
            sendRequest(new SaveCursaRequest(cursaDTO));
            Response response = readResponse();
            if (response is OkResponse)
            {
                return;
            }
            if (response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                throw new MyException(error.Message);
            }
        }

        public void deleteCursa(string idCursa)
        {
            sendRequest(new DeleteCursaRequest(idCursa));
            Response response = readResponse();
            if (response is OkResponse)
            {
                return;
            }
            if (response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                throw new MyException(error.Message);
            }
        }

        public void submitted(Cursa[] curse)
        {
            CursaDTO[] cursaDTOs = DTOUtils.getDTO(curse);
            sendRequest(new NotifyRequest(cursaDTOs));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse error = (ErrorResponse)response;
                throw new MyException(error.Message);
            }
        }



        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                //output.close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new MyException("Error sending object " + e);
            }
        }

        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    //Monitor.Wait(responses); 
                    response = responses.Dequeue();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }

        private void handleUpdate(UpdateResponse updateResponse)
        {
            if(updateResponse is NewParticipantCursaResponse)
            {
                Console.WriteLine("Handling update response");
                NewParticipantCursaResponse resp = (NewParticipantCursaResponse)updateResponse;
                CursaDTO[] cursaDTOs = resp.CursaDTOs;
                Cursa[] curse = DTOUtils.getFromDTO(cursaDTOs);
                try
                {
                    client.participantCursaAdded(curse);
                }
                catch(MyException e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
        }

        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (response is UpdateResponse)
                    {
                        handleUpdate((UpdateResponse)response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue((Response)response);
                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }

            }
        }

        public Oficiu findOficiuByUserPass(string username, string password, IObserver client)
        {
            throw new NotImplementedException();
        }

        public Cursa[] findAllCursa(int capMotor)
        {
            throw new NotImplementedException();
        }
    }
}
