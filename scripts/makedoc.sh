#!/bin/sh

cd $(dirname $0)/..
sh scripts/install.sh
[ -d docs ] || mkdir docs
ant doc