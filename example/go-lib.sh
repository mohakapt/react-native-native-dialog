#!/usr/bin/env bash -e

rm -rf ./lib
mkdir -p ./lib
mkdir -p ./lib/android

cp ../android/build.gradle ./lib/android/build.gradle
cp -a ../android/src ./lib/android/src
cp -a ../ios ./lib/ios
cp ../package.json ./lib/package.json
cp ../README.md ./lib/README.md
cp ../LICENSE ./lib/LICENSE
cp ../index.js ./lib/index.js
rm -rf ./lib/ios/RNNativeDialog.xcworkspace

yarn add file:./lib
