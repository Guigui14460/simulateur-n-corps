#!/bin/sh

workspace_dir="."
output_dir="nbodysimulator_rendu"

[ -d ${output_dir} ] || mkdir ${output_dir}
rm -rf ${output_dir}/*

basedir=$(pwd)
cd ${workspace_dir}

cp -r ${basedir}/src ${output_dir}/src/
#cd - > /dev/null

mkdir ${output_dir}/scripts
cp -f scripts/*.sh ${output_dir}/scripts/

cp README.txt ${output_dir}
cp build.xml ${output_dir}

tar -cf ${output_dir}.tar ${output_dir}
rm -rf ${output_dir}
mv ${output_dir}.tar ../
