#!/usr/bin/env bash

protoc -I=./proto --java_out=./src/main/java ./proto/msg.proto

#protoc -I=$SRC_DIR --java_out=$DST_DIR $SRC_DIR/addressbook.proto