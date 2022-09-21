#!/usr/bin/env sh

# Replace config env vars
printf "Replacing config env vars ..."
find .next/static -type f -exec sed -i -e "s|__SENTRY_DSN_FRONTEND_HERE__|$SENTRY_DSN_FRONTEND|g" -e "s|__SENTRY_ENVIRONMENT_HERE__|$SENTRY_ENVIRONMENT|g" -e "s|__SENTRY_TRACES_SAMPLE_RATE_FRONTEND_HERE__|$SENTRY_TRACES_SAMPLE_RATE_FRONTEND|g" {} \;
printf " done.\n"

# Run CMD
exec "$@"
