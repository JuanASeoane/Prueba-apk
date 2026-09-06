# NombrePDFApp

Aplicación Android sencilla que permite escribir un nombre y guardarlo en un archivo PDF en el teléfono.

## Generar la APK con GitHub Actions

1. Crea un repositorio nuevo en GitHub.
2. Sube **el contenido de esta carpeta** al repositorio.
3. Entra en la pestaña **Actions**.
4. Abre el workflow **Compilar APK**.
5. Si no se ejecutó automáticamente, pulsa **Run workflow**.
6. Cuando termine, abre la ejecución y descarga el artefacto **NombrePDFApp-APK**.
7. Dentro encontrarás `app-debug.apk`, que puedes instalar en un móvil Android.

El workflow también se ejecuta automáticamente al subir cambios a las ramas `main` o `master`.

## Requisitos técnicos

- Android Gradle Plugin: 8.7.3
- Kotlin: 2.0.21
- Gradle usado por GitHub Actions: 8.9
- Java: 17
- minSdk: 24
- targetSdk: 35

## Funcionamiento

- Introduce un nombre.
- Pulsa el botón para guardar el PDF.
- El archivo se guarda mediante las APIs de almacenamiento de Android.

Esta primera versión genera una APK de depuración (`debug`), adecuada para pruebas e instalación directa.
