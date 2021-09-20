import React, {Component} from 'react';
import {withTranslation} from "react-i18next";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";

import AppRest from "../../../services/AppRest";
import AllTable from "../../../commons/table/AllTable";


class AppAll extends Component {
    
    state = {
        isLoading: true,
        appAll: [],
        selected: undefined,
        columns: []
    };
    
    appRest = new AppRest();

    fetchAll = () => {
        this.appRest.findAll().then(response => {
            this.setState({appAll: response.data, isLoading: false});
        });
    };

    componentDidMount = () => {

        const {t} = this.props;

        const columns = [
            {title: t('app.name'), field: 'name'},
            {title: t('app.description'), field: 'description'}        ];

        this.setState({columns: columns});
        this.fetchAll();
    };

    render() {

        const {t} = this.props;
        const {columns, appAll, selected} = this.state;

        if (this.state.isLoading) {
            return <div>Loading...</div>;
        }

        return (
            <React.Fragment>
                <Typography variant="h1" color="primary">{t('app.title')}</Typography>
                <Button onClick={this.goToCreate} variant="contained" color="primary">{t('button.create')}</Button>
                <Button onClick={this.goToUpdate} variant="contained" color="primary">{t('button.update')}</Button>
                <Button onClick={this.delete} variant="contained" color="primary">{t('button.delete')}</Button>
                <AllTable
                    entities={appAll}
                    selected={selected}
                    onRowClick={this.rowClick}
                    columns={columns}/>
            </React.Fragment>
        );
    };

    goToCreate = () => {
        this.props.history.push("/app/create");
    };

    goToUpdate = () => {
        if (this.state.selected) {
            this.props.history.push("/app/update/" + this.state.selected.id);
            this.setState({selected: undefined});
        }
    };

    delete = () => {
        if (this.state.selected) {
            this.appRest.delete(this.state.selected).then(this.fetchAll);
            this.setState({selected: undefined});
        }
    };

    rowClick = (app) => {
        this.setState({selected: app});
    };

}

export default withTranslation()(AppAll);
