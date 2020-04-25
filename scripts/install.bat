if not exist lib ( mkdir lib )
if not exist bin ( mkdir bin )
if not exist lib\javafx.graphics.jar (
cd lib
powershell.exe -command "Invoke-WebRequest https://download2.gluonhq.com/openjfx/11.0.2/openjfx-11.0.2_windows-x64_bin-sdk.zip -O javaFX.zip"
tar -xf javaFX.zip
move javafx-sdk-11.0.2\bin\*.dll "."
move javafx-sdk-11.0.2\lib\*.jar "."
copy ".\*.dll" "..\bin\"
rmdir /s /q javafx-sdk-11.0.2
del ".\javaFX.zip"
del "*.dll"
cd ..
)