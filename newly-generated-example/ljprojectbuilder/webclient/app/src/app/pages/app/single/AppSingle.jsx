import React, {Component} from 'react';
import {withRouter} from "react-router-dom";

import AppRest from "../../../services/AppRest";
import AddUpdateForm from "../../../commons/form/AddUpdateForm";
import Typography from "@material-ui/core/Typography";

import {withTranslation} from "react-i18next";

class AppSingle extends Component {

    state = {
        app: {
            name: "",
                    description: ""}
        ,
        titleKey: ""
    };

    appRest = new AppRest();

    attributes = [
    {name: "name", type: "string"},
    {name: "description", type: "string"}    ];

    componentDidMount = () => {
        let updateResult = this.checkUpdate();
        this.submitFunction = updateResult.submitFunction;
        this.setState({titleKey: updateResult.titleKey});
    };

    render() {

        const {t} = this.props;

        if (!this.state.app) {
            return (<div>Loading...</div>);
        }

        return (
            <React.Fragment>
                <Typography variant="h1" color="primary">{t(this.state.titleKey, {entity: t('app')})}</Typography>
                <AddUpdateForm
                    entity={this.state.app}
                    attributes={this.attributes}
                    prefix='app'
                    handleSubmit={this.handleSubmit}
                    handleChange={this.handleChange}
                />
            </React.Fragment>
        );
    }

    checkUpdate() {
        let func;
        let titleKey = "";
        if (this.props.match.params.id === undefined) {
            func = this.appRest.create;
            titleKey = "form.create";
        } else {
            this.appRest.findById(this.props.match.params.id).then(response => this.setApp(response.data));
            func = this.appRest.update;
            titleKey = "form.update";
        }
        return {submitFunction: func, titleKey: titleKey};
    };

    setApp = (data) => {
        this.setState({
            app: data
        });
    };

    handleChange = (event) => {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        let app = this.state.app;
        app[name] = value;

        this.setState({
            app: app
        });
    };

    handleSubmit = (event) => {

        //turn off page relaod
        event.preventDefault();

        let app = this.state.app;

        this.attributes.forEach(attribute => {
           if (attribute.type === "time") {
               let date = new Date("1970-01-01T" + this.state.app[attribute.name]);
               app[attribute.name] = date.toISOString();
           }
        });

        this.submitFunction(app).then(this.goBack);
    };

    goBack = () => {
        this.props.history.push("/app");
    };

}

export default withTranslation()(withRouter(AppSingle));
