#!/usr/bin/env bash

set -e

EXIT_STATUS=0
echo "Publishing archives for branch $TRAVIS_BRANCH"
if [[ -n $TRAVIS_TAG ]] || [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]; then
  if [[ -n $TRAVIS_TAG ]]; then
    echo "Pushing build to Bintray"
    ./gradlew bintrayUpload || EXIT_STATUS=$?
  else
    echo "Ignoring publishing, no tags found"
    #echo "Publishing snapshot"
    #./gradlew publish || EXIT_STATUS=$?
  fi
fi
exit $EXIT_STATUS
