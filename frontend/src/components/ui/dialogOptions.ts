export const dialogOptions = {
  showIcon: false, // Whether to show icon.
  closable: false, // Whether to show close icon
  maskClosable: true, // Whether the dialog can be closed by clicking the mask
  style: {
    width: '360px',
  },
  onNegativeClick: () => console.debug('onNegativeClick'),
}
