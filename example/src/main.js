/**
 * @flow
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, Button, NativeModules } from 'react-native';
import ModalAlert from './src';

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

		// ModalAlert.showDialog({
		// 	title: 'Add Subject',
		// 	message: 'Lorem ipsum dolor sit',
		//
		// 	positiveButton: 'Add',
		// 	negativeButton: 'Cancel',
		// 	neutralButton: 'Help',
		//
		// 	theme: 'light',
		// 	accentColor: '#4CAF50',
		//
		// 	onPositivePress: () => console.warn('positive'),
		// 	onNegativePress: () => console.warn('negative'),
		// 	onNeutralPress: () => console.warn('neutral'),
		//
		// 	onDismiss: () => console.warn('dismiss'),
		// });
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
