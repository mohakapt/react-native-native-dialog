# react-native-native-dialog
> A React Native module that exposes some of the common native dialogs to React Native.

[![Latest Stable Version](https://img.shields.io/npm/v/react-native-native-dialog.svg)](https://www.npmjs.com/package/react-native-native-dialog)
[![NPM Downloads](https://img.shields.io/npm/dm/react-native-native-dialog.svg)](https://www.npmjs.com/package/react-native-native-dialog)
[![GitHub issues](https://img.shields.io/github/issues-raw/mohakapt/react-native-native-dialog.svg)](https://github.com/mohakapt/react-native-native-dialog/issues)
[![Used Languages](https://img.shields.io/github/languages/top/mohakapt/react-native-native-dialog.svg)](https://github.com/mohakapt/react-native-native-dialog/issues)

### 🚀 Motivation
The issue with trying to mock native components using `<View />`s is that no matter how much time and effort you spend to make it look like the real-deal, You end up with janky looking results (I spent hours taking screenshots of the real Android dialog and trying to imitate it, And didn't get a satisfying result). But to be fair, using `react-native` `<View />`s offers a lot of customization which is something you cannot simply just get with native libraries. So there is a decision that needs to be made.

Anyways I decided to make a library for some of the commonly used dialogs using native APIs.


### ⬇️ Installation

```bash
npm install react-native-native-dialog --save
```
**Or if you're using yarn:**

```bash
yarn add react-native-native-dialog
```
> ⚠️ The library only works with react-native@0.60 and up.
>
### Additional Setup
Since this library only works with react-native@0.60.0 and up there is no need to manually link the library, But there still some additional setup you need to do.

[Check ]


**Automatically:**
```bash
react-native link react-native-native-dialog
```

**Manual:**

**iOS**

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-native-dialog` and add `RNNativeDialog.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNNativeDialog.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

**Android**

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.github.mohaka.RNNativeDialogPackage;` to the imports at the top of the file
  - Add `new RNNativeDialogPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-native-dialog'
  	project(':react-native-native-dialog').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-native-dialog/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-native-dialog')
  	```

## Running the tests

## Contributing
We would love to have community contributions and support! A few areas where could use help right now:
* Bug reports and/or fixes
* Writing tests
* Creating examples for the docs

If you want to contribute, please submit a pull request, or contact mohakapt@gmail.com for more information.
When you commit your messages, follow this convention:
```
Main changes subject
- Optional message
- Another optional message
```

If you do a breaking change, add an explanation preceded by `BREAKING CHANGE:` keyword. For example:
```
BREAKING CHANGE: Main changes subject
- Optional message
- Another optional message
```

## Authors

* **Heysem Katibi** - *Initial work*
* **Yaman Katby**

See also the list of [contributors](https://github.com/mohakapt/react-native-native-dialog/contributors) who participated in this project.

## License

This library is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details.
