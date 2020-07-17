# Nablarchの開発を支援するIntelliJ IDEA用プラグイン

| master | develop |
|:-----------|:------------|
|[![Build Status](https://travis-ci.org/nablarch/nablarch-intellij-plugin.svg?branch=master)](https://travis-ci.org/nablarch/nablarch-intellij-plugin)|[![Build Status](https://travis-ci.org/nablarch/nablarch-intellij-plugin.svg?branch=develop)](https://travis-ci.org/nablarch/nablarch-intellij-plugin)|

## Function

- Alerts when private API of Nablarch is used (IntelliJ IDEA inspection)
- Alerts when blacklisted Java API, is used (IntelliJ IDEA inspection)

## Installation

- Download the zip file from the release page and install the plug-in for IntelliJ IDEA.

## TIPS

### To allow @Published(tag="architect")

Add architect to the “List of tags allowed to be called” in Settings > Editor > Inspections > nablarch/use unpublished api.

### “Plug-in meant for IntelliJ IDEA that provides a little support to Nablarch development” to be used in conjunction

https://github.com/siosio/nablarch-helper

### Check IntelliJ idea inspection with CIs

Execute inspection of IntelliJ IDEA using Jenkins and see that the results are displayed correctly: http://siosio.hatenablog.com/entry/2016/12/23/212140

### Customize unauthorized API

1. Create a blacklist (blackList.config) of unauthorized APIs and store them locally, as shown below.   
※If ".*" is present, it is determined to be package settings and if ".*" is not present, it is determined to be class settings.
  ```
  java.security.interfaces.*
  java.security.spec.*
  java.lang.Error
  ```
2. Specify the above black list file in Settings > Editor > Inspections > nablarch/use blacklist java api with IntelliJ.
