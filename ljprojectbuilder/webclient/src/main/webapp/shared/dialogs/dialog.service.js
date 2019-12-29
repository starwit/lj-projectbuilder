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
	    			cancel: 'error.dialog.correct',
	    			refresh: 'error.dialog.continue',
	    			validationErrors: null,
	    			gotoAfter: null
	    		},
	    		showDialog : showDialog,
	    		closeDialog: closeDialog,
	    		closeDialogWithErrors: closeDialogWithErrors,
	    		resetAndContinue: resetAndContinue,
	    		showValidationDialog: showValidationDialog,
	    		showGeneratorValidationDialog: showGeneratorValidationDialog
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
		
		function showGeneratorValidationDialog(dialogtitle, dialogtext, validationErrors, dialogid, gotoAfter) {
			dialogService.dialog.title = dialogtitle;
			dialogService.dialog.text = dialogtext;
			dialogService.dialog.validationErrors = validationErrors;
			dialogService.dialog.cancel = 'error.dialog.generation.close';
			dialogService.dialog.refresh = 'error.dialog.generation.showtemplate',
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
		
		/**
		 * Closes error dialog
		 */
		function closeDialogWithErrors(dialogid) {
			document.getElementById(dialogid).style.display = 'none';
		};
		
		/**
		 * Reset changes, close dialog and continue.
		 */
		function resetAndContinue(dialogid) {
			document.getElementById(dialogid).style.display = 'none';
			dialogService.dialog.gotoAfter();
		};

	}];