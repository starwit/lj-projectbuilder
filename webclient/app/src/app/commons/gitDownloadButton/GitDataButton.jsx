import React, {useEffect, useState} from "react";
import {Box, Button, Container, DialogTitle, IconButton, Typography} from "@mui/material";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import {useTranslation} from "react-i18next";
import {Close, CloudSync} from "@mui/icons-material";
import ValidatedTextField from "../validatedTextField/ValidatedTextField";
import RegexConfig from "../../../regexConfig";
import GitDataButtonStyles from "./GitDataButtonStyles";

function GitDataButton(props) {
    const {credentialsRequired, handleGit, handleAfterSuccess, buttonIcon, buttonName, buttonVariant} = props;
    const gitDataButtonStyles = GitDataButtonStyles();
    const [hasFormError, setHasFormError] = React.useState(false);
    const [downloadRequestData, setDownloadRequestData] = useState({"username": "", "password": ""});
    const {t} = useTranslation();

    const [openAuthDialog, setOpenAuthDialog] = useState(false);


    const handleChange = (event) => {
        const {name, value} = event.target;
        const downloadRequestDataNew = {...downloadRequestData};
        downloadRequestDataNew[name] = value;
        setDownloadRequestData(downloadRequestDataNew);
    };

    const handleLogin = () => {
        if (credentialsRequired) {
            setOpenAuthDialog(true);
        } else {
            handleAppTemplateReload();
        }
    };

    const handleAppTemplateReload = () => {
        if (hasFormError && credentialsRequired) {
            return;
        }
        handleGit(downloadRequestData).then(() => {
            handleAfterSuccess();
            setOpenAuthDialog(false);
        });
    };

    const onClose = () => {
        setDownloadRequestData({"username": "", "password": ""});
        setOpenAuthDialog(false);
    };

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
    }, [downloadRequestData, hasFormError]);


    return (
        <Container>
            <Button onClick={handleLogin} startIcon={buttonIcon ? buttonIcon : <CloudSync />}
                variant={buttonVariant}>{buttonName}</Button>
            <Dialog open={openAuthDialog} onClose={onClose} spacing={2}>
                <DialogTitle className={gitDataButtonStyles.dialogHeaderBar}>
                    <Typography noWrap variant={"h6"} component={"p"}>
                        {t("gitAuth.title")}
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
                        "& .MuiTextField-root": {m: 1, width: "95%"}

                    }}
                    noValidate
                    autoComplete="off"
                >
                    <ValidatedTextField
                        fullWidth
                        label={t("gitAuth.username") + "*"}
                        value={downloadRequestData.username}
                        name="username"
                        onChange={handleChange}
                        isCreate={true}
                        helperText={t("gitAuth.username.hint")}
                        regex={RegexConfig.appTemplateAuthUser}
                    />
                    <ValidatedTextField
                        type="password"
                        fullWidth
                        label={t("gitAuth.password") + "*"}
                        value={downloadRequestData.password}
                        name="password"
                        onChange={handleChange}
                        isCreate={true}
                        helperText={t("gitAuth.password.hint")}
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
