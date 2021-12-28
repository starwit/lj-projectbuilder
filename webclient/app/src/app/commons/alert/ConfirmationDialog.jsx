import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';
import { useTranslation } from 'react-i18next';

export default function ConfirmationDialog(props) {
    const { content, open, onClose, onSubmit, title, message } = props;
    const { t } = useTranslation();

    return (
        <Dialog
            open={open}
            onClose={onClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <Alert severity="warning">
                <AlertTitle>{content ? content.title : title}</AlertTitle>
                {content ? content.message : message}
            </Alert>
            <DialogActions>
                <Button onClick={onClose}>{t("button.no")}</Button>
                <Button onClick={onSubmit} autoFocus>
                    {t("button.yes")}
                </Button>
            </DialogActions>
        </Dialog>
    );
}