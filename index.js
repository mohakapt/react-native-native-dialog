import * as React from 'react';
import { NativeModules, Platform } from 'react-native';

const { RNNativeDialog } = NativeModules;

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
		} else if (!items.every(x => typeof x === 'object' && 'id' in x && 'title' in x && typeof x['title'] === 'string')) {
			console.warn('Unable to understand "items", Dialog items can be either Array<string> or Array<{ id: number | string, title: string }>');
			return;
		}

		props.message = undefined;
		if (mode !== 'single' && mode !== 'multiple')
			props.positiveButton = undefined;

		if ((mode === 'single' || mode === 'multiple') && props.preferredStyle !== 'popupDialog') {
			console.warn('UIAlertController doesn\'t support "single" or "multiple" modes, Consider using "popupDialog" in "preferredStyle"');
			return;
		}

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
		if (!checkIfSupported(true, true)) return;

		if (!props) return;
		props = {
			...defaultDialogProps,
			...defaultNumberPickerDialogProps,
			...props,
		};

		const { value, minValue, maxValue } = props;
		if (minValue > maxValue) {
			console.warn('"minValue" must be less or equal to "maxValue"');
			return;
		}

		if (value < minValue || value > maxValue) {
			console.warn('"value" must be in range between "minValue" and "maxValue"');
			return;
		}

		if (props.preferredStyle !== 'popupDialog') {
			console.warn('UIAlertController doesn\'t support UIPickerView, Consider using "popupDialog" in "preferredStyle"');
			return;
		}

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
