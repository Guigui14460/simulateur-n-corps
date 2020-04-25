#!/bin/sh

cd $(dirname $0)/..
sh scripts/compile.sh
ant run