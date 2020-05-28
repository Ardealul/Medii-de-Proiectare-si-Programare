using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using lab3.domain;
using Protobuff;

namespace Protobuff
{
    static class ProtoUtils
    {
        //REQUESTS
        //public static Request createLoginRequest(Oficiu oficiu)
        //{
        //    OficiuDTO oficiuDTO = new OficiuDTO
        //    {
        //        IdOficiu = oficiu.Id,
        //        Username = oficiu.Username,
        //        Password = oficiu.Password
        //    };
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.Login,
        //        OficiuDTO = oficiuDTO
        //    };
        //    return request;
        //}

        //public static Request createLogoutRequest(Oficiu oficiu)
        //{
        //    OficiuDTO oficiuDTO = new OficiuDTO
        //    {
        //        IdOficiu = oficiu.Id,
        //        Username = oficiu.Username,
        //        Password = oficiu.Password
        //    };
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.Logout,
        //        OficiuDTO = oficiuDTO
        //    };
        //    return request;
        //}

        //public static Request createFindOficiuByUserPass(string username, string password)
        //{
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.FindOficiuByUserPass,
        //        Username = username,
        //        Password = password
        //    };
        //    return request;
        //}

        //public static Request createFindAllParticipant()
        //{
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.FindAllParticipant
        //    };
        //    return request;
        //}

        //public static Request createFindAllCursa()
        //{
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.FindAllCursa
        //    };
        //    return request;
        //}

        //public static Request createFindAllParticipantEchipa(string echipa)
        //{
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.FindAllParticipantEchipa,
        //        Echipa = echipa
        //    };
        //    return request;
        //}

        //public static Request createFindParticipant(string nume, string echipa, int capMotor)
        //{
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.FindParticipant,
        //        Nume = nume,
        //        Echipa = echipa,
        //        CapMotor = capMotor
        //    };
        //    return request;
        //}

        //public static Request createFindCursa(string idCursa)
        //{
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.FindCursa,
        //        IdCursa = idCursa
        //    };
        //    return request;
        //}

        //public static Request createSaveParticipantCursa(ParticipantCursa participantCursa)
        //{
        //    ParticipantCursaDTO participantCursaDTO = new ParticipantCursaDTO
        //    {
        //        IdParticipant = participantCursa.IdParticipant,
        //        IdCursa = participantCursa.IdCursa
        //    };
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.SaveParticipantCursa,
        //        ParticipantCursaDTO = participantCursaDTO
        //    };
        //    return request;
        //}

        //public static Request createDeleteCursa(string idCursa)
        //{
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.DeleteCursa,
        //        IdCursa = idCursa
        //    };
        //    return request;
        //}

        //public static Request createSaveCursa(Cursa cursa)
        //{
        //    CursaDTO cursaDTO = new CursaDTO
        //    {
        //        IdCursa = cursa.Id,
        //        NrPersoane = cursa.NrPersoane,
        //        CapMotor = cursa.CapacitateMotor
        //    };
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.SaveCursa,
        //        CursaDTO = cursaDTO
        //    };
        //    return request;
        //}

        //public static Request createNotify(Cursa[] curse)
        //{
        //    Request request = new Request
        //    {
        //        Type = Request.Types.Type.Notify,
        //    };
        //    foreach (Cursa cursa in curse)
        //    {
        //        CursaDTO cursaDTO = new CursaDTO
        //        {
        //            IdCursa = cursa.Id,
        //            NrPersoane = cursa.NrPersoane,
        //            CapMotor = cursa.CapacitateMotor
        //        };
        //        request.CurseDTO.Add(cursaDTO);
        //    }
        //    return request;
        //}

        //RESPONSES
        public static Response createOkResponse()
        {
            Response response = new Response
            {
                Type = Response.Types.Type.Ok
            };
            return response;
        }

        public static Response createErrorResponse(string eroare)
        {
            Response response = new Response
            {
                Type = Response.Types.Type.Error,
                Eroare = eroare
            };
            return response;
        }

        public static Response createGetOficiuByUserPassResponse(Oficiu oficiu)
        {
            OficiuDTO oficiuDTO = new OficiuDTO
            {
                IdOficiu = oficiu.Id,
                Username = oficiu.Username,
                Password = oficiu.Password
            };
            Response response = new Response
            {
                Type = Response.Types.Type.GetOficiuByUserPass,
                OficiuDTO = oficiuDTO
            };
            return response;
        }

        public static Response createGetAllParticipantResponse(lab3.domain.Participant[] participants)
        {
            Response response = new Response
            {
                Type = Response.Types.Type.GetAllParticipants,
            };
            foreach (lab3.domain.Participant participant in participants)
            {
                Participant participantDTO = new Participant
                {
                    Nume = participant.Nume,
                    Echipa = participant.Echipa,
                    CapMotor = participant.CapacitateMotor
                };
                response.Participanti.Add(participantDTO);
            }
            return response;
        }

        public static Response createGetAllCursaResponse(lab3.domain.Cursa[] curse)
        {
            Response response = new Response
            {
                Type = Response.Types.Type.GetAllCursa,
            };
            foreach (lab3.domain.Cursa cursa in curse)
            {
                CursaDTO cursaDTO = new CursaDTO
                {
                    IdCursa = cursa.Id,
                    NrPersoane = cursa.NrPersoane,
                    CapMotor = cursa.CapacitateMotor
                };
                response.CurseDTO.Add(cursaDTO);
            }
            return response;
        }

