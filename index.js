/* @flow */

import * as React from 'react';
import { Platform, Alert, ActionSheetIOS, NativeModules, NativeEventEmitter } from 'react-native';

const { RNNativeDialog } = NativeModules;
const RNNativeDialogEvents = new NativeEventEmitter(RNNativeDialog);

export type Id = number | string | Array<number> | Array<string>;
export type Items = Array<string> | Array<{ id: (string | number), title: string }>;

export type DialogProps = {
	title?: string,
	message?: string,

	positiveButton?: string,
	negativeButton?: string,
	neutralButton?: string,

	onPositivePress?: () => void,
	onNegativePress?: () => void,
	onNeutralPress?: () => void,

	cancellable?: boolean,
	cancelOnTouchOutside?: boolean,
	onDismiss?: () => void,

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
	items: Items,
	selectedItems?: Id,

	onItemSelect?: (Id) => void,
}

export type ProgressDialogProps = {
	...DialogProps,

	size: 'large' | 'small',
}

const defaultDialogProps = {
	cancellable: true,
	cancelOnTouchOutside: true,

	theme: 'light',
	accentColor: '#007aff',
};

const defaultInputDialogProps = {
	keyboardType: 'default',
	autoCorrect: false,
	autoFocus: false,
	autoCapitalize: 'none',
	selectTextOnFocus: false,
	secureTextEntry: false,
};

const defaultItemsDialogProps = {
	mode: 'default',
};

const defaultProgressDialogProps = {
	cancellable: false,
	cancelOnTouchOutside: false,

	size: 'large',
};

const EVENT_POSITIVE_BUTTON = 'native_dialog__positive_button';
const EVENT_NEGATIVE_BUTTON = 'native_dialog__negative_button';
const EVENT_NEUTRAL_BUTTON = 'native_dialog__neutral_button';
const EVENT_DISMISS_DIALOG = 'native_dialog__dismiss_dialog';

const removeAllListeners = () => {
	RNNativeDialogEvents.removeAllListeners(EVENT_POSITIVE_BUTTON);
	RNNativeDialogEvents.removeAllListeners(EVENT_NEGATIVE_BUTTON);
	RNNativeDialogEvents.removeAllListeners(EVENT_NEUTRAL_BUTTON);
	RNNativeDialogEvents.removeAllListeners(EVENT_DISMISS_DIALOG);
};

export default class ModalAlert {
	static showDialog = (props: DialogProps) => {
		if (!props) return;
		props = { ...defaultDialogProps, ...props };

		RNNativeDialogEvents.addListener(EVENT_POSITIVE_BUTTON, () => {
			const { onPositivePress } = props;
			if (onPositivePress) onPositivePress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_NEGATIVE_BUTTON, () => {
			const { onNegativePress } = props;
			if (onNegativePress) onNegativePress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_NEUTRAL_BUTTON, () => {
			const { onNeutralPress } = props;
			if (onNeutralPress) onNeutralPress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_DISMISS_DIALOG, () => {
			const { onDismiss } = props;
			if (onDismiss) onDismiss();
			removeAllListeners();
		});

		RNNativeDialog.showDialog(props);
	};

	static showInputDialog = (props: InputDialogProps) => {
		if (!props) return;
		props = {
			...defaultDialogProps,
			...defaultInputDialogProps,
			...props,
		};

		RNNativeDialogEvents.addListener(EVENT_POSITIVE_BUTTON, ({ value } = {}) => {
			const { onPositivePress } = props;
			if (onPositivePress) onPositivePress(value);
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_NEGATIVE_BUTTON, ({ value } = {}) => {
			const { onNegativePress } = props;
			if (onNegativePress) onNegativePress(value);
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_NEUTRAL_BUTTON, ({ value } = {}) => {
			const { onNeutralPress } = props;
			if (onNeutralPress) onNeutralPress(value);
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_DISMISS_DIALOG, () => {
			const { onDismiss } = props;
			if (onDismiss) onDismiss();
			removeAllListeners();
		});

		RNNativeDialog.showInputDialog(props);
	};

	static showItemsDialog = (props: ItemsDialogProps) => {
		if (!props || !props.items || !Array.isArray(props.items)) return;
		props = {
			...defaultDialogProps,
			...defaultItemsDialogProps,
			...props,
		};

		const { items, mode } = props;
		if (items.every(x => typeof x === 'string')) {
			props.items = items.map((item, index) => ({ id: index, title: item }));
		} else if (!items.every(x => typeof x === 'object' && 'id' in x && 'title' in x))
			return;

		props.message = undefined;
		if (mode !== 'single' && mode !== 'multiple')
			props.positiveButton = undefined;

		RNNativeDialogEvents.addListener(EVENT_NEGATIVE_BUTTON, () => {
			const { onNegativePress } = props;
			if (onNegativePress) onNegativePress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_NEUTRAL_BUTTON, () => {
			const { onNeutralPress } = props;
			if (onNeutralPress) onNeutralPress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_POSITIVE_BUTTON, (value) => {
			const { onItemSelect, mode } = props;

			if (onItemSelect && value && Array.isArray(value)) {
				if (mode === 'multiple')
					onItemSelect(value);
				else
					onItemSelect(value.length > 0 ? value[0] : -1);
			}
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_DISMISS_DIALOG, () => {
			const { onDismiss } = props;
			if (onDismiss) onDismiss();
			removeAllListeners();
		});

		RNNativeDialog.showItemsDialog(props);
	};

	static showProgressDialog = (props: ProgressDialogProps) => {
		if (!props) return;
		props = {
			...defaultDialogProps,
			...defaultProgressDialogProps,
			...props,
		};

		RNNativeDialogEvents.addListener(EVENT_POSITIVE_BUTTON, () => {
			const { onPositivePress } = props;
			if (onPositivePress) onPositivePress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_NEGATIVE_BUTTON, () => {
			const { onNegativePress } = props;
			if (onNegativePress) onNegativePress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_NEUTRAL_BUTTON, () => {
			const { onNeutralPress } = props;
			if (onNeutralPress) onNeutralPress();
			removeAllListeners();
		});
		RNNativeDialogEvents.addListener(EVENT_DISMISS_DIALOG, () => {
			const { onDismiss } = props;
			if (onDismiss) onDismiss();
			removeAllListeners();
		});

		RNNativeDialog.showProgressDialog(props);
	};
}
