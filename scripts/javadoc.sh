#!/bin/bash

if [ "$TRAVIS_REPO_SLUG" == "typicalbot/typicalbot" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

    echo -e "Publishing javadoc...\n"

    cp -R build/docs/javadoc $HOME/javadoc-latest

    cd $HOME

    git config --global user.email "travis@travis-ci.org"
    git config --global user.name "travis-ci"
    git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/typicalbot/typicalbot gh-pages > /dev/null

    cd gh-pages


    git rm -rf ./javadoc
    cp -Rf $HOME/javadoc-latest ./javadoc
    git add -f .
    git commit -m "Latest javadoc pushed. ($TRAVIS_BUILD_NUMBER)"
    git push -fq origin gh-pages > /dev/null

    echo -e "Published javadoc.\n"

fi
