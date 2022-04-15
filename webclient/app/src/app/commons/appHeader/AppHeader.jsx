import React from "react";
// Material UI Components
import logo from "../../assets/images/logo-white.png";
import {AppBar, Button, IconButton, Toolbar} from "@mui/material";
import AppHeaderStyles from "./AppHeaderStyles";
import {useHistory} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {Logout} from "@mui/icons-material";

function AppHeader(props) {
    const {menuItems} = props;
    const appHeaderStyles = AppHeaderStyles();
    const history = useHistory();
    const {t} = useTranslation();

    return (
        <>
            <AppBar position="fixed" color="inherit" className={appHeaderStyles.appBar}>
                <Toolbar className={appHeaderStyles.toolbar}>
                    <img className={appHeaderStyles.menuLogoImg} src={logo} alt="Logo of lirejarp"/>
                    <div className={appHeaderStyles.spacer}/>
                    {menuItems.map(item => (
                        <Button key={item.title} color="secondary" disableRipple className={appHeaderStyles.linkButton}
                            onClick={() => history.push(item.link)}>{t(item.title)}</Button>
                    ))}
                    <IconButton color="secondary" disableRipple className={appHeaderStyles.linkButton}
                        onClick={() => history.push("/logout")}><Logout/></IconButton>
                </Toolbar>
            </AppBar>
            <div className={appHeaderStyles.contentSpacer}/>
        </>
    );
}

export default AppHeader;
