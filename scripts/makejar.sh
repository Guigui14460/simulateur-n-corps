#!/bin/sh

cd $(dirname $0)/..
sh scripts/compile.sh
[ -d dist ] || mkdir dist
ant packaging