# Mutantes API (Spring Boot)

Proyecto completo que implementa la API para detectar mutantes a partir de una matriz NxN de ADN.

Endpoints:
- POST /mutant   -> 200 si es mutante, 403 si no lo es
- GET  /stats    -> estadísticas

Ejecutar con Gradle:

./gradlew bootRun

Generar JAR:

./gradlew bootJar

Docker:

docker build -t mutantes-api .
docker run -p 8080:8080 mutantes-api


## Deploy on Render
This project includes a `render.yaml` to deploy on Render using Docker. The service uses the included `Dockerfile`.

Uploaded supplemental file (local path):
- /mnt/data/Examen Mercadolibre .pdf
