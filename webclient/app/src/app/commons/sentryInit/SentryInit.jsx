import React, {useEffect, useMemo} from "react";
import * as Sentry from "@sentry/react";
import {BrowserTracing} from "@sentry/tracing";
import {sentryConfiguration} from "../../AppConfig";
import ConfigurationRest from "../../services/ConfigurationRest";

function SentryInit(props) {
    const configurationRest = useMemo(() => new ConfigurationRest(), []);

    useEffect(() => {
        configurationRest.getSentryDsn().then(response => {
            Sentry.init({
                dsn: response.data,
                integrations: [new BrowserTracing()],

                // We recommend adjusting this value in production, or using tracesSampler
                // for finer control
                tracesSampleRate: sentryConfiguration.tracesSampleRate,
                enabled: process.env.NODE_ENV !== "development"
            });
        });
    }, [configurationRest]);

    return (
        <></>
    );
}

export default SentryInit;
