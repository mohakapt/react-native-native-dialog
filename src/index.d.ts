export type Id<T extends string | number> = T | T[];
export type Items<T extends string | number> = string[] | { id: T, title: string }[];
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

type ItemsDialogEvents<T extends string | number> = CommonDialogEvents & {
	onItemSelect?: (selectedId: Id<T>) => void,
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

export type ItemsDialogProps<T extends string | number> = CommonDialogProps & ItemsDialogEvents<T> & {
	mode?: 'default' | 'single' | 'multiple',
	items: Items<T>,
	selectedItems?: Id<T>,
}

export type NumberPickerDialogProps = CommonDialogProps & NumberPickerDialogEvents & {
	value: number,
	minValue?: number,
	maxValue?: number,
}

export type RatingDialogProps = CommonDialogProps & CommonDialogEvents & {
	mode?: 'rose' | 'bar',
	value: 0 | 1 | 2 | 3 | 4 | 5,
}

declare const showDialog: (props: DialogProps) => void;
declare const showInputDialog: (props: InputDialogProps) => void;
declare const showItemsDialog: <T extends string | number>(props: ItemsDialogProps<T>) => void;
declare const showNumberPickerDialog: (props: NumberPickerDialogProps) => void;
declare const showRatingDialog: (props: RatingDialogProps) => void;

export {
	showDialog,
	showInputDialog,
	showItemsDialog,
	showNumberPickerDialog,
	showRatingDialog,
};
