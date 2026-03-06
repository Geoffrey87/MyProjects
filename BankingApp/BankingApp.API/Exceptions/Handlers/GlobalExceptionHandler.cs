using BankingApp.API.Exceptions.Custom;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using System.Net;

namespace BankingApp.API.Exceptions.Handlers
{
    public class GlobalExceptionHandler : IExceptionFilter
    {
        public void OnException(ExceptionContext context)
        {
            var (statusCode, message) = context.Exception switch
            {
                NotFoundException ex => (HttpStatusCode.NotFound, ex.Message),
                ConflictException ex => (HttpStatusCode.Conflict, ex.Message),
                BadRequestException ex => (HttpStatusCode.BadRequest, ex.Message),
                ForbiddenException ex => (HttpStatusCode.Forbidden, ex.Message),
                UnauthorizedException ex => (HttpStatusCode.Unauthorized, ex.Message),
                _ => (HttpStatusCode.InternalServerError, "Internal server error: " + context.Exception.Message)
            };

            context.Result = new ObjectResult(new
            {
                timestamp = DateTime.UtcNow,
                status = (int)statusCode,
                error = statusCode.ToString(),
                message
            })
            {
                StatusCode = (int)statusCode
            };

            context.ExceptionHandled = true;
        }
    }
}
