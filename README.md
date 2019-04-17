# react-native-native-dialog
One Paragraph of project description goes here

[![Latest Stable Version](https://img.shields.io/npm/v/react-native-native-dialog.svg)](https://www.npmjs.com/package/react-native-native-dialog)
[![NPM Downloads](https://img.shields.io/npm/dm/react-native-native-dialog.svg)](https://www.npmjs.com/package/react-native-native-dialog)
[![GitHub issues](https://img.shields.io/github/issues-raw/mohakapt/react-native-native-dialog.svg)](https://github.com/mohakapt/react-native-native-dialog/issues)
[![Used Languages](https://img.shields.io/github/languages/top/mohakapt/react-native-native-dialog.svg)](https://github.com/mohakapt/react-native-native-dialog/issues)

Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

## Installation

```bash
npm install react-native-native-dialog --save
```
**Or if you're using yarn:**

```bash
yarn add react-native-native-dialog
```

### Link Native Code.

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

If you want to contribute, please submit a pull request, or contact m.yaman.katby@gmail.com for more information.
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
