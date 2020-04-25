#!/bin/sh

cd $(dirname $0)/..
sh scripts/install.sh
[ -d bin ] || mkdir bin
ant compile