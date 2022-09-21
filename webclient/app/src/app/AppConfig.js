const appMenuItems = [
    {title: "apps.title", link: "/"},
    {title: "apptemplates.title", link: "/apptemplates/all"}
];

const sentryConfiguration = {
    dsn: process.env.SENTRY_DSN_FRONTEND,
    tracesSampleRate: Number(process.env.SENTRY_TRACES_SAMPLE_RATE_FRONTEND || .75),
    environment: process.env.SENTRY_ENVIRONMENT
};

export {appMenuItems, sentryConfiguration};
