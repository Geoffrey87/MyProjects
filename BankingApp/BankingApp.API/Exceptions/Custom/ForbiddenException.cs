namespace BankingApp.API.Exceptions.Custom
{
    public class ForbiddenException : Exception
    {
        public ForbiddenException(string message) : base(message) { }
    }
}
