import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';
import { useTranslation } from 'react-i18next';

export default function SuccessDialog(props) {
    const { content, open, onClose } = props;
    const { t } = useTranslation();

    return (
        <Dialog
            open={open}
            onClose={onClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <Alert severity="success">
                <AlertTitle>{t(content.title)}</AlertTitle>
                {t(content.message)}
            </Alert>
            <DialogActions>
                <Button onClick={onClose} autoFocus>{t("button.ok")}</Button>
            </DialogActions>
        </Dialog>
    );
}