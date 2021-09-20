import React, {Component} from 'react';
import {withTranslation} from "react-i18next";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";

import AppTemplateRest from "../../../services/AppTemplateRest";
import AllTable from "../../../commons/table/AllTable";


class AppTemplateAll extends Component {
    
    state = {
        isLoading: true,
        appTemplateAll: [],
        selected: undefined,
        columns: []
    };
    
    appTemplateRest = new AppTemplateRest();

    fetchAll = () => {
        this.appTemplateRest.findAll().then(response => {
            this.setState({appTemplateAll: response.data, isLoading: false});
        });
    };

    componentDidMount = () => {

        const {t} = this.props;

        const columns = [
            {title: t('appTemplate.name'), field: 'name'},
            {title: t('appTemplate.templatefiles'), field: 'templatefiles'},
            {title: t('appTemplate.description'), field: 'description'}        ];

        this.setState({columns: columns});
        this.fetchAll();
    };

    render() {

        const {t} = this.props;
        const {columns, appTemplateAll, selected} = this.state;

        if (this.state.isLoading) {
            return <div>Loading...</div>;
        }

        return (
            <React.Fragment>
                <Typography variant="h1" color="primary">{t('appTemplate.title')}</Typography>
                <Button onClick={this.goToCreate} variant="contained" color="primary">{t('button.create')}</Button>
                <Button onClick={this.goToUpdate} variant="contained" color="primary">{t('button.update')}</Button>
                <Button onClick={this.delete} variant="contained" color="primary">{t('button.delete')}</Button>
                <AllTable
                    entities={appTemplateAll}
                    selected={selected}
                    onRowClick={this.rowClick}
                    columns={columns}/>
            </React.Fragment>
        );
    };

    goToCreate = () => {
        this.props.history.push("/apptemplate/create");
    };

    goToUpdate = () => {
        if (this.state.selected) {
            this.props.history.push("/apptemplate/update/" + this.state.selected.id);
            this.setState({selected: undefined});
        }
    };

    delete = () => {
        if (this.state.selected) {
            this.appTemplateRest.delete(this.state.selected).then(this.fetchAll);
            this.setState({selected: undefined});
        }
    };

    rowClick = (appTemplate) => {
        this.setState({selected: appTemplate});
    };

}

export default withTranslation()(AppTemplateAll);
