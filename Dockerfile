FROM openjdk:17-slim

# Directorio de trabajo
WORKDIR /app

# Copia el jar de la aplicación
COPY target/*.jar app.jar

# Expone el puerto que utiliza tu aplicación
EXPOSE 8080

# Variable de entorno para la conexión a la base de datos
ENV URL_DB=r2dbc:mysql://franchises-db.c7uw28yuwyua.us-east-1.rds.amazonaws.com/franchises-db
ENV USER_DB=admin
ENV PASSWORD_DB=admin123

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]