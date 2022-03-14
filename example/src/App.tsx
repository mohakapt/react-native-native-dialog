import React from 'react';
import { Button, View } from 'react-native';
import NativeDialog from 'react-native-native-dialog';

export default function App() {
	const onTouched = (style: 'alert' | 'popupDialog', theme: 'light' | 'dark') => {
		// NativeDialog.showDialog({
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
		// 	theme,
		// 	accentColor: '#ff4a9e',
		//
		// 	onPositivePress: (input: string) => console.warn('positive - ', input),
		// 	onNegativePress: (input: string) => console.warn('negative - ', input),
		// 	onNeutralPress: (input: string) => console.warn('neutral - ', input),
		//
		// 	onDismiss: () => console.warn('dismiss'),
		// });

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
		// 	theme,
		// 	accentColor: '#ff4a9e',
		//
		// 	value: 'asdf',
		// 	placeholder: 'Enter your password',
		// 	keyboardType: 'number-pad',
		//
		// 	autoFocus: true,
		// 	maxLength: 5,
		//
		// 	onPositivePress: (input: string) => console.warn('positive - ', input),
		// 	onNegativePress: (input: string) => console.warn('negative - ', input),
		// 	onNeutralPress: (input: string) => console.warn('neutral - ', input),
		//
		// 	onDismiss: () => console.warn('dismiss'),
		// });

		// NativeDialog.showItemsDialog({
		// 	title: 'How would you like to pay for your meal today?',
		//
		// 	mode: 'default',
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
		// 	theme,
		// 	accentColor: '#1cc35d',
		//
		// 	positiveButton: 'Select',
		// 	negativeButton: 'Cancel',
		//
		// 	onItemSelect: (selectedId: string) => {
		// 		console.warn(selectedId);
		// 	},
		// 	onNegativePress: () => console.warn('negative'),
		// 	onDismiss: () => console.warn('dismiss'),
		// });

		// NativeDialog.showNumberPickerDialog({
		// 	title: 'Pick a Number',
		//
		// 	preferredStyle: style,
		// 	theme,
		// 	accentColor: '#ff4a9e',
		//
		// 	minValue: -6,
		// 	maxValue: 6,
		// 	value: 5,
		// 	positiveButton: 'Pick',
		//
		// 	onPositivePress: (input: string) => console.warn('positive - ', input),
		// 	onNegativePress: (input: string) => console.warn('negative - ', input),
		// 	onNeutralPress: (input: string) => console.warn('neutral - ', input),
		//
		// 	onDismiss: () => console.warn('dismiss'),
		//
		// });

		// NativeDialog.showDatePickerDialog({
		// 	theme,
		// 	date: '01/01/1996',
		// });

		NativeDialog.showRatingDialog({
			title: 'Rate The App',

			preferredStyle: style,
			theme,
			accentColor: '#d93b3b',

			mode: 'rose',
			value: 2,

			positiveButton: 'Submit',
			negativeButton: 'Don\'t Ask Again',
			neutralButton: 'Remind Me Later',

			onPositivePress: (input: string) => console.warn('positive - ', input),
			onNegativePress: (input: string) => console.warn('negative - ', input),
			onNeutralPress: (input: string) => console.warn('neutral - ', input),

			onDismiss: () => console.warn('dismiss'),
		});
	};

	return (
		<View style={{ flex: 1, justifyContent: 'center' }}>
			<Button title={'Show Dialog (Alert) - Light'} onPress={() => onTouched('alert', 'light')} />
			<Button title={'Show Dialog (PopupDialog) - Light'}
			        onPress={() => onTouched('popupDialog', 'light')} />
			<Button title={'Show Dialog (Alert) - Dark'} onPress={() => onTouched('alert', 'dark')} />
			<Button title={'Show Dialog (PopupDialog) - Dark'}
			        onPress={() => onTouched('popupDialog', 'dark')} />
		</View>
	);
}
