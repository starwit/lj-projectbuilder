import React from "react";
// Material UI Components
import logo from "../../assets/images/logo.png";
import {AppBar, Avatar, Button, Toolbar} from "@mui/material";
import AppHeaderStyles from "./AppHeaderStyles";
import {useHistory} from "react-router-dom";
import {useTranslation} from "react-i18next";


function AppHeader() {
    const appHeaderStyles = AppHeaderStyles();
    const history = useHistory();
    const {t} = useTranslation();

    return (
        <>
            <AppBar position="fixed" color="inherit" className={appHeaderStyles.appBar}>
                <Toolbar className={appHeaderStyles.toolbar}>
                    <img className={appHeaderStyles.menuLogoImg} src={logo} alt="Logo of lirejarp"/>
                    <div className={appHeaderStyles.spacer}/>
                    <Button color="inherit" disableRipple className={appHeaderStyles.linkButton}
                            onClick={() => history.push("/")}>{t("appHeader.apps")}</Button>
                    <Button color="inherit" disableRipple
                            className={appHeaderStyles.linkButton}>{t("appHeader.store")}</Button>
                    <Avatar className={appHeaderStyles.linkButton}></Avatar>
                </Toolbar>
            </AppBar>
            <div className={appHeaderStyles.contentSpacer}/>
        </>
    );
}

export default AppHeader;