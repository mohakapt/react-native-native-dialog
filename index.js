/* @flow */

import * as React from 'react';
import { Platform, Alert, ActionSheetIOS, NativeModules, NativeEventEmitter } from 'react-native';

export type Item = Array<string> | Array<{ id: (string | number), title: string }>;
export type Id = number | string | Array<number> | Array<string>;

export type DialogProps = {
	title?: string,
	message?: string,

	positiveButton?: string,
	negativeButton?: string,
	neutralButton?: string,

	onPositivePress?: () => void,
	onNegativePress?: () => void,
	onNeutralPress?: () => void,

	theme?: 'light' | 'dark',
	accentColor?: string,
}

export type InputDialogProps = {
	...DialogProps,

	value?: string,
	placeholder?: string,
	keyboardType?: 'default' | 'number-pad' | 'decimal-pad' | 'numeric' | 'email-address' | 'phone-pad',
	maxLength?: number,
	autoCorrect?: boolean,
	autoFocus?: boolean,
	autoCapitalize?: 'characters' | 'words' | 'sentences' | 'none',
	selectTextOnFocus?: boolean,
	secureTextEntry?: boolean,

	onPositivePress?: (string) => void,
	onNegativePress?: (string) => void,
	onNeutralPress?: (string) => void,
}

export type ItemsDialogProps = {
	...DialogProps,

	mode?: 'default' | 'single' | 'multiple',
	items: Item,
	selectedItems?: Id,

	onItemSelect?: (Id) => void,
}

export default class ModalAlert {
	static showDialog = (props: DialogProps) => {
		if (!props) return;

		const { RNNativeDialog } = NativeModules;
		const RNNativeDialogEvents = new NativeEventEmitter(RNNativeDialog);

		const removeAllListeners = () => {
			RNNativeDialogEvents.removeAllListeners('native_dialog__positive_button');
			RNNativeDialogEvents.removeAllListeners('native_dialog__negative_button');
			RNNativeDialogEvents.removeAllListeners('native_dialog__neutral_button');
		};

		RNNativeDialogEvents.addListener('native_dialog__positive_button', () => {
			const { onPositivePress } = props;
			if (onPositivePress) onPositivePress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener('native_dialog__negative_button', () => {
			const { onNegativePress } = props;
			if (onNegativePress) onNegativePress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener('native_dialog__neutral_button', () => {
			const { onNeutralPress } = props;
			if (onNeutralPress) onNeutralPress();
			removeAllListeners();
		});

		RNNativeDialog.showDialog(props);
	};

	static showInputDialog = (props: InputDialogProps) => {
		if (!props) return;

		const { RNNativeDialog } = NativeModules;
		const RNNativeDialogEvents = new NativeEventEmitter(RNNativeDialog);

		const removeAllListeners = () => {
			RNNativeDialogEvents.removeAllListeners('native_dialog__positive_button');
			RNNativeDialogEvents.removeAllListeners('native_dialog__negative_button');
			RNNativeDialogEvents.removeAllListeners('native_dialog__neutral_button');
		};

		RNNativeDialogEvents.addListener('native_dialog__positive_button', ({ value } = {}) => {
			const { onPositivePress } = props;
			if (onPositivePress) onPositivePress(value);
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener('native_dialog__negative_button', ({ value } = {}) => {
			const { onNegativePress } = props;
			if (onNegativePress) onNegativePress(value);
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener('native_dialog__neutral_button', ({ value } = {}) => {
			const { onNeutralPress } = props;
			if (onNeutralPress) onNeutralPress(value);
			removeAllListeners();
		});

		RNNativeDialog.showInputDialog(props);
	};

	static showItemsDialog = (props: ItemsDialogProps) => {
		if (!props || !props.items || !Array.isArray(props.items)) return;

		const { items, mode } = props;
		if (items.every(x => typeof x === 'string')) {
			props.items = items.map((item, index) => ({ id: index, title: item }));
		} else if (!items.every(x => typeof x === 'object' && 'id' in x && 'title' in x))
			return;

		props.message = undefined;
		if (mode !== 'single' && mode !== 'multiple')
			props.positiveButton = undefined;

		const { RNNativeDialog } = NativeModules;
		const RNNativeDialogEvents = new NativeEventEmitter(RNNativeDialog);

		const removeAllListeners = () => {
			RNNativeDialogEvents.removeAllListeners('native_dialog__positive_button');
			RNNativeDialogEvents.removeAllListeners('native_dialog__negative_button');
			RNNativeDialogEvents.removeAllListeners('native_dialog__neutral_button');
		};

		RNNativeDialogEvents.addListener('native_dialog__negative_button', () => {
			const { onNegativePress } = props;
			if (onNegativePress) onNegativePress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener('native_dialog__neutral_button', () => {
			const { onNeutralPress } = props;
			if (onNeutralPress) onNeutralPress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener('native_dialog__positive_button', (value) => {
			const { onItemSelect, mode } = props;

			if (onItemSelect && value && Array.isArray(value)) {
				if (mode === 'multiple')
					onItemSelect(value);
				else
					onItemSelect(value.length > 0 ? value[0] : -1);
			}
			removeAllListeners();
		});

		RNNativeDialog.showItemsDialog(props);
	};
}
