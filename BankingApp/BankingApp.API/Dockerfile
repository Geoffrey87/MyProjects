# Stage 1 — lightweight runtime base image
FROM mcr.microsoft.com/dotnet/aspnet:10.0 AS base
WORKDIR /app
EXPOSE 8080

# Stage 2 — build image 
# This image is discarded at the end and never goes to production
FROM mcr.microsoft.com/dotnet/sdk:10.0 AS build
WORKDIR /src

# Copy only the .csproj first to take advantage of Docker's layer caching
# If the .csproj hasn't changed, Docker skips the restore step
COPY ["BankingApp.API.csproj", "."]
RUN dotnet restore

# Copy the remaining source code and compile in Release mode
COPY . .
RUN dotnet publish -c Release -o /app/publish

# Stage 3 — final image
# Starts from the lightweight base image (Stage 1) and copies only the build output (Stage 2)
FROM base AS final
WORKDIR /app
COPY --from=build /app/publish .
ENTRYPOINT ["dotnet", "BankingApp.API.dll"]