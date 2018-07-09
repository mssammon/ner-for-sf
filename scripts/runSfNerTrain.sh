#!/usr/bin/env bash

TRAINDIR=/shared/experiments/mssammon/lorelei/2018/ner-for-sf/conll-4types-split/train
TESTDIR=/shared/experiments/mssammon/lorelei/2018/ner-for-sf/conll-4types-split/dev
CONFIG=config/sf.config

./scripts/train.sh $TRAINDIR $TESTDIR $CONFIG

