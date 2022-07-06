import React from "react";
import * as Sentry from "@sentry/react";
import {BrowserTracing} from "@sentry/tracing";
import {sentryConfiguration} from "../../AppConfig";

function SentryInit(props) {
    Sentry.init({
        dsn: sentryConfiguration.dsn,
        integrations: [new BrowserTracing()],

        // We recommend adjusting this value in production, or using tracesSampler
        // for finer control
        tracesSampleRate: sentryConfiguration.tracesSampleRate,
        enabled: process.env.NODE_ENV !== "development",
        environment: sentryConfiguration.environment
    });

    return (
        <></>
    );
}

export default SentryInit;
