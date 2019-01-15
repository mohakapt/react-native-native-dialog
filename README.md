
# react-native-native-dialog

## Getting started

`$ npm install react-native-native-dialog --save`

### Mostly automatic installation

`$ react-native link react-native-native-dialog`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-native-dialog` and add `RNNativeDialog.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNNativeDialog.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

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


## Usage
```javascript
import RNNativeDialog from 'react-native-native-dialog';

// TODO: What to do with the module?
RNNativeDialog;
```
  