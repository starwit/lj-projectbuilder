import React, { useState } from "react";
import { Button, TextField, Container, DialogTitle, Typography, IconButton, Box } from '@mui/material';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import { useTranslation } from 'react-i18next';
import AppTemplateRest from "../../services/AppTemplateRest";
import { CloudSync, Close } from "@mui/icons-material";
import ErrorAlert from "../alert/ErrorAlert";
import AppTemplateDialogStyles from "./AppTemplateDialogStyles";

function AppTemplateAuthDialog(props) {
    const { appTemplate, handleRefresh, setOpenSuccessDialog, setOpenErrorDialog } = props;
    const appTemplateDialogStyles = AppTemplateDialogStyles();
    const [downloadRequestData, setDownloadRequestData] = useState({ "appTemplateId": null, "username": null, "password": null });
    const { t } = useTranslation();

    const appTemplateRest = new AppTemplateRest();

    const [openAuthDialog, setOpenAuthDialog] = useState(false);
  

    const handleChange = (event) => {
        const { name, value } = event.target;
        let downloadRequestDataNew = { ...downloadRequestData };
        downloadRequestDataNew[name] = value;
        setDownloadRequestData(downloadRequestDataNew);
    }

    const handleLogin = () => {
        console.log("in handleLogin");
        if (appTemplate.credentialsRequired) {
            setOpenAuthDialog(true);
        } else {
            handleAppTemplateReload();
        }
    }

    const handleAppTemplateReload = () => {
        downloadRequestData.appTemplateId = appTemplate.id;
        appTemplateRest.updateTemplates(downloadRequestData).then(() => {
            handleRefresh();
            setOpenAuthDialog(false);
            setOpenSuccessDialog(true);
        }).catch(err => {
            setOpenErrorDialog(true);
            console.log(err.response.data);
        });
    }

    const onClose = () => {
        setDownloadRequestData({ "appTemplateId": null, "username": null, "password": null });
        setOpenAuthDialog(false);
    }

    return (
        <Container>
            <Button onClick={handleLogin} startIcon={<CloudSync />} >{t("button.loadtemplate")}</Button>
            <Dialog open={openAuthDialog} onClose={onClose} spacing={2}>
                <DialogTitle className={appTemplateDialogStyles.dialogHeaderBar}>
                    <Typography noWrap variant={"h6"} component={"p"}>
                        {t("appTemplateAuthDialog.title")}
                    </Typography>
                    <div className={appTemplateDialogStyles.flex} />
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
                    <ErrorAlert alert={alert} onClose={() => setOpenErrorDialog(false)} />
                    <TextField
                        fullWidth
                        label={t("appTemplateAuthDialog.user")}
                        value={downloadRequestData.username}
                        name="username"
                        onChange={handleChange}
                    />
                    <TextField
                        type="password"
                        fullWidth
                        label={t("appTemplateAuthDialog.password")}
                        value={downloadRequestData.password}
                        name="password"
                        onChange={handleChange}
                    />
                    <DialogActions>
                        <Button onClick={onClose}>{t("button.cancel")}</Button>
                        <Button onClick={handleAppTemplateReload} autoFocus>
                            {t("button.ok")}
                        </Button>
                    </DialogActions>
                </Box>
            </Dialog>
        </Container>
    );
}
export default AppTemplateAuthDialog;