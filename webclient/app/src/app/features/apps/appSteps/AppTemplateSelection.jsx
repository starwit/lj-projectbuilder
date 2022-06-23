import React, {useCallback, useEffect, useMemo, useState} from "react";
import {Container, Grid} from "@mui/material";
import AppTemplateSelectCard from "../appTemplateSelectCard/AppTemplateSelectCard";
import AppTemplateRest from "../../../services/AppTemplateRest";
import {Clear} from "@mui/icons-material";
import {useTranslation} from "react-i18next";
import {LoadingSpinner, Statement} from "@starwit/react-starwit";

function AppTemplateSelection(props) {
    const {onChange, value} = props;

    const [templates, setTemplates] = useState(null);
    const [appTemplatesError, setAppTemplatesError] = useState(null);
    const appTemplateRest = useMemo(() => new AppTemplateRest(), []);
    const {t} = useTranslation();

    const loadAppTemplates = useCallback(() => {
        setTemplates(null);
        setAppTemplatesError(null);
        appTemplateRest.findAll().then(allAppsResponse => {
            setTemplates(allAppsResponse.data);
        }).catch(allAppsResponseError => {
            setAppTemplatesError(allAppsResponseError.response);
        });
    }, [appTemplateRest, setTemplates, setAppTemplatesError]);

    useEffect(() => {
        loadAppTemplates();
    }, [loadAppTemplates]);

    function handleSelection(template) {
        onChange(template);
    }

    if (!templates) {
        return <LoadingSpinner message={t("appTemplates.loading")}/>;
    }

    if (appTemplatesError) {
        return <Statement
            message={t("appTemplates.loading.error")}
            icon={<Clear/>}
            actionMessage={t("button.retry")}
            onActionClick={loadAppTemplates}
        />;
    }

    return (
        <Container>
            <Grid container spacing={5}>
                {templates.map((template, index) => (
                    <Grid item sm={3} key={index}>
                        <AppTemplateSelectCard
                            template={template}
                            onSelection={handleSelection}
                            selected={value?.id === template.id}
                        />
                    </Grid>
                ))}

            </Grid>
        </Container>
    );
}

AppTemplateSelection.defaultProps = {
    onChange: () => {
        // This is intentional
    }
};
export default AppTemplateSelection;
