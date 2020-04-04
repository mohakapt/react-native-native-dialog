import * as React from 'react';
import { NativeEventEmitter, NativeModules, Platform } from 'react-native';

const { RNNativeDialog } = NativeModules;
const RNNativeDialogEvents = new NativeEventEmitter(RNNativeDialog);

const defaultDialogProps = {
	cancellable: true,
	cancelOnTouchOutside: true,

	theme: 'light',
	accentColor: '#007aff',

	buttonAlignment: 'default',
	transitionStyle: 'bounceUp',
	preferredWidth: 340,
	preferredStyle: 'popupDialog',
	hideStatusBar: false,

	positiveButtonStyle: 'default',
	negativeButtonStyle: 'cancel',
	neutralButtonStyle: 'default',
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

const defaultNumberPickerDialogProps = {
	minValue: 0,
	maxValue: 100,
};

const defaultRatingDialogProps = {};

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

const checkIfSupported = (ios, android, iosCondition = true, androidCondition = true) => {
	const supported = (iosCondition && ios && Platform.OS === 'ios') || (androidCondition && android && Platform.OS === 'android');
	if (!supported) {
		console.warn('This feature is not supported yet, If you have the time and the skill please consider spending some time to help implementing it.');
	}
	return supported;
};

export default {
	showDialog(props) {
		if (!checkIfSupported(true, true)) return;

		if (!props) return;
		props = { ...defaultDialogProps, ...props };

		RNNativeDialog.showDialog(props).then(({ action }) => {
			switch (action) {
				case 'positive':
					const { onPositivePress } = props;
					if (onPositivePress) onPositivePress();
					return;
				case 'negative':
					const { onNegativePress } = props;
					if (onNegativePress) onNegativePress();
					return;
				case 'neutral':
					const { onNeutralPress } = props;
					if (onNeutralPress) onNeutralPress();
					return;
				case 'dismiss':
					const { onDismiss } = props;
					if (onDismiss) onDismiss();
					return;
			}
		});
	},

	showItemsDialog(props) {
		if (!checkIfSupported(true, true)) return;

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

		RNNativeDialog.showItemsDialog(props).then(({ action, value }) => {
			switch (action) {
				case 'positive':
					const { onItemSelect, mode } = props;

					if (onItemSelect && value && Array.isArray(value)) {
						if (mode === 'multiple')
							onItemSelect(value);
						else
							onItemSelect(value.length > 0 ? value[0] : -1);
					}
					return;
				case 'negative':
					const { onNegativePress } = props;
					if (onNegativePress) onNegativePress();
					return;
				case 'neutral':
					const { onNeutralPress } = props;
					if (onNeutralPress) onNeutralPress();
					return;
				case 'dismiss':
					const { onDismiss } = props;
					if (onDismiss) onDismiss();
					return;
			}
		});
	},

	showInputDialog(props) {
		if (!checkIfSupported(true, true)) return;

		if (!props) return;
		props = {
			...defaultDialogProps,
			...defaultInputDialogProps,
			...props,
		};

		RNNativeDialog.showInputDialog(props).then(({ action, value }) => {
			switch (action) {
				case 'positive':
					const { onPositivePress } = props;
					if (onPositivePress) onPositivePress(value);
					return;
				case 'negative':
					const { onNegativePress } = props;
					if (onNegativePress) onNegativePress(value);
					return;
				case 'neutral':
					const { onNeutralPress } = props;
					if (onNeutralPress) onNeutralPress(value);
					return;
				case 'dismiss':
					const { onDismiss } = props;
					if (onDismiss) onDismiss();
					return;
			}
		}).catch(() => console.warn('I am here'));
	},

	showNumberPickerDialog(props) {
		if (!checkIfSupported(false, true)) return;

		if (!props) return;
		props = {
			...defaultDialogProps,
			...defaultNumberPickerDialogProps,
			...props,
		};

		RNNativeDialog.showNumberPickerDialog(props).then(({ action, value }) => {
			switch (action) {
				case 'positive':
					const { onPositivePress } = props;
					if (onPositivePress) onPositivePress(value);
					return;
				case 'negative':
					const { onNegativePress } = props;
					if (onNegativePress) onNegativePress(value);
					return;
				case 'neutral':
					const { onNeutralPress } = props;
					if (onNeutralPress) onNeutralPress(value);
					return;
				case 'dismiss':
					const { onDismiss } = props;
					if (onDismiss) onDismiss();
					return;
			}
		});
	},

	showRatingDialog(props) {
		if (!checkIfSupported(false, false)) return;

		if (!props) return;
		props = {
			...defaultDialogProps,
			...defaultRatingDialogProps,
			...props,
		};
	},
};
