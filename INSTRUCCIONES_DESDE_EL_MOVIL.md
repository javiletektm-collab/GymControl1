# Generar la APK desde el móvil sin Android Studio

Este proyecto está preparado para que GitHub compile la aplicación automáticamente.

## Primera vez

1. Abre **github.com** en Chrome y crea una cuenta gratuita.
2. Pulsa tu foto > **Your repositories** > **New**.
3. Pon como nombre: `GymControl`.
4. Selecciona **Public** para que la compilación con los servidores estándar de GitHub sea gratuita.
5. Pulsa **Create repository**.
6. En la página del repositorio, pulsa **Add file** > **Upload files**.
7. Descomprime este ZIP en el teléfono y sube **el contenido interior de la carpeta GymControlAndroid**, no la carpeta contenedora completa.
   - Debes ver `app`, `.github`, `build.gradle` y `settings.gradle` directamente en la portada del repositorio.
8. Pulsa **Commit changes**.

## Generar y descargar la APK

1. Abre la pestaña **Actions** de tu repositorio.
2. Entra en **Generar APK de GymControl**.
3. Pulsa **Run workflow** y otra vez **Run workflow**.
4. Cuando aparezca una marca verde, abre esa ejecución.
5. Baja hasta **Artifacts**.
6. Pulsa **GymControl-APK**.
7. Se descargará un ZIP. Descomprímelo y encontrarás `app-debug.apk`.
8. Pulsa el APK para instalarlo. Android puede pedir permiso para instalar aplicaciones desde Chrome o desde tu gestor de archivos.

## Después de cada modificación

Cada vez que se suba una modificación al repositorio, GitHub volverá a compilar automáticamente. También puedes usar manualmente **Actions > Generar APK de GymControl > Run workflow**.

## Para Google Play

Esta compilación es una APK de prueba. Para publicar será necesario generar un AAB de lanzamiento firmado. Eso se añadirá cuando la aplicación esté terminada y tengas creada la cuenta de Google Play Console.
