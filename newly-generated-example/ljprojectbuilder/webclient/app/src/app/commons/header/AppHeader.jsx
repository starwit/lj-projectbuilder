import React, { Component } from "react";
import compose from "recompose/compose";
// Material UI Components
import { withStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";

import logo from "../../assets/images/logo.png";
import logoResponsive from "../../assets/images/logo.png";
import MenuStyles from "../../assets/styles/MenuStyles";


class AppHeader extends Component {
    render() {
        const { classes, handleDrawerToggle, title } = this.props;
        return (
            <AppBar position="fixed" color="inherit" className={classes.appBar}>
                <Toolbar>
                    <IconButton
                        color="inherit"
                        aria-label="Open drawer"
                        onClick={handleDrawerToggle}
                        className={classes.menuButton}>
                        <MenuIcon />
                    </IconButton>
                    <Typography className={classes.menuLogo} color="inherit" noWrap>
                        {<img className={classes.menuLogoImg} src={logo} alt="logo"/>}
                    </Typography>
                    <Typography className={classes.menuLogoResponsive} color="inherit" noWrap>
                        {<img className={classes.menuLogoImg} src={logoResponsive} alt="logo"/>}
                    </Typography>
                    <Typography className={classes.menuTitle} variant="h5" noWrap>
                        {/* <img src={logo} alt="logo" width="300px" /> */}
                        {title}
                    </Typography>
                </Toolbar>
            </AppBar>
        );
    }
}

export default compose(withStyles(MenuStyles, { withTheme: true }))(AppHeader);