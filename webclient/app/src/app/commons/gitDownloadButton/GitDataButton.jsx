import React, { useState, useEffect } from "react";
import { Button, Container, DialogTitle, Typography, IconButton, Box } from '@mui/material';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import { useTranslation } from 'react-i18next';
import { CloudSync, Close } from "@mui/icons-material";
import ErrorAlert from "../alert/ErrorAlert";
import NotificationDialog from "../alert/NotificationDialog";
import ValidatedTextField from "../validatedTextField/ValidatedTextField";
import RegexConfig from "../../../regexConfig";
import GitDataButtonStyles from "./GitDataButtonStyles";

function GitDataButton(props) {
    const { credentialsRequired, handleGit, handleAfterSuccess, buttonIcon, buttonName, buttonVariant } = props;
    const gitDataButtonStyles = GitDataButtonStyles();
    const [hasFormError, setHasFormError] = React.useState(false);    
    const [downloadRequestData, setDownloadRequestData] = useState({ "username": "", "password": "" });
    const [alert, setAlert] = useState({"open":false, "title": "ERROR", "message": ""});
    const { t } = useTranslation();

    const [openAuthDialog, setOpenAuthDialog] = useState(false);
  

    const handleChange = (event) => {
        const { name, value } = event.target;
        let downloadRequestDataNew = { ...downloadRequestData };
        downloadRequestDataNew[name] = value;
        setDownloadRequestData(downloadRequestDataNew);
    }

    const handleLogin = () => {
        if (credentialsRequired) {
            handleAlertClose();
            setOpenAuthDialog(true);
        } else {
            handleAppTemplateReload();
        }
    }

    const handleAppTemplateReload = () => {
        if(hasFormError && credentialsRequired) {
            return;
        }
        handleGit(downloadRequestData).then(() => {
            handleAfterSuccess();
            setOpenAuthDialog(false);
        }).catch(err => {
            setAlert({"open":true, "title": t("alert.error"), "message": t(err.response.data.messageKey)});
            console.log(err.response.data);
        });
    }

    const handleAlertClose = () => {
        setAlert({"open":false, "title": "ERROR", "message": ""});
    }

    const onClose = () => {
        setDownloadRequestData({ "username": "", "password": "" });
        setOpenAuthDialog(false);
    }

    useEffect(() => {
        if (!downloadRequestData) {
            return;
        }
        let hasError = false;

        if (!RegexConfig.appTemplateAuthUser.test(downloadRequestData.username)) {
            hasError = true;
        }
        if (!RegexConfig.appTemplateAuthPassword.test(downloadRequestData.password)) {
            hasError = true;
        }
        setHasFormError(hasError);
    }, [downloadRequestData, hasFormError])


    return (
        <Container>
            <Button onClick={handleLogin} startIcon={buttonIcon ? buttonIcon : <CloudSync />} variant={buttonVariant} >{buttonName}</Button>
            <NotificationDialog 
                open={alert.open && !credentialsRequired} 
                onClose={handleAlertClose} 
                severity="error" 
                title={t(alert.title)} 
                message={t(alert.message)} 
            />
            <Dialog open={openAuthDialog} onClose={onClose} spacing={2}>
                <DialogTitle className={gitDataButtonStyles.dialogHeaderBar}>
                    <Typography noWrap variant={"h6"} component={"p"}>
                        {t("appTemplateAuthDialog.title")}
                    </Typography>
                    <div className={gitDataButtonStyles.flex} />
                    <IconButton
                        aria-label="close"
                        onClick={onClose}
                    >
                        <Close />
                    </IconButton>
                </DialogTitle>
                <Box
                    component="form"
                    sx={{
                        '& .MuiTextField-root': { m: 1, width: '95%' },

                    }}
                    noValidate
                    autoComplete="off"
                >
                    <ErrorAlert alert={alert} />
                    <ValidatedTextField
                        fullWidth
                        label={t("appTemplateAuthDialog.user") + "*"}
                        value={downloadRequestData.username}
                        name="username"
                        onChange={handleChange}
                        isCreate={true}
                        helperText={t("appTemplateAuthDialog.user.error")}
                        regex={RegexConfig.appTemplateAuthUser}
                    />
                    <ValidatedTextField
                        type="password"
                        fullWidth
                        label={t("appTemplateAuthDialog.password") + "*"}
                        value={downloadRequestData.password}
                        name="password"
                        onChange={handleChange}
                        isCreate={true}
                        helperText={t("appTemplateAuthDialog.password.error")}
                        autoComplete="on"
                        regex={RegexConfig.appTemplateAuthPassword}
                    />
                    <DialogActions>
                        <Button onClick={onClose}>{t("button.cancel")}</Button>
                        <Button disabled={hasFormError} onClick={handleAppTemplateReload} autoFocus>
                            {t("button.ok")}
                        </Button>
                    </DialogActions>
                </Box>
            </Dialog>
        </Container>
    );
}
export default GitDataButton;
