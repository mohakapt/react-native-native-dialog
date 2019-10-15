#!/usr/bin/env bash -e

SRC="../"
DEST="../example/node_modules/react-native-native-dialog/"
FILES=("android" "ios" "index.d.ts" "index.js" "LICENSE" "package.json" "react-native-native-dialog.podspec" "README.md")

for i in "${FILES[@]}"; do
  rm -rf "${DEST}${i}"
  cp -r "${SRC}${i}" "${DEST}${i}"
done
