export type Id = number | string | Array<number> | Array<string>;
export type Items = Array<string> | Array<{ id: (string | number), title: string }>;

type CommonDialogProps = {
	title?: string,
	message?: string,

	cancellable?: boolean,
	cancelOnTouchOutside?: boolean,
	onDismiss?: () => void,

	theme?: 'light' | 'dark',
	accentColor?: string,
}

type CommonDialogButtons = {
	positiveButton?: string,
	negativeButton?: string,
	neutralButton?: string,
}

type CommonDialogEvents = {
	onPositivePress?: () => void,
	onNegativePress?: () => void,
	onNeutralPress?: () => void,
}

export type DialogProps = CommonDialogProps & CommonDialogButtons & CommonDialogEvents;

export type InputDialogProps = CommonDialogProps & CommonDialogButtons & {
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
