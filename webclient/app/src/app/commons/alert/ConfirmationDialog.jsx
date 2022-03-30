import * as React from "react";
import { useState } from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import { useTranslation } from "react-i18next";
import { DialogContent, DialogContentText, DialogTitle } from "@mui/material";
import PropTypes from "prop-types";
import { LoadingButton } from "@mui/lab";

export default function ConfirmationDialog(props) {
    const { open, onClose, onSubmit, title, message, confirmTitle } = props;
    const { t } = useTranslation();
    const [isProcessing, setIsProcessing] = useState(false);

    function confirmAction() {
        setIsProcessing(true);
        onSubmit()
            .then(() => {
                onClose();
                setIsProcessing(false);
            })
            .catch(() => {
                setIsProcessing(false);
            });
    }

    return (
        <Dialog
            open={open}
            onClose={onClose}
            aria-labelledby="confirm-dialog-title"
            aria-describedby="confirm-dialog-description"
        >
            <DialogTitle id="confirm-dialog-title">{title}</DialogTitle>
            <DialogContent>
                <DialogContentText id="confirm-dialog-description">{message}</DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>{t("button.cancel")}</Button>
                <LoadingButton loading={isProcessing} onClick={confirmAction} autoFocus>
                    {confirmTitle ? confirmTitle : t("button.yes")}
                </LoadingButton>
            </DialogActions>
        </Dialog>
    );
}

ConfirmationDialog.propTypes = {
    onSubmit: PropTypes.func.isRequired,
    onClose: PropTypes.func.isRequired,
    title: PropTypes.string,
    message: PropTypes.string,
    open: PropTypes.bool,
    confirmTitle: PropTypes.string,
};
