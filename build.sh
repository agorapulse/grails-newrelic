#!/usr/bin/env bash
export TERM="dumb"

printf "\n> \e[1;37mBuilding grails-newrelic\e[0m\n"

set -e

ABSOLUTE_PATH=$(cd `dirname "${BASH_SOURCE[0]}"` && pwd)
cd $ABSOLUTE_PATH

appVersion=$(./version.sh)

printf "\n# Project version \e[1;37m$appVersion\e[0m"

rm -rf build

if [ -n "${ARTIFACTORY_CONTEXT_URL}" ]; then
    printf "\n# \e[93mPublishing to Artifactory\e[0m\n\n"

    ./gradlew jar sourcesJar generatePomFileForMavenJavaPublication artifactoryPublish
else
    printf "\n# \e[93mInstalling locally\e[0m\n\n"

    ./gradlew install -x groovydoc -x javadoc
fi
