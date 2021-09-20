import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import { withStyles } from "@material-ui/core/styles";
import NavigationMenu from "../commons/menu/NavigationMenu";
import styles from "../assets/styles/MenuStyles";
import {withTranslation} from "react-i18next";
import {compose} from "recompose";
import VersionRest from "../services/VersionRest";

class AppNavigationMenu extends Component {

    state = {
        info: undefined,
        selected: "/"
    }

    versionRest = new VersionRest();

    buildMenuContent = () => {
        const { t } = this.props;
        let navListHome = { type: "Link", elements: [{ component: Link, to: "/", id: "0", title: t('home.title') }] };
        let navListEntitiesSection = { type: "Link", elements: [
                        { component: Link, title: t('appTemplate.title'), to: '/apptemplate', id: 'appTemplate' },
            { component: Link, title: t('templateFile.title'), to: '/templatefile', id: 'templateFile' },
            { component: Link, title: t('app.title'), to: '/app', id: 'app' }
        ]};

        let navListDivider = { type: "Divider" };

        let navListSections =
            [navListHome,
            navListDivider,
            navListEntitiesSection];

        return navListSections;
    };

    componentDidMount = () => {
        this.versionRest.info().then(response => this.setState({info: response.data.build, selected: this.props.location.pathname}))
    };

    componentDidUpdate = (prevProps) => {
        if (this.props.location.pathname !== prevProps.location.pathname) {
            this.setState({selected: this.props.location.pathname});
        }
    };

    render() {
        const { info, selected } = this.state;
        const { mobileOpen } = this.props;

        if (!info) {
            return (<div>Loading...</div>);
        }

        return (
            <NavigationMenu
                navListSections={this.buildMenuContent()}
                selected={selected}
                mobileOpen={mobileOpen}
                buildVersion={info.version}/>
        );
    }
}


export default compose(withStyles(styles, { withTheme: true }),
    withTranslation())(withRouter(AppNavigationMenu));
