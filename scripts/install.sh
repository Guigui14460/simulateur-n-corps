#!/bin/sh

cd $(dirname $0)/..
[ -d lib ] || mkdir lib
[ -f lib/javafx.graphics.jar ] && exit 0
cd lib/
wget https://download2.gluonhq.com/openjfx/11.0.2/openjfx-11.0.2_linux-x64_bin-sdk.zip
unzip openjfx-11.0.2_linux-x64_bin-sdk.zip
cp javafx-sdk-11.0.2/lib/*.jar .
cp javafx-sdk-11.0.2/lib/*.so .
rm -r javafx-sdk-11.0.2/ openjfx-11.0.2_linux-x64_bin-sdk.zip
cd ..