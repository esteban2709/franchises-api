# Franchises API

Aplicación **Spring Boot WebFlux** para gestionar un listado de franquicias.  
Cada franquicia tiene **sucursales asociadas**, y las sucursales están vinculadas a **productos**.

---

## 🚀 ¿Cómo correr el proyecto localmente?

### 1. Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instaladas las siguientes herramientas:

- **Java 17 o superior**  
  Descarga el JDK adecuado para tu sistema operativo desde:  
  [Oracle JDK Downloads](https://www.oracle.com/java/technologies/downloads/)

- **Maven**  
  Descarga e instala Maven desde:  
  [Apache Maven](https://maven.apache.org/download.cgi)  
  Luego, configura las variables de entorno según tu sistema operativo.

- **MySQL (XAMPP recomendado)**  
  Para gestionar la base de datos, se recomienda instalar **XAMPP**, que incluye MySQL.  
  Descárgalo desde:  
  [XAMPP Download](https://www.apachefriends.org/es/download.html)

---

### 2. Clonar el repositorio

Ejecuta el siguiente comando en tu terminal:

```sh
git clone https://github.com/esteban2709/franchises-api.git
cd franchises-api
```

---

### 3. Configuración de la base de datos

1. Modifica las variables de conexión en `application.properties` según tu configuración local.  
   También puedes utilizar la base de datos preconfigurada en la nube:

   ```properties
   spring.r2dbc.url=${URL_DB:r2dbc:mysql://franchises-db.c7uw28yuwyua.us-east-1.rds.amazonaws.com/franchises-db}
   spring.r2dbc.username=${USER_DB:admin}
   spring.r2dbc.password=${PASSWORD_DB:admin123}
   ```

2. Crea la base de datos ejecutando el siguiente comando en MySQL:

   ```sql
   CREATE DATABASE franchises-db;
   ```

---

### 4. Construir y ejecutar el proyecto

Ejecuta los siguientes comandos en la terminal dentro del directorio del proyecto:

```sh
mvn clean compile
mvn spring-boot:run
```

---

### 6. Ejecutar con Docker

Si prefieres ejecutar la aplicación dentro de un contenedor **Docker**, sigue estos pasos:

1. **Instalar Docker** *(Si no lo tienes instalado)*  
   Descarga e instala Docker desde:  
   [Docker Download](https://www.docker.com/get-started/)

2. **Construir la imagen de Docker**

   ```sh
   docker build -t franchises-api .
   ```

3. **Ejecutar el contenedor mapeando el puerto 8080:**

   ```sh
   docker run -p 8080:8080 franchises-api
   ```

Esto iniciará la aplicación dentro de un contenedor y estará accesible en `http://localhost:8080`.

## 🎯 ¡Listo! Ahora puedes empezar a usar **Franchises API** 🚀

