# Nablarchの開発を支援するIntelliJ IDEA用プラグイン

| master | develop |
|:-----------|:------------|
|[![Build Status](https://travis-ci.org/nablarch/nablarch-intellij-plugin.svg?branch=master)](https://travis-ci.org/nablarch/nablarch-intellij-plugin)|[![Build Status](https://travis-ci.org/nablarch/nablarch-intellij-plugin.svg?branch=develop)](https://travis-ci.org/nablarch/nablarch-intellij-plugin)|

## 機能

- Nablarchの非公開APIを使用している箇所を警告する(IntelliJ IDEAのインスペクション)
- ブラックリストとして定義したJavaのAPIを使用している箇所を警告する(IntelliJ IDEAのインスペクション)

## インストール

- リリースページからzipファイルをダウンロードし、IntelliJ IDEAにプラグインをインストールする

## TIPS

### @Published(tag="architect")を許可したい

Settings > Editor > Inspections > nablarch/use unpublished apiで「呼び出しを許可するタグのリスト」に architect を足す。

### 併せて使いたい「Nablarchの開発を少しサポートするIntelliJ IDEA用のプラグイン」

https://github.com/siosio/nablarch-helper

### IntelliJ IDEAのインスペクションをCIでチェックしたい

JenkinsでIntelliJ IDEAのinspectionを実行して結果をいい感じに表示させてみる
http://siosio.hatenablog.com/entry/2016/12/23/212140

### 使用不許可APIをカスタマイズしたい

1. 下記のように、使用不許可APIをブラックリスト（BlackList.config）としてを作成してローカルで保存する。  
※「.*」がある時はパッケージ指定、そうでないときはクラス指定と判定される。
  ```
  java.security.interfaces.*
  java.security.spec.*
  java.lang.Error
  ```
2. IntelliJで Settings > Editor > Inspections > nablarch/use blacklist java api で上記ブラックリストファイルを指定する。
