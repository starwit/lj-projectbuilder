import React, {Component} from 'react';
import {withRouter} from "react-router-dom";

import TemplateFileRest from "../../../services/TemplateFileRest";
import AddUpdateForm from "../../../commons/form/AddUpdateForm";
import Typography from "@material-ui/core/Typography";

import {withTranslation} from "react-i18next";

class TemplateFileSingle extends Component {

    state = {
        templateFile: {
            description: "",
                    name: ""}
        ,
        titleKey: ""
    };

    templateFileRest = new TemplateFileRest();

    attributes = [
    {name: "description", type: "string"},
    {name: "name", type: "string"}    ];

    componentDidMount = () => {
        let updateResult = this.checkUpdate();
        this.submitFunction = updateResult.submitFunction;
        this.setState({titleKey: updateResult.titleKey});
    };

    render() {

        const {t} = this.props;

        if (!this.state.templateFile) {
            return (<div>Loading...</div>);
        }

        return (
            <React.Fragment>
                <Typography variant="h1" color="primary">{t(this.state.titleKey, {entity: t('templateFile')})}</Typography>
                <AddUpdateForm
                    entity={this.state.templateFile}
                    attributes={this.attributes}
                    prefix='templateFile'
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
            func = this.templateFileRest.create;
            titleKey = "form.create";
        } else {
            this.templateFileRest.findById(this.props.match.params.id).then(response => this.setTemplateFile(response.data));
            func = this.templateFileRest.update;
            titleKey = "form.update";
        }
        return {submitFunction: func, titleKey: titleKey};
    };

    setTemplateFile = (data) => {
        this.setState({
            templateFile: data
        });
    };

    handleChange = (event) => {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        let templateFile = this.state.templateFile;
        templateFile[name] = value;

        this.setState({
            templateFile: templateFile
        });
    };

    handleSubmit = (event) => {

        //turn off page relaod
        event.preventDefault();

        let templateFile = this.state.templateFile;

        this.attributes.forEach(attribute => {
           if (attribute.type === "time") {
               let date = new Date("1970-01-01T" + this.state.templateFile[attribute.name]);
               templateFile[attribute.name] = date.toISOString();
           }
        });

        this.submitFunction(templateFile).then(this.goBack);
    };

    goBack = () => {
        this.props.history.push("/templatefile");
    };

}

export default withTranslation()(withRouter(TemplateFileSingle));
