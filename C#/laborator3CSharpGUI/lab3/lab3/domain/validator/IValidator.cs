using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab3.domain
{
    public interface IValidator<T>
    {
        void validate(T elem);
    }

    public class ValidatorException : ApplicationException
    {
        public ValidatorException(string message) : base(message) { }
    }
}
