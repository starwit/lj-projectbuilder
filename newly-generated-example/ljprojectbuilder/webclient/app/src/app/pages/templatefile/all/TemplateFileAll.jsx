import React, {Component} from 'react';
import {withTranslation} from "react-i18next";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";

import TemplateFileRest from "../../../services/TemplateFileRest";
import AllTable from "../../../commons/table/AllTable";


class TemplateFileAll extends Component {
    
    state = {
        isLoading: true,
        templateFileAll: [],
        selected: undefined,
        columns: []
    };
    
    templateFileRest = new TemplateFileRest();

    fetchAll = () => {
        this.templateFileRest.findAll().then(response => {
            this.setState({templateFileAll: response.data, isLoading: false});
        });
    };

    componentDidMount = () => {

        const {t} = this.props;

        const columns = [
            {title: t('templateFile.description'), field: 'description'},
            {title: t('templateFile.name'), field: 'name'}        ];

        this.setState({columns: columns});
        this.fetchAll();
    };

    render() {

        const {t} = this.props;
        const {columns, templateFileAll, selected} = this.state;

        if (this.state.isLoading) {
            return <div>Loading...</div>;
        }

        return (
            <React.Fragment>
                <Typography variant="h1" color="primary">{t('templateFile.title')}</Typography>
                <Button onClick={this.goToCreate} variant="contained" color="primary">{t('button.create')}</Button>
                <Button onClick={this.goToUpdate} variant="contained" color="primary">{t('button.update')}</Button>
                <Button onClick={this.delete} variant="contained" color="primary">{t('button.delete')}</Button>
                <AllTable
                    entities={templateFileAll}
                    selected={selected}
                    onRowClick={this.rowClick}
                    columns={columns}/>
            </React.Fragment>
        );
    };

    goToCreate = () => {
        this.props.history.push("/templatefile/create");
    };

    goToUpdate = () => {
        if (this.state.selected) {
            this.props.history.push("/templatefile/update/" + this.state.selected.id);
            this.setState({selected: undefined});
        }
    };

    delete = () => {
        if (this.state.selected) {
            this.templateFileRest.delete(this.state.selected).then(this.fetchAll);
            this.setState({selected: undefined});
        }
    };

    rowClick = (templateFile) => {
        this.setState({selected: templateFile});
    };

}

export default withTranslation()(TemplateFileAll);
