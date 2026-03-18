# Stage 1 — build image
# This image is discarded at the end and never goes to production
FROM node:22-alpine AS build
WORKDIR /app

# Copy only package.json first to take advantage of Docker's layer caching
# If dependencies haven't changed, Docker skips the npm install step
COPY package*.json .
RUN npm install

# Copy the remaining source code and build in production mode
COPY . .
RUN npm run build -- --configuration=production

# Stage 2 — lightweight runtime base image (nginx to serve static files)
FROM nginx:alpine AS final
COPY --from=build /app/dist/banking-web/browser /usr/share/nginx/html
EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]