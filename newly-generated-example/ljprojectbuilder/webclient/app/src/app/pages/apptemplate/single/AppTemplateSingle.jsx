import React, {Component} from 'react';
import {withRouter} from "react-router-dom";

import AppTemplateRest from "../../../services/AppTemplateRest";
import AddUpdateForm from "../../../commons/form/AddUpdateForm";
import Typography from "@material-ui/core/Typography";

import {withTranslation} from "react-i18next";

class AppTemplateSingle extends Component {

    state = {
        appTemplate: {
            name: "",
                    templatefiles: "",
                    description: ""}
        ,
        titleKey: ""
    };

    appTemplateRest = new AppTemplateRest();

    attributes = [
    {name: "name", type: "string"},
    {name: "templatefiles", type: "string"},
    {name: "description", type: "string"}    ];

    componentDidMount = () => {
        let updateResult = this.checkUpdate();
        this.submitFunction = updateResult.submitFunction;
        this.setState({titleKey: updateResult.titleKey});
    };

    render() {

        const {t} = this.props;

        if (!this.state.appTemplate) {
            return (<div>Loading...</div>);
        }

        return (
            <React.Fragment>
                <Typography variant="h1" color="primary">{t(this.state.titleKey, {entity: t('appTemplate')})}</Typography>
                <AddUpdateForm
                    entity={this.state.appTemplate}
                    attributes={this.attributes}
                    prefix='appTemplate'
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
            func = this.appTemplateRest.create;
            titleKey = "form.create";
        } else {
            this.appTemplateRest.findById(this.props.match.params.id).then(response => this.setAppTemplate(response.data));
            func = this.appTemplateRest.update;
            titleKey = "form.update";
        }
        return {submitFunction: func, titleKey: titleKey};
    };

    setAppTemplate = (data) => {
        this.setState({
            appTemplate: data
        });
    };

    handleChange = (event) => {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        let appTemplate = this.state.appTemplate;
        appTemplate[name] = value;

        this.setState({
            appTemplate: appTemplate
        });
    };

    handleSubmit = (event) => {

        //turn off page relaod
        event.preventDefault();

        let appTemplate = this.state.appTemplate;

        this.attributes.forEach(attribute => {
           if (attribute.type === "time") {
               let date = new Date("1970-01-01T" + this.state.appTemplate[attribute.name]);
               appTemplate[attribute.name] = date.toISOString();
           }
        });

        this.submitFunction(appTemplate).then(this.goBack);
    };

    goBack = () => {
        this.props.history.push("/apptemplate");
    };

}

export default withTranslation()(withRouter(AppTemplateSingle));
