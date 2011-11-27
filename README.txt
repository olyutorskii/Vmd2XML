[UTF-8 Japanese]

                               V m d 2 X M L
                                  Readme

                                              Copyright(c) 2011 olyutorskii


=== Vmd2XMLとは ===

Vmd2XMLは、3D動画制作ツール、MikuMikuDance(MMD)で用いられる
モーションデータファイル(*.vmd)の内容を、XML形式のデータファイルと
交換するためのアプリケーションです。
Vmd2XMLは、MikuTogaプロジェクトの派生物として誕生しました。

MikuTogaプロジェクトは、MMDによる3Dアニメーション制作を支援するプログラムの
整備のために発足した、オープンソースプロジェクトです。

Vmd2XMLは、同じMikuTogaプロジェクト内のTogaGemライブラリを用いて
開発が進められています。

※ MMD開発者の樋口M氏は、MikuTogaの開発活動に一切関与していません。
　 Vmd2XMLに関する問い合わせをを樋口M氏へ投げかけないように！約束だよ！


=== 実行環境 ===

 - Vmd2XMLはJava言語(JLS3)で記述されたプログラムです。
 - Vmd2XMLはJRE1.6に準拠したJava実行環境で利用できるように作られています。
   原則として、JRE1.6に準拠した実行系であれば、プラットフォームを選びません。


=== 開発プロジェクト運営元 ===

  http://sourceforge.jp/projects/mikutoga/ まで。


=== ディレクトリ内訳構成 ===

基本的にはMaven3のmaven-archetype-quickstart構成に準じます。

./README.txt
    あなたが今見てるこれ。

./CHANGELOG.txt
    変更履歴。

./LICENSE.txt
    ライセンスに関して。

./SCM.txt
    ソースコード管理に関して。

./pom.xml
    Maven3用プロジェクト構成定義ファイル。

./src/main/java/
    Javaのソースコード。

./src/test/java/
    JUnit 4.* 用のユニットテストコード。

./src/main/config/
    各種ビルド・構成管理に必要なファイル群。

./src/main/config/checks.xml
    Checkstyle用configファイル。

./src/main/config/pmdrules.xml
    PMD用ルール定義ファイル。


--- EOF ---
