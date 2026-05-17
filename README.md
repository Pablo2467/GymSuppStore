# 🏋️ GymSupp Store

Sistema de Gestión de Suplementos Deportivos — Proyecto Integrador de Calidad de Software 2026A

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![JUnit](https://img.shields.io/badge/JUnit-5-blue)
![Selenium](https://img.shields.io/badge/Selenium-4.x-yellow)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)
![SonarQube](https://img.shields.io/badge/SonarQube-Community-orange)

---

## Descripción

GymSupp Store es una aplicación web Java desarrollada con Spring Boot que permite gestionar el 
inventario de suplementos deportivos. Cuenta con autenticación por roles, CRUD completo de 
productos y una suite integral de pruebas automatizadas.

---

## Requisitos del Sistema

| Herramienta | Versión mínima |
|-------------|---------------|
| Java (JDK)  | 11 o superior |
| Maven       | 3.8+          |
| Docker      | 24+           |
| Docker Compose | 2.x        |
| Git         | 2.x           |
| RAM         | 8 GB recomendado |
| Disco       | 10 GB libres  |

---

## Estructura del Repositorio

```
GymSuppStore/
├── app/                          # (alias de src/) Código fuente principal
│   └── src/
│       ├── main/java/            # Código de producción
│       └── test/java/            # Pruebas unitarias JUnit
├── tests/
│   ├── selenium/                 # Pruebas funcionales Selenium + POM
│   └── jmeter/                   # Planes de prueba JMeter (.jmx)
├── docker/
│   ├── Dockerfile                # Imagen de la aplicación
│   └── docker-compose.yml        # Orquestación: app + SonarQube
├── reports/                      # Reportes generados (JaCoCo, JMeter, SonarQube)
├── docs/                         # Documentación: plan de pruebas, especificaciones
├── .github/
│   └── workflows/
│       └── ci.yml                # Pipeline CI/CD con GitHub Actions
├── pom.xml                       # Dependencias Maven
└── README.md
```

---

## Instalación y Ejecución Local

### 1. Clonar el repositorio

```bash
git clone https://github.com/TU_USUARIO/GymSuppStore.git
cd GymSuppStore
```

### 2. Ejecutar con Maven (sin Docker)

```bash
mvn spring-boot:run
```

Acceder en: [http://localhost:8080](http://localhost:8080)

**Credenciales disponibles:**

| Rol      | Usuario   | Contraseña |
|----------|-----------|------------|
| Admin    | admin     | gym123     |
| Vendedor | vendedor  | vend123    |

### 3. Ejecutar con Docker Compose

```bash
docker-compose up --build
```

Servicios disponibles:
- Aplicación: [http://localhost:8080](http://localhost:8080)
- SonarQube: [http://localhost:9000](http://localhost:9000) (admin / admin)

---

## Ejecución de Pruebas

### Pruebas Unitarias (JUnit 5)

```bash
# Todas las pruebas unitarias (excluye Selenium)
mvn test -Dtest="!ProductSeleniumTest"
```

### Reporte de Cobertura (JaCoCo)

```bash
mvn test jacoco:report -Dtest="!ProductSeleniumTest"
# Reporte en: target/site/jacoco/index.html
```

### Pruebas Selenium - Chrome (requiere app corriendo)

```bash
# Asegurarse que la app está en http://localhost:8080
BROWSER=chrome mvn test -Dtest="ProductSeleniumTest"
```

### Pruebas Selenium - Firefox

```bash
BROWSER=firefox mvn test -Dtest="ProductSeleniumTest"
```

> En Linux sin pantalla (headless) funciona automáticamente.  
> En Windows: `set BROWSER=firefox` antes del comando.

### Pruebas de Rendimiento (JMeter)

```bash
# Requiere JMeter instalado y la app corriendo
jmeter -n -t tests/jmeter/plan-pruebas.jmx -l reports/jmeter-results.jtl -e -o reports/jmeter-html/
```

### Análisis SonarQube

```bash
# Con Docker Compose corriendo (SonarQube en localhost:9000)
mvn sonar:sonar \
  -Dsonar.projectKey=gymsupp-store \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=admin \
  -Dsonar.password=admin
```

---

## CI/CD - GitHub Actions

El pipeline se ejecuta automáticamente en cada push a `main` o `master`:

1. **Pruebas Unitarias** — JUnit 5 + JaCoCo (cobertura)
2. **SonarQube** — Análisis estático de calidad
3. **Selenium Chrome** — 5 casos funcionales en headless
4. **Selenium Firefox** — mismos 5 casos en Firefox headless
5. **Docker Build** — construye la imagen final

Los reportes quedan disponibles como **Artifacts** en cada ejecución de GitHub Actions.

---

## Tecnologías Utilizadas

- **Backend:** Spring Boot, Spring Security, Spring Data JPA
- **Base de datos:** H2 (desarrollo) / PostgreSQL (producción)
- **Plantillas:** Thymeleaf
- **Pruebas unitarias:** JUnit 5, Mockito, JaCoCo
- **Pruebas funcionales:** Selenium WebDriver 4.x, WebDriverManager, Page Object Model (POM)
- **Pruebas de rendimiento:** Apache JMeter 5.x
- **Calidad de código:** SonarQube Community
- **Contenedores:** Docker, Docker Compose
- **CI/CD:** GitHub Actions

---

## Estándares Aplicados

- ISO/IEC 25010 — Modelo de calidad del producto
- ISO/IEC 29119 — Procesos de prueba de software
- ISO 9001 — Aseguramiento de calidad
- CMMI — Madurez del proceso de desarrollo

---

## Autores

**Juan Pablo Valverde Barreiro**