import React, {useCallback, useEffect, useMemo, useState} from "react";
import {Container, Grid} from "@mui/material";
import AppTemplateSelectCard from "../../../../commons/appTemplateSelectCard/AppTemplateSelectCard";
import AppTemplateRest from "../../../../services/AppTemplateRest";
import LoadingSpinner from "../../../../commons/loadingSpinner/LoadingSpinner";
import Statement from "../../../../commons/statement/Statement";
import {Clear} from "@mui/icons-material";
import {useTranslation} from "react-i18next";


function TemplateSection(props) {

    const {onChange, value} = props;


    const [templates, setTemplates] = useState(null);
    const [appTemplatesError, setAppTemplatesError] = useState(null);
    const appTemplateRest = useMemo(() => new AppTemplateRest(), []);
    const {t} = useTranslation();

    /* const templates = [
         {
             id: 1,
             name: "Standard Runtime",
             description: "Das ist eine lange Beschreibung der Standard-Runtime",
             image: Image
         },
         {
             id: 2,
             name: "Andere Runtime",
             description: "Das ist eine lange Beschreibung der Standard-Runtime",
             image: Image
         },
     ]*/


    const loadAppTemplates = useCallback(() => {
        setTemplates(null);
        setAppTemplatesError(null);
        appTemplateRest.findAll().then(allAppsResponse => {
            setTemplates(allAppsResponse.data);
        }).catch(allAppsResponseError => {
            setAppTemplatesError(allAppsResponseError.response)
        })
    }, [appTemplateRest, setTemplates, setAppTemplatesError])


    useEffect(() => {
        loadAppTemplates()
    }, [loadAppTemplates]);

    function handleSelection(template) {
        onChange(template)
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
                {templates.map(template => (
                    <Grid item sm={3}>
                        <AppTemplateSelectCard
                            template={template}
                            onSelection={handleSelection}
                            selected={value?.id === template.id}
                        />
                    </Grid>
                ))}

            </Grid>
        </Container>
    )

}

TemplateSection.defaultProps = {
    onChange: () => {
        //This is intentional
    }
}

export default TemplateSection;