        public static Response createGetEchipeResponse(string echipe)
        {
            Response response = new Response
            {
                Type = Response.Types.Type.GetEchipe,
                Echipe = echipe
            };
            return response;
        }

        public static Response createGetParticipantResponse(lab3.domain.Participant participant)
        {
            ParticipantDTO participantDTO = new ParticipantDTO
            {
                IdParticipant = participant.Id,
                Nume = participant.Nume,
                Echipa = participant.Echipa,
                CapMotor = participant.CapacitateMotor
            };
            Response response = new Response
            {
                Type = Response.Types.Type.GetParticipant,
                ParticipantDTO = participantDTO
            };
            return response;
        }

        public static Response createGetCursaResponse(lab3.domain.Cursa cursa)
        {
            Cursa cursaDTO = new Cursa
            {
                NrPersoane = cursa.NrPersoane,
                CapMotor = cursa.CapacitateMotor
            };
            Response response = new Response
            {
                Type = Response.Types.Type.GetCursa,
                Cursa = cursaDTO
            };
            return response;
        }

        public static Response createNewParticipantCursaAddedResponse()
        {
            Response response = new Response
            {
                Type = Response.Types.Type.NewParticipantCursaAdded,
            };
            return response;
        }

        //GET from REQUESTS
        public static Oficiu getOficiu(Request request)
        {
            Oficiu oficiu = new Oficiu(request.OficiuDTO.Username, request.OficiuDTO.Password)
            {
                Id = request.OficiuDTO.IdOficiu
            };
            return oficiu;
        }

        public static string getUsername(Request request)
        {
            return request.Username;
        }

        public static string getPassword(Request request)
        {
            return request.Password;
        }

        public static string getEchipa(Request request)
        {
            return request.Echipa;
        }

        public static string getNume(Request request)
        {
            return request.Nume;
        }

        public static int getCapMotor(Request request)
        {
            return request.CapMotor;
        }

        public static string getIdCursa(Request request)
        {
            return request.IdCursa;
        }

        public static lab3.domain.ParticipantCursa getParticipantCursa(Request request)
        {
            lab3.domain.ParticipantCursa participantCursa = new lab3.domain.ParticipantCursa(request.ParticipantCursa.IdParticipant, request.ParticipantCursa.IdCursa);
            participantCursa.Id = new Tuple<string, string>(request.ParticipantCursa.IdParticipant, request.ParticipantCursa.IdCursa);
            return participantCursa;
        }

        public static lab3.domain.Cursa getCursa(Request request)
        {
            lab3.domain.Cursa cursa = new lab3.domain.Cursa(request.CursaDTO.NrPersoane, request.CursaDTO.CapMotor)
            {
                Id = request.CursaDTO.IdCursa
            };
            return cursa;
        }

        //GET from RESPONSES
        //public static string getEroare(Response response)
        //{
        //    return response.Eroare;
        //}

        //public static Oficiu getOficiu(Response response)
        //{
        //    Oficiu oficiu = new Oficiu(response.OficiuDTO.Username, response.OficiuDTO.Password)
        //    {
        //        Id = response.OficiuDTO.IdOficiu
        //    };
        //    return oficiu;
        //}

        //public static string getEchipe(Response response)
        //{
        //    return response.Echipe;
        //}

        //public static lab3.domain.Participant getParticipant(Response response)
        //{
        //    lab3.domain.Participant participant = new lab3.domain.Participant(response.ParticipantDTO.Nume, response.ParticipantDTO.Echipa, response.ParticipantDTO.CapMotor)
        //    {
        //        Id = response.ParticipantDTO.IdParticipant
        //    };
        //    return participant;
        //}

        //public static lab3.domain.Cursa getCursa(Response response)
        //{
        //    lab3.domain.Cursa cursa = new lab3.domain.Cursa(response.Cursa.NrPersoane, response.Cursa.CapMotor);
        //    return cursa;
        //}

        //public static lab3.domain.Participant[] getParticipants(Response response)
        //{
        //    lab3.domain.Participant[] participants = new lab3.domain.Participant[response.Participant.Count];
        //    for(int i = 0; i < response.Participant.Count; i++)
        //    {
        //        lab3.domain.Participant participant = new lab3.domain.Participant(response.Participant[i].Nume, response.Participant[i].Echipa, response.Participant[i].CapMotor);
        //        participants[i] = participant;
        //    }
        //    return participants;
        //}

        //public static lab3.domain.Cursa[] getCurse(Response response)
        //{
        //    lab3.domain.Cursa[] curse = new lab3.domain.Cursa[response.CurseDTO.Count];
        //    for (int i = 0; i < response.CurseDTO.Count; i++)
        //    {
        //        lab3.domain.Cursa cursa = new lab3.domain.Cursa(response.CurseDTO[i].NrPersoane, response.CurseDTO[i].CapMotor)
        //        {
        //            Id = response.CurseDTO[i].IdCursa
        //        };
        //        curse[i] = cursa;
        //    }
        //    return curse;
        //}
    }
}
