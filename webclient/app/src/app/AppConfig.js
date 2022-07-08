const appMenuItems = [
    {title: "apps.title", link: "/"},
    {title: "apptemplates.title", link: "/apptemplates/all"}
];

const sentryConfiguration = {
    dsn: "https://89ae8cc61d2545a2a88021f96a433b43@o1305630.ingest.sentry.io/6548197",
    tracesSampleRate: .75
    // environment: process.env.SENTRY_ENVIRONMENT
};

export {appMenuItems, sentryConfiguration};
