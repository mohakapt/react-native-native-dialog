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

type CommonDialogProps = {
	title?: string,
	message?: string,

	cancellable?: boolean,
	cancelOnTouchOutside?: boolean,

	theme?: 'light' | 'dark',
	accentColor?: string,

	positiveButton?: string,
	negativeButton?: string,
	neutralButton?: string,
}

type CommonDialogEvents = {
	onPositivePress?: () => void,
	onNegativePress?: () => void,
	onNeutralPress?: () => void,
	onDismiss?: () => void,
}

export type DialogProps = CommonDialogProps & IosDialogProps & AndroidDialogProps & CommonDialogEvents;

export type InputDialogProps = CommonDialogProps & IosDialogProps & AndroidDialogProps & {
	value?: string,
	placeholder?: string,
	keyboardType?: 'default' | 'number-pad' | 'decimal-pad' | 'numeric' | 'email-address' | 'phone-pad',
	maxLength?: number,
	autoCorrect?: boolean,
	autoFocus?: boolean,
	autoCapitalize?: 'characters' | 'words' | 'sentences' | 'none',
	selectTextOnFocus?: boolean,
	secureTextEntry?: boolean,

	onPositivePress?: (value: string) => void,
	onNegativePress?: (value: string) => void,
	onNeutralPress?: (value: string) => void,
}

export type ItemsDialogProps = DialogProps & {
	mode?: 'default' | 'single' | 'multiple',
	items: Items,
	selectedItems?: Id,

	onItemSelect?: (selectedId: Id) => void,
}

export type ProgressDialogProps = DialogProps & {
	size?: 'large' | 'small',
}

export type TipDialogProps = DialogProps & {
	image?: any,
	id?: string,
	dontShowAgain?: string,
	force?: boolean,
}

declare const showDialog: (props: DialogProps) => void;
declare const showInputDialog: (props: InputDialogProps) => void;
declare const showItemsDialog: (props: ItemsDialogProps) => void;
declare const showProgressDialog: (props: ProgressDialogProps) => void;
declare const showTipDialog: (props: TipDialogProps) => void;

export { showDialog, showInputDialog, showItemsDialog, showProgressDialog, showTipDialog };
