import { Moment } from 'moment';

export type Id = number | string | Array<number> | Array<string>;
export type Items = Array<string> | Array<{ id: (string | number), title: string }>;
export type IosButtonType = 'default' | 'cancel' | 'destructive';

type IosDialogProps = {
	buttonAlignment?: 'default' | 'vertical' | 'horizontal',
	transitionStyle?: 'bounceUp' | 'bounceDown' | 'zoomIn' | 'fadeIn',
	preferredWidth?: number,
	preferredStyle?: 'popupDialog' | 'alert' | 'actionSheet',
	hideStatusBar?: boolean,

	positiveButtonStyle?: IosButtonType,
	negativeButtonStyle?: IosButtonType,
	neutralButtonStyle?: IosButtonType,
}

type AndroidDialogProps = {}

type CommonDialogProps = IosDialogProps & AndroidDialogProps & {
	title?: string,
	message?: string,

	cancellable?: boolean,
	cancelOnTouchOutside?: boolean,

	theme?: 'light' | 'dark',
	accentColor?: string,

	positiveButton?: string,
	negativeButton?: string,
	neutralButton?: string,

	onDismiss?: () => void,
}

type CommonDialogEvents = {
	onPositivePress?: () => void,
	onNegativePress?: () => void,
	onNeutralPress?: () => void,
}

type InputDialogEvents = {
	onPositivePress?: (value: string) => void,
	onNegativePress?: (value: string) => void,
	onNeutralPress?: (value: string) => void,
}

type ItemsDialogEvents = {
	onItemSelect?: (selectedId: Id) => void,
}

type NumberPickerDialogEvents = {
	onPositivePress?: (value: number) => void,
	onNegativePress?: (value: number) => void,
	onNeutralPress?: (value: number) => void,
}

export type DialogProps = CommonDialogProps & CommonDialogEvents;

export type InputDialogProps = CommonDialogProps & InputDialogEvents & {
	value?: string,
	placeholder?: string,
	keyboardType?: 'default' | 'number-pad' | 'decimal-pad' | 'numeric' | 'email-address' | 'phone-pad',
	maxLength?: number,
	autoCorrect?: boolean,
	autoFocus?: boolean,
	autoCapitalize?: 'characters' | 'words' | 'sentences' | 'none',
	selectTextOnFocus?: boolean,
	secureTextEntry?: boolean,
}

export type ItemsDialogProps = CommonDialogProps & ItemsDialogEvents & {
	mode?: 'default' | 'single' | 'multiple',
	items: Items,
	selectedItems?: Id,
}

export type ProgressDialogProps = CommonDialogProps & CommonDialogEvents & {
	size?: 'large' | 'small',
}

export type TipDialogProps = CommonDialogProps & CommonDialogEvents & {
	image?: any,
	id?: string,
	dontShowAgain?: string,
	force?: boolean,
}

export type DatePickerDialogProps = CommonDialogProps & CommonDialogEvents & {
	date: Date | Moment,
	mode?: 'date' | 'time' | 'datetime',
	is24Hour?: boolean,
	minDate?: Date | Moment,
	maxDate?: Date | Moment,
}

export type NumberPickerDialogProps = CommonDialogProps & NumberPickerDialogEvents & {
	value: number,
	minValue?: number,
	maxValue?: number,
}

export type RatingDialogProps = CommonDialogProps & CommonDialogEvents & {}

declare const showDialog: (props: DialogProps) => void;
declare const showInputDialog: (props: InputDialogProps) => void;
declare const showItemsDialog: (props: ItemsDialogProps) => void;
declare const showProgressDialog: (props: ProgressDialogProps) => void;
declare const showTipDialog: (props: TipDialogProps) => void;
declare const showDatePickerDialog: (props: DatePickerDialogProps) => void;
declare const showNumberPickerDialog: (props: NumberPickerDialogProps) => void;
declare const showRatingDialog: (props: RatingDialogProps) => void;

export {
	showDialog,
	showInputDialog,
	showItemsDialog,
	showProgressDialog,
	showTipDialog,
	showDatePickerDialog,
	showNumberPickerDialog,
	showRatingDialog,
};
