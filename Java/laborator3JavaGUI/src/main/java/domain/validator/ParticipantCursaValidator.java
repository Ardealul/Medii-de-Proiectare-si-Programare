package domain.validator;

import domain.ParticipantCursa;

public class ParticipantCursaValidator implements Validator<ParticipantCursa> {
    @Override
    public void validate(ParticipantCursa entity) throws ValidatorException {
        String errors = "";
        if(entity.getIdParticipant().equals(""))
            errors += "IdParticipant invalid!";
        if(entity.getIdCursa().equals(""))
            errors += "IdCursa invalid!";
        if(errors.length() > 0)
            throw new ValidatorException(errors);
    }
}
