dialogService = [function dialogService() {
	    var dialogService = {
	    		dialog : {
	    			id : {
	    				success: 'successdialog', 
	    				error: 'errordialog',
	    				fileimport: 'importdialog'
	    			},
	    			title: 'dialog.title',
	    			text: 'dialog.text',
	    			validationErrors: null,
	    			gotoAfter: null
	    		},
	    		showDialog : showDialog,
	    		closeDialog: closeDialog,
	    		showValidationDialog: showValidationDialog
	     };
	    return dialogService;
	    
		/**
		 * Display dialog after saving project configuration.
		 */
		function showDialog(dialogtitle, dialogtext, dialogid, gotoAfter) {
			dialogService.dialog.title = dialogtitle;
			dialogService.dialog.text = dialogtext;
			dialogService.dialog.gotoAfter = gotoAfter;
			document.getElementById(dialogid).style.display = 'block';
		};

		/**
		 * Display dialog with errors after saving failed.
		 */
		function showValidationDialog(dialogtitle, dialogtext, validationErrors, dialogid, gotoAfter) {
			dialogService.dialog.title = dialogtitle;
			dialogService.dialog.text = dialogtext;
			dialogService.dialog.validationErrors = validationErrors;
			dialogService.dialog.gotoAfter = gotoAfter;
			document.getElementById(dialogid).style.display = 'block';
		};
		
		/**
		 * Closes the dialog configured in showDialog.
		 */
		function closeDialog(dialogid) {
			document.getElementById(dialogid).style.display = 'none';
			dialogService.dialog.gotoAfter();
		};

	}];