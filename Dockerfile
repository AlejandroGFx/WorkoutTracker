# 1. EL INGREDIENTE BASE (El sistema operativo y Java)
# Le decimos a Docker: "Trae un mini-Linux que ya tenga Java 17 instalado"
FROM eclipse-temurin:17-jdk-alpine

# 2. EL DIRECTORIO DE TRABAJO
# Creamos una carpeta dentro de ese mini-Linux para no trabajar en la raíz
WORKDIR /app

# 3. COPIAR TU APLICACIÓN AL CONTENEDOR
# Copiamos el archivo .jar que generaste al compilar (tu app) dentro del mini-Linux
# (El nombre target/*.jar asume que usas Maven)
COPY target/*.jar app.jar

# 4. LA INSTRUCCIÓN DE ARRANQUE
# Le decimos a Docker qué comando ejecutar cuando el contenedor se encienda
ENTRYPOINT ["java", "-jar", "app.jar"]