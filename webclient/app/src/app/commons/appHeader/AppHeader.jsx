import React from "react";
// Material UI Components
import logo from "../../assets/images/logo-white.png";
import {AppBar, Button, IconButton, Toolbar, Typography} from "@mui/material";
import HeaderStyles from "./../../assets/styles/HeaderStyles";
import {useHistory} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {Logout} from "@mui/icons-material";

function AppHeader(props) {
    const {menuItems, title} = props;
    const headerStyles = HeaderStyles();
    const history = useHistory();
    const {t} = useTranslation();

    return (
        <>
            <AppBar position="fixed" color="inherit" className={headerStyles.appBar}>
                <Toolbar className={headerStyles.toolbar}>
                    <img className={headerStyles.menuLogoImg} src={logo} alt="Logo of lirejarp"/>
                    <Typography className={headerStyles.menuTitle} variant="h2" noWrap>
                        {title}
                    </Typography>
                    <div className={headerStyles.spacer}/>
                    {menuItems.map(item => (
                        <Button key={item.title} color="secondary" disableRipple className={headerStyles.linkButton}
                            onClick={() => history.push(item.link)}>{t(item.title)}</Button>
                    ))}
                    <IconButton color="secondary" disableRipple className={headerStyles.linkButton}
                        onClick={() => history.push("/logout")}><Logout/></IconButton>
                </Toolbar>
            </AppBar>
            <div className={headerStyles.contentSpacer}/>
        </>
    );
}

export default AppHeader;
