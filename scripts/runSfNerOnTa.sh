#!/bin/bash

CONFIG=config/sf.config

DIST=target
LIB=target/dependency
cpath=".:target/test-classes"
for JAR in `ls $DIST/*jar`; do
    cpath="$cpath:$JAR"
done
for JAR in `ls $LIB/*jar`; do
    cpath="$cpath:$JAR"
done

#add models (VERY IMPORTANT!)
cpath="$cpath:/shared/experiments/mssammon/lorelei/2018/ner-for-sf/for-chase/ner-models-lorelei-heuristic.jar"

CMD="java -classpath  ${cpath} -Xmx12g org.cogcomp.sfner.RunSfNer"

echo "$0: running command '$CMD'..."

${CMD}