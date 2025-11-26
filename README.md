🧬 Mutantes API – Detector de ADN Mutante

Examen MercadoLibre – Spring Boot 3 + Java 17

API REST que analiza secuencias de ADN para determinar si un humano es mutante.
Incluye validaciones, persistencia, estadísticas, documentación Swagger, tests y deployment en Render.

🚀 Demo en Producción (Render)
Recurso	URL
API Base URL	https://mutantes-api-thc5.onrender.com

Swagger UI	https://mutantes-api-thc5.onrender.com/swagger-ui.html

OpenAPI Docs	https://mutantes-api-thc5.onrender.com/api-docs
📦 Tecnologías utilizadas

Java 17

Spring Boot 3

Spring Web

Spring Data JPA

H2 Database

Lombok

JUnit 5 + MockMvc

Swagger / OpenAPI

Docker

Gradle

📥 Instalación y Ejecución Local

Clonar el repositorio:

https://github.com/diegodd01/3k09-49997-Integrador-final.git

cd mutantes-project-render

▶ Ejecutar la API
```bash 
./gradlew bootRun
```

La API arrancará en:

http://localhost:8080

🔍 Probar la API con Swagger

Abrir:

👉 http://localhost:8080/swagger-ui.html

Ahí podés probar:

POST /mutant

GET /stats

/ (endpoint de health)

🧪 Endpoints Principales
1️⃣ POST /mutant

Determina si un ADN es mutante.

URL:

POST http://localhost:8080/mutant

Body válido (mutante)
{
"dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}

Respuestas
Código	Significado
200 OK	Es mutante
403 Forbidden	No es mutante
400 Bad Request	ADN inválido
2️⃣ GET /stats

Devuelve estadísticas desde la base de datos.

URL:

GET http://localhost:8080/stats

Ejemplo de respuesta:
{
"count_mutant_dna": 40,
"count_human_dna": 100,
"ratio": 0.4
}

🧬 Cómo funciona el algoritmo

Implementado en MutantDetector.java, busca secuencias de 4 letras iguales en:

✔ Horizontal →
✔ Vertical ↓
✔ Diagonal descendente ↘
✔ Diagonal ascendente ↗

Optimizado:

Early termination: corta al encontrar 2 secuencias

Comparación por char[]

Complejidad O(N²)

Validaciones estrictas (NxN, caracteres válidos, etc.)

🗄 Persistencia (BD H2)

Cada ADN analizado se guarda en la tabla:

DNA_RECORDS


Con estos campos:

Campo	Tipo	Descripción
id	bigint	Autoincremental
dna_hash	varchar(64)	SHA-256 único
is_mutant	boolean	Resultado
created_at	timestamp	Fecha de análisis

✔ No se guardan ADN duplicados (cache por hash).
✔ StatsService usa consultas directas para calcular el ratio.

🧪 Ejecutar Tests
```bash
./gradlew test
```

Incluye tests para:

MutantDetector

MutantService

StatsService

MutantController (MockMvc)

Cobertura > 90% en servicios.

🧰 Ver Base de Datos H2 Localmente

Ejecutá la app (bootRun)

Abrí en navegador:

http://localhost:8080/h2-console


Ingresá estos valores:

JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (vacío)


Consultar tabla:

SELECT * FROM DNA_RECORDS;

🐳 Ejecutar con Docker

Build de la imagen:
```bash
docker build -t mutantes-api .
```

Ejecutar el contenedor:
```bash
docker run -p 8080:8080 mutantes-api
   ```

La API quedará disponible en:

http://localhost:8080

🧱 Arquitectura del Proyecto
src/main/java/org/example/mutantes
│
├── controller      → Controladores REST (/mutant /stats)
├── service         → Lógica de negocio (análisis, hashing, stats)
├── repository      → Spring Data JPA (DnaRecordRepository)
├── entity          → Entidad JPA (DnaRecord)
├── validation      → Validación custom de ADN
├── exception       → Excepciones + handler global
├── config          → SwaggerConfig
└── MutantDetector  → Algoritmo de detección

📄 Secuencia del Caso de Uso “Detectar Mutante”

Controller recibe JSON

Service calcula hash del ADN

Busca si ya existe en BD

Si existe → retorna resultado

Si no → ejecuta algoritmo

Guarda resultado en BD

Retorna 200 o 403

(Se adjuntan diagramas de secuencia en la carpeta /docs)

🎯 Conclusión

La Mutantes API cumple todos los niveles del examen:

✔ Nivel 1: Algoritmo eficiente
✔ Nivel 2: API REST + Render
✔ Nivel 3: Persistencia + Stats + Tests + Documentación

👨‍💻 Autor

Diego Daza