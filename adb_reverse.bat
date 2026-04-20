@echo off
echo Configurando tunel ADB reverse para backend (puerto 8084)...
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" reverse tcp:8084 tcp:8084
echo Listo. El movil ya puede conectar al backend del PC.
pause

