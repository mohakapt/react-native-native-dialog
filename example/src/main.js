/* @flow */

import React, { Component } from 'react';
import { Button, Platform, StyleSheet, Text, View } from 'react-native';
import NativeDialog from 'react-native-native-dialog';
import DateTimePicker from 'react-native-modal-datetime-picker';
import moment from 'moment';

const instructions = Platform.select({
	ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
	android:
		'Double tap R on your keyboard to reload,\n' +
		'Shake or press menu button for dev menu',
});

type Props = {};
type State = {
	selectedPositions: Array<number>,
	isVisible: boolean
}
export default class App extends Component<Props, State> {

	constructor(props) {
		super(props);
		this.state = { selectedPositions: [], isVisible: false };
	}

	onTouched = (style) => {
		// NativeDialog.showInputDialog({
		// 	title: 'Do you want to update your iCloud Backup before erasing?',
		// 	message: 'If you erase without updating your backup, you may lose photos and other data that are not yet uploaded to iCloud.',
		//
		// 	positiveButton: 'Back Up Then Erase',
		// 	negativeButton: 'Erase Now',
		// 	neutralButton: 'Cancel',
		//
		// 	negativeButtonStyle: 'default',
		// 	neutralButtonStyle: 'cancel',
		//
		// 	transitionStyle: 'bounceUp',
		// 	preferredStyle: style,
		//
		// 	theme: 'dark',
		// 	accentColor: '#ff4a9e',
		//
		// 	value: 'asdf',
		// 	placeholder: 'Enter your password',
		// 	keyboardType: 'number-pad',
		//
		// 	autoFocus: true,
		// 	maxLength: 5,
		//
		// 	onPositivePress: (input) => console.warn('positive - ', input),
		// 	onNegativePress: (input) => console.warn('negative - ', input),
		// 	onNeutralPress: (input) => console.warn('neutral - ', input),
		//
		// 	onDismiss: () => console.warn('dismiss'),
		// });

		// NativeDialog.showItemsDialog({
		// 	title: 'How would you like to pay for your meal today?',
		//
		// 	mode: 'multiple',
		// 	items: [
		// 		{ id: 'cc', title: 'Credit Card' },
		// 		{ id: 'dc', title: 'Debit Card' },
		// 		{ id: 'pp', title: 'PayPal' },
		// 		{ id: 'c', title: 'Cash' },
		// 	],
		// 	selectedItems: ['pp', 'c'],
		//
		// 	preferredStyle: style,
		//
		// 	theme: 'light',
		// 	accentColor: '#1cc35d',
		//
		// 	positiveButton: 'Select',
		// 	negativeButton: 'Cancel',
		//
		// 	onItemSelect: selectedId => {
		// 		console.warn(selectedId);
		// 	},
		// 	onNegativePress: () => console.warn('negative'),
		// 	onDismiss: () => console.warn('dismiss'),
		// });

		// NativeDialog.showNumberPickerDialog({
		// 	title: 'Pick a Number',
		//
		// 	value: 20,
		// 	positiveButton: 'Pick',
		//
		// 	onPositivePress: value => {
		// 		console.warn('---- ', value);
		// 	},
		// });

		NativeDialog.showDatePickerDialog({
			theme: 'dark',
			date: '01/01/1996',
		});
	};

	render() {
		return (
			<View style={styles.container}>
				<Text style={styles.welcome}>Welcome to React Native!</Text>
				<Text style={styles.instructions}>To get started, edit App.js</Text>
				<Text style={styles.instructions}>{instructions}</Text>
				<Button title={'Show Dialog (Alert)'} onPress={() => this.onTouched('alert')} />
				<Button title={'Show Dialog (PopupDialog)'} onPress={() => this.setState({ isVisible: true })} />

				<DateTimePicker
					isVisible={this.state.isVisible}
					mode={'datetime'}
					onConfirm={() => undefined}
					onCancel={() => undefined}
				/>
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
