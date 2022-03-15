import * as React from 'react';
import { NativeModules, Platform } from 'react-native';

const { NativeDialog } = NativeModules;

const defaultDialogProps = {
	cancellable: true,
	cancelOnTouchOutside: true,

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
	allowEmptyEntry: true,
};

const defaultItemsDialogProps = {
	mode: 'default',
};

const defaultNumberPickerDialogProps = {
	minValue: 0,
	maxValue: 100,
};

const defaultRatingDialogProps = {
	mode: 'rose',
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

		NativeDialog.showDialog(props)
			.then(({ action }) => {
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
			})
			.catch(console.error);
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

		if (Platform.OS === 'ios' && (mode === 'single' || mode === 'multiple') && props.preferredStyle !== 'popupDialog') {
			console.warn('UIAlertController doesn\'t support "single" or "multiple" modes, Consider using "popupDialog" in "preferredStyle"');
			return;
		}

		NativeDialog.showItemsDialog(props)
			.then(({ action, value }) => {
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
			})
			.catch(console.error);
	},

	showInputDialog(props) {
		if (!checkIfSupported(true, true)) return;

		if (!props) return;
		props = {
			...defaultDialogProps,
			...defaultInputDialogProps,
			...props,
		};

		NativeDialog.showInputDialog(props)
			.then(({ action, value }) => {
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
			})
			.catch(console.error);
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

		if (Platform.OS === 'ios' && props.preferredStyle !== 'popupDialog') {
			console.warn('UIAlertController doesn\'t support UIPickerView, Consider using "popupDialog" in "preferredStyle"');
			return;
		}

		NativeDialog.showNumberPickerDialog(props)
			.then(({ action, value }) => {
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
			})
			.catch(console.error);
	},

	showRatingDialog(props) {
		if (!checkIfSupported(true, true)) return;

		if (!props) return;
		props = {
			...defaultDialogProps,
			...defaultRatingDialogProps,
			...props,
		};

		if (props.value < 0 || props.value > 5) {
			console.warn('"value" must be between 0 and 5.');
			return;
		}

		if (Platform.OS === 'ios' && props.preferredStyle !== 'popupDialog') {
			console.warn('UIAlertController doesn\'t support custom, Consider using "popupDialog" in "preferredStyle"');
			return;
		}

		if (Platform.OS === 'android' && props.mode === 'rose') {
			console.warn('Android doesn\'t support rose styled rating views, Consider using "bar" in "mode"');
			return;
		}

		NativeDialog.showRatingDialog(props)
			.then(({ action, value }) => {
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
			})
			.catch(console.error);
	},
};
