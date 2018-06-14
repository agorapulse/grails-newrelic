#!/usr/bin/env bash
export TERM="dumb"

printf "\n> \e[1;37mTesting grails-newrelic\e[0m\n\n"

set -e

ABSOLUTE_PATH=$(cd `dirname "${BASH_SOURCE[0]}"` && pwd)
cd $ABSOLUTE_PATH

./gradlew clean test -Dgrails.env=test --stacktrace --info
