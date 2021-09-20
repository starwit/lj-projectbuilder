#!/bin/bash

pattern_release='^[0-9]+\.[0-9]+\.[0-9]+$'
pattern_dev='^[0-9]+\.[0-9]+\.[0-9]+\.dev$'
helm_chart_dir=helm/ljprojectbuilder

function log {
  echo "[$(date --rfc-3339=seconds)]: $*"
}

check_failed=false

log "Checking prerequisites..."

if [ -z "$RELEASE_VERSION" ]; then
  log "RELEASE_VERSION is not set!"
  check_failed=true
fi

if [[ ! $RELEASE_VERSION =~ $pattern_release ]]; then
  log "RELEASE_VERSION needs to match $pattern_release, e.g. '1.12.0'!"
  check_failed=true
fi

if [ -z "$NEXT_VERSION" ]; then
  log "NEXT_VERSION is not set!"
  check_failed=true
fi

if [[ ! $NEXT_VERSION =~ $pattern_release ]]; then
  log "NEXT_VERSION needs to match $pattern_release, e.g. '1.12.0'!"
  check_failed=true
fi

# We need to reset git urls if we run in Bamboo (because of Bamboos build cache)
if [ ! -z "$REMOTE_URL" ]; then
  git remote set-url origin $REMOTE_URL
  git config --global user.email $REMOTE_MAIL
  git config --global user.name $REMOTE_USER
fi

log "Checking current version..."
pattern_current_version="${RELEASE_VERSION}.dev"
current_version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout 2> /dev/null)
if [[ $current_version != $pattern_current_version ]]; then
  log "Current version $current_version does not match $pattern_current_version! Something is definitely wrong with your project configuration. Please fix manually!"
  check_failed=true
fi

if [ "$check_failed" = true ]; then
  log "Prerequisite check failed! Exiting..."
  exit 1
fi

## Replace dev version number (x.x.x.dev) with RELEASE_VERSION (e.g. in pom.xml)
log "Current version is $current_version"

# Current project version is valid, so set it to RELEASE_VERSION
log "Setting project version to $RELEASE_VERSION"
if ! mvn versions:set -DnewVersion=$RELEASE_VERSION -q ; then
  log "Version change failed!"
  exit 1
fi

log "Checking current Helm chart version..."
pattern_current_helm_version="${RELEASE_VERSION}-dev"
current_helm_version=$(yq read ${helm_chart_dir}/Chart.yaml version)
if [[ $current_helm_version != $pattern_current_helm_version ]]; then
  log "Current Helm chart version $current_helm_version does not match $pattern_current_helm_version! Something is definitely wrong with your project configuration. Please fix manually!"
  check_failed=true
fi

if [ "$check_failed" = true ]; then
  log "Prerequisite Helm check failed! Exiting..."
  exit 1
fi

log "Current Helm chart version is $current_helm_version"

# Current project version is valid, so set it to RELEASE_VERSION
log "Setting Helm chart version to $RELEASE_VERSION"
if ! yq write -i ${helm_chart_dir}/Chart.yaml version $RELEASE_VERSION ; then
  log "Helm chart version change failed!"
  exit 1
fi

log "Setting Docker tag to $RELEASE_VERSION"
if ! yq write -i ${helm_chart_dir}/values.yaml image.tag $RELEASE_VERSION ; then
  log "Docker tag change failed!"
  exit 1
fi

log "Checking if current branch is 'dev'..."
current_branch=$(git rev-parse --abbrev-ref HEAD)
if [[ $current_branch != "dev" ]]; then
  log "Repo is not on branch 'dev'. Not sure, if this is intended. Exiting..."
  exit 1
fi

## Do the actual committing
git commit -a -m "Prepare release of version $RELEASE_VERSION: Remove 'dev' suffix" && \
git tag $RELEASE_VERSION
if [ $? -ne 0 ]; then
  log "Commit failed. Exiting..."
  exit 1
fi

## Replace RELEASE_VERSION with NEXT_VERSION
log "Setting project version to ${NEXT_VERSION}.dev"
if ! mvn versions:set -DnewVersion=${NEXT_VERSION}.dev -q ; then
  log "Version change failed!"
  exit 1
fi

## Replace RELEASE_VERSION with NEXT_VERSION
log "Setting Helm chart version to ${NEXT_VERSION}-dev"
if ! yq write -i ${helm_chart_dir}/Chart.yaml version ${NEXT_VERSION}-dev ; then
  log "Helm chart version change failed!"
  exit 1
fi

git commit -a -m "Prepare next dev cycle: Setting version to ${NEXT_VERSION}.dev"
if [ $? -ne 0 ]; then
  log "Commit failed. Exiting..."
  exit 1
fi

## Merge dev into master (force merge commit)
git checkout master && \
git merge $RELEASE_VERSION --no-ff -m "Merge release tag $RELEASE_VERSION into master" && \
\
## Push
git push --all && \
git push --tags && exit 0

log "Something went wrong. Please check repository state!"
exit 1