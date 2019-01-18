/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, Button } from 'react-native';
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
		ModalAlert.showItemsDialog({
			title: 'Add Subject',
			message: 'This is the message to show,\n\n Are you sure?',

			positiveButton: 'Add',
			negativeButton: 'Cancel',
			neutralButton: 'Help',

			theme: 'light',
			accentColor: '#4CAF50',

			onPositivePress: () => console.warn('positive'),
			onNegativePress: () => console.warn('negative'),
			onNeutralPress: () => console.warn('neutral'),
			onItemSelect: (value) => this.setState({ selectedPositions: value }),

			mode: 'multiple',
			items: ['A', 'B', 'C', 'D', 'E', 'F'],
			selectedItems: this.state.selectedPositions,
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
