dialogService = [function dialogService() {
	    var dialogService = {
	    		dialog : {
	    			id : {
	    				success: 'successdialog', 
	    				error: 'errordialog'
	    			},
	    			title: 'dialog.title',
	    			text: 'dialog.text',
	    			gotoAfter: null
	    		},
	    		showDialog : showDialog,
	    		closeDialog: closeDialog
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
		 * Closes the dialog configured in showDialog.
		 */
		function closeDialog(dialogid) {
			document.getElementById(dialogid).style.display = 'none';
			dialogService.dialog.gotoAfter();
		};

	}];