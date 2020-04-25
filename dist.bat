set basedir="."
set output_dir=nbodysimulator_rendu

rmdir /s /q "%output_dir%"
if not exist "%output_dir%" ( mkdir "%output_dir%" )

cd "%workspace_dir%"

mkdir "%output_dir%\scripts"
mkdir "%output_dir%\src"

xcopy "%basedir%\scripts" "%output_dir%\scripts" /E
xcopy "%basedir%\src" "%output_dir%\src" /E
copy "README.txt" "%output_dir%"
copy "build.xml" "%output_dir%"

tar -cf "%output_dir%.tar" "%output_dir%"
rmdir "%output_dir%" /s /q
move "%output_dir%.tar" "../"