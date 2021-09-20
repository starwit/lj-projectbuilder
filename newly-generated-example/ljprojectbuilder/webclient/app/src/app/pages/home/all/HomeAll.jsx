import React, {Component} from 'react';
import {withTranslation} from "react-i18next";
import Typography from "@material-ui/core/Typography";
import "./HomeAll.css";

class HomeAll extends Component {

    render() {

        const t = this.props.t;

        return (
            <React.Fragment>
                <Typography variant="h1" color="primary">{t('home.title')}</Typography>
            </React.Fragment>
        );
    }

}

export default withTranslation()(HomeAll);
