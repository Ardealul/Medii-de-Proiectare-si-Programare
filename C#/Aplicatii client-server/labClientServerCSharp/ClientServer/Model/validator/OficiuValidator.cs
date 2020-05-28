using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab3.domain.validator
{
    public class OficiuValidator: IValidator<Oficiu>
    {
        public void validate(Oficiu oficiu)
        {
            StringBuilder errors = new StringBuilder();
            if (Int32.Parse(oficiu.Id) <= 0)
                errors.Append("Id must be positive value!");
            if (oficiu.Username == "")
                errors.Append("Username must be not null!");
            if (oficiu.Password == "")
                errors.Append("Password must be not null!");

            if (errors.Length != 0)
                throw new ValidatorException(errors.ToString());
        }
    }
}
