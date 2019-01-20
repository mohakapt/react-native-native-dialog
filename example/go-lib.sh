#!/usr/bin/env bash -e

rm -rf ./lib
mkdir -p ./lib

cp ../package.json ./lib/package.json
cp ../README.md ./lib/README.md
cp ../LICENSE ./lib/LICENSE
cp ../index.js ./lib/index.js

yarn add file:./lib
