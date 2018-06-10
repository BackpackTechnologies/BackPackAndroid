#!/bin/sh

git config --global user.email "guillaume.hebant@epitech.eu"
git config --global user.name "ghebant"
git push https://$TRAVIS_TOKEN@github.com/ghebant/TravisAndroidTest HEAD:master