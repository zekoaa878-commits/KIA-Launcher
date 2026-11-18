@echo off
echo KIA Launcher Lite - Build بدون Android Studio
if "%ANDROID_HOME%"=="" set ANDROID_HOME=%LOCALAPPDATA%\Android\Sdk
if not exist "%ANDROID_HOME%\platform-tools" echo [!] حط Command Line Tools في %ANDROID_HOME% وشغل تاني & pause & exit
call gradlew :app:assembleRelease
echo جاهز: app\build\outputs\apk\release\app-release.apk
pause
