🧬 Mutantes API – Detector de ADN Mutante

Proyecto completo para examen MercadoLibre – Spring Boot 3 + Java 17

API REST que detecta si un humano es mutante analizando secuencias de ADN en una matriz NxN.
Implementa arquitectura profesional, validaciones avanzadas, persistencia, optimizaciones, Docker y documentación Swagger.

🚀 Demo en Producción (Render)
🔗 API Base URL:

👉 https://mutantes-api-thc5.onrender.com

🔍 Swagger UI:

👉 https://mutantes-api-thc5.onrender.com/swagger-ui.html

🧾 API Docs (OpenAPI JSON):

👉 https://mutantes-api-thc5.onrender.com/api-docs

📌 Endpoints Principales
1️⃣ POST /mutant

Determina si un ADN es mutante.

Request Body
{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}

Respuestas
Código	Significado
200 OK	Es mutante
403 Forbidden	No es mutante
400 Bad Request	ADN inválido
2️⃣ GET /stats

Retorna estadísticas acumuladas.

Ejemplo de salida:
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}

🏗️ Arquitectura del Proyecto
src/main/java/org/example/
│
├── config/                     → Configuración (Swagger)
├── controller/                 → Controladores REST
├── dto/                        → DTOs de entrada/salida
├── entity/                     → Entidad JPA (dna_records)
├── exception/                  → Excepciones + Handler global
├── repository/                 → Acceso a BD (Spring Data JPA)
├── service/                    → Lógica de negocio
├── validation/                 → Validador custom @ValidDnaSequence
└── MutantDetectorApplication   → Main class

⚡ Algoritmo de Detección

Implementado en MutantDetector.java con TODAS las optimizaciones que pide MercadoLibre:

✔ Early termination (corta al encontrar 2 secuencias)
✔ Conversión a char[][] (rápido)
✔ Boundary checking
✔ Comparaciones directas sin loops
✔ Complejidad O(N²) (óptimo para matrices grandes)
✔ Validaciones de ADN (NxN, caracteres, nulos, etc.)

El algoritmo detecta secuencias mutantes en 4 direcciones:

Horizontal →

Vertical ↓

Diagonal descendente ↘

Diagonal ascendente ↗

💾 Persistencia – BD + Dedupe con Hash

Este proyecto usa H2 en memoria y técnicas de deduplicación:

✔ Hash SHA-256 del ADN

Evita guardar ADN repetidos

Permite búsquedas O(1)

Cumple con lo requerido en la rúbrica

Tabla dna_records
Campo	Tipo	Descripción
id	bigint	PK autoincremental
dna_hash	varchar(64)	Único (SHA-256)
is_mutant	boolean	Resultado
created_at	timestamp	Fecha de análisis
🧪 Testing (JUnit 5)

La suite completa incluye:

Archivo	Tests Total
MutantDetectorTest	17 tests
MutantServiceTest	5 tests
StatsServiceTest	6 tests
MutantControllerTest	8 tests
TOTAL: 36 tests	

✔ Casos normales
✔ Casos borde
✔ Validaciones
✔ Integración con MockMvc
✔ Cobertura > 90% en servicios

📘 Documentación API (Swagger + OpenAPI)

Incluye:

SwaggerConfig

@Tag, @Operation, @ApiResponse en controllers

@Schema en DTOs

Swagger UI accesible en producción

Ver Swagger:
👉 https://mutantes-api-thc5.onrender.com/swagger-ui.html

🐳 Docker (Producción)

El proyecto incluye un Dockerfile multistage optimizado:

✔ Etapa 1: compila usando Gradle
✔ Etapa 2: imagen final ultraliviana con OpenJDK 17 Alpine

Build:

docker build -t mutantes-api .


Run:

docker run -p 8080:8080 mutantes-api


Deploy listo para Render.

▶️ Ejecutar Localmente
1. Clonar
git clone <URL_DE_TU_REPOSITORIO>
cd Mutantes

2. Ejecutar
./gradlew bootRun

3. Abrir Swagger

👉 http://localhost:8080/swagger-ui.html

🧾 Ejemplos de Prueba
Mutante (200)
{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]
}

Humano (403)
{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATTT",
    "AGACGG",
    "GCGTCA",
    "TCACTG"
  ]
}

Inválido (400)
{
  "dna": ["ATXCGA","CAGTGC"]
}
