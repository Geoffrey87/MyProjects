namespace BankingApp.API.Exceptions.Custom
{
    public class BadRequestException : Exception
    {
        public BadRequestException(string message) : base(message) { }
    }
}
