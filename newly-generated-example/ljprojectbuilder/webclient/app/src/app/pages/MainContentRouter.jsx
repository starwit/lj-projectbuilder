import React, { Component } from "react";
import { Route, Switch } from "react-router-dom";
import { withRouter } from "react-router-dom";
// Material UI Components
import { withStyles } from "@material-ui/core/styles";
// Page Components
import Home from "./home/HomeMain";
import AppTemplateMain from "./apptemplate/AppTemplateMain";
import TemplateFileMain from "./templatefile/TemplateFileMain";
import AppMain from "./app/AppMain";

import MenuStyles from "../assets/styles/MenuStyles";

class MainContentRouter extends Component {

    render() {
        const { classes} = this.props;

        return (
            <main className={classes.content}>
                <div className={classes.toolbar} />
                <Switch>
                    <Route
                        path="/apptemplate"
                        component={AppTemplateMain}/>
                    <Route
                        path="/templatefile"
                        component={TemplateFileMain}/>
                    <Route
                        path="/app"
                        component={AppMain}/>
                    <Route
                        path="/"
                        component={Home}/>
                </Switch>
            </main>
        );
    }
}

export default withStyles(MenuStyles, { withTheme: true })(withRouter(MainContentRouter));
