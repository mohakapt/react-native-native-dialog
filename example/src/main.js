/* @flow */

import React, { Component } from 'react';
import { Button, Platform, StyleSheet, Text, View } from 'react-native';
import ModalAlert from 'react-native-native-dialog';

const instructions = Platform.select({
	ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
	android:
		'Double tap R on your keyboard to reload,\n' +
		'Shake or press menu button for dev menu',
});

type Props = {};
type State = {
	selectedPositions: Array<number>,
}
export default class App extends Component<Props, State> {

	constructor(props) {
		super(props);
		this.state = { selectedPositions: [] };
	}

	onTouched = () => {
		// ModalAlert.showInputDialog({
		// 	title: 'Do you want to update your iCloud Backup before erasing?',
		// 	message: 'If you erase without updating your backup, you may lose photos and other data that are not yet uploaded to iCloud.',
		//
		// 	positiveButton: 'Back Up Then Erase',
		// 	negativeButton: 'Erase Now',
		// 	neutralButton: 'Cancel',
		//
		// 	neutralButtonStyle: 'cancel',
		//
		// 	// preferredStyle: 'alert',
		//
		// 	theme: 'dark',
		// 	accentColor: '#0ad2c4',
		//
		// 	value: 'asdf',
		// 	placeholder: 'Enter your password',
		// 	keyboardType: 'number-pad',
		//
		// 	autoFocus: true,
		// 	maxLength: 5,
		// 	// onPositivePress: () => console.warn('positive'),
		// 	// onNegativePress: () => console.warn('negative'),
		// 	// onNeutralPress: () => console.warn('neutral'),
		// 	//
		// 	// onDismiss: () => console.warn('dismiss'),
		// });

		ModalAlert.showNumberPickerDialog({
			title: 'Pick a Number',

			value: 20,
			positiveButton: 'Pick',

			onPositivePress: value => {
				console.warn('---- ', value);
			},
		});
	};

	render() {
		return (
			<View style={styles.container}>
				<Text style={styles.welcome}>Welcome to React Native!</Text>
				<Text style={styles.instructions}>To get started, edit App.js</Text>
				<Text style={styles.instructions}>{instructions}</Text>
				<Button title={'Show Dialog'} onPress={this.onTouched} />
			</View>
		);
	}
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
		justifyContent: 'center',
		alignItems: 'center',
		backgroundColor: '#F5FCFF',
	},
	welcome: {
		fontSize: 20,
		textAlign: 'center',
		margin: 10,
	},
	instructions: {
		textAlign: 'center',
		color: '#333333',
		marginBottom: 5,
	},
});
