import React, { Component } from "react";
import { withTranslation } from "react-i18next";
import { MuiThemeProvider, createMuiTheme, withStyles } from "@material-ui/core/styles";
import CssBaseline from "@material-ui/core/CssBaseline";

import "./App.css";
import MainTheme from "./assets/themes/MainTheme";
import MenuStyles from "./assets/styles/MenuStyles";

import AppHeader from "./commons/header/AppHeader";
import AppNavigationMenu from "./contentProvider/AppNavigationMenu";
import MainContentRouter from "./pages/MainContentRouter";
import {compose} from "recompose";

class App extends Component {
    state = {
        mobileOpen: false
    };

    handleDrawerToggle = () => {
        this.setState(state => ({ mobileOpen: !state.mobileOpen }));
    };

    render() {
        // theme settings
        const theme = createMuiTheme(new MainTheme());

        // state variables
        const {mobileOpen} = this.state;

        // prop variables
        const {classes, t} = this.props;

        return (
            <React.Fragment>
                <MuiThemeProvider theme={theme}>
                    <div className="App">
                        <div className={classes.root}>
                            <CssBaseline />
                            <AppHeader handleDrawerToggle={this.handleDrawerToggle} title={t('app.title')} />
                            <AppNavigationMenu mobileOpen={mobileOpen} />
                            <MainContentRouter />
                        </div>
                    </div>
                </MuiThemeProvider>
            </React.Fragment>
        );
    }

}

export default compose(withStyles(MenuStyles, { withTheme: true}),
    withTranslation())(App);
