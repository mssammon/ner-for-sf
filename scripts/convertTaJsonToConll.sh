#!/usr/bin/env bash

MAIN="org.cogcomp.sfner.ConvertJsonCorpusToConll"
LIB="target/dependency"
CP="target/classes"

IN="/shared/corpora/ner/conll2003/eng-files/Train-4types-json"
OUT="/shared/corpora/ner/conll2003/eng-files/Train-4types-conll"


for JAR in `ls $LIB/*jar`; do
    CP="${CP}:$JAR"
done

CMD="java -Xmx32g -cp $CP $MAIN $MODE $SRC $PROC"

#echo "$0: running command '$CMD'..."

$CMD

MODE="BUILD"
CMD="nice java -Xmx32g -cp $CP $MAIN $MODE $PROC $IDX"

echo "$0: running command '$CMD'..."

$CMD

echo "$0: done."