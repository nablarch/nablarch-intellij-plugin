# Nablarchの開発を支援するIntelliJ IDEA用プラグイン

## 機能

- Nablarchの非公開APIを使用している箇所を警告する(InntelliJ IDEAのインスペクション)
- ブラックリストとして定義したJavaのAPIを使用している箇所を警告する(InntelliJ IDEAのインスペクション)

## インストール

- リリースページからzipファイルをダウンロードし、IntelliJ IDEAにプラグインをインストールする

## TIPS

### @Published(tag="architect")を許可したい

Settings > Editor > Inspections > nablarch/use unpublished apiで「呼び出しを許可するタグのリスト」に architect を足す。

### 併せて使いたい「Nablarchの開発を少しサポートするIntelliJ IDEA用のプラグイン」

https://github.com/siosio/nablarch-helper
