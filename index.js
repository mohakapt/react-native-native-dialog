/* @flow */

import * as React from 'react';
import { Platform, Alert, ActionSheetIOS, NativeModules, NativeEventEmitter } from 'react-native';

export type AlertOptions = {
	title?: string,
	message?: ?string,

	positiveButton?: string,
	negativeButton?: string,
	naturalButton?: string,

	onPositivePress?: ?() => void,
	onNegativePress?: ?() => void,
	onNaturalPress?: ?() => void,

	positiveButtonStyle?: 'default' | 'cancel' | 'destructive';
	negativeButtonStyle?: 'default' | 'cancel' | 'destructive';
	naturalButtonStyle?: 'default' | 'cancel' | 'destructive';

	actionSheetIos?: boolean,
	accentColor?: string,
}

export type InputAlertOptions = {
	title?: string,
	message?: string,

	value?: string,
	placeholder?: string,
	keyboardType?: 'default' | 'number-pad' | 'decimal-pad' | 'numeric' | 'email-address' | 'phone-pad',
	maxLength?: number,
	autoCorrect?: boolean,
	autoFocus?: boolean,
	autoCapitalize?: 'characters' | 'words' | 'sentences' | 'none',
	selectTextOnFocus?: boolean,
	secureTextEntry?: boolean,

	positiveButton?: string,
	negativeButton?: string,

	onPositivePress?: (string) => void,
	onNegativePress?: (string) => void,
}

export type Id = number | string | Array<number> | Array<string>;
export type ItemsAlertOptions = {
	title?: string,
	mode?: 'default' | 'single' | 'multiple',

	items: Array<string> | Array<{ id: (string | number), title: string }>,
	selectedItems?: Id,

	positiveButton?: string,
	negativeButton?: string,

	onItemSelect?: (Id) => void,
	onNegativePress?: () => void,
}

class ModalAlert {
	static showInputAlert = (options: InputAlertOptions) => {
		const { RNNativeDialog } = NativeModules;
		const RNNativeDialogEvents = new NativeEventEmitter(RNNativeDialog);

		const removeAllListeners = () => {
			RNNativeDialogEvents.removeAllListeners('native_dialog__positive_button');
			RNNativeDialogEvents.removeAllListeners('native_dialog__negative_button');
		};

		RNNativeDialogEvents.addListener('native_dialog__positive_button', ({ value }) => {
			const { onPositivePress } = options;
			if (onPositivePress) onPositivePress(value);
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener('native_dialog__negative_button', ({ value }) => {
			const { onNegativePress } = options;
			if (onNegativePress) onNegativePress(value);
			removeAllListeners();
		});

		RNNativeDialog.showInputDialog(options);
	};
}

export default ModalAlert;
