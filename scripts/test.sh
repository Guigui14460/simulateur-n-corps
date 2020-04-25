#!/bin/sh

cd $(dirname $0)/..
sh scripts/compile.sh

echo "All tests running ..."
ant test