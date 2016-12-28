package nablarch.intellij.plugin.inspector

/**
 * ブラックリスト情報を保持するBeanクラス。
 *
 * @author Naoki Yamamoto
 */
data class Blacklist(val packages: List<String>, val classes: List<String>, val methods: List<String>)