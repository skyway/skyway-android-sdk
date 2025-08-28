# SkyWay Android SDK release notes

[English](./release-notes.en.md)

## [Version 4.0.5](https://github.com/skyway/skyway-android-sdk/releases/tag/v4.0.5) - 2025-08-28

### Added

- 16KB Page Size に対応
  - 64-bit アーキテクチャ(arm64v8a & x86_64) について対応しました

### Fixed
- v4.0.4が新SkyWayと同梱する場合ビルドエラーになる問題を修正しました

## [Version 4.0.4](https://github.com/skyway/skyway-android-sdk/releases/tag/v4.0.4) - 2025-08-13

### Added

- 16KB Page Size に対応
  - 64-bit アーキテクチャ(arm64v8a & x86_64) について対応しました

## [Version 4.0.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v4.0.3) - 2024-07-12

### Fixed

- proguardのルールを修正しました。

## [Version 4.0.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v4.0.2) - 2024-06-05

### Changed

- 新SkyWayと旧SkyWayを同一のアプリで動作可能にしました。
  - ただし、同時利用は非対応となります。
  - 新旧を切り替える場合は必ずSDKの終了処理（`Dispose`/`Destory`などの呼び出し、オブジェクトの解放）を行ってください。

## [Version 4.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v4.0.1) - 2022-12-02

### Fixed

- `Canvas.ScalingEnum.ASPECT_FIT`が正常に動作しない不具合を修正しました。

## [Version 4.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v4.0.0) - 2022-11-11

### Breaking Changed
- 内部で利用しているWebRTCライブラリを更新し、動作の安定性を向上させました。
  - `getStats()` メソッドを利用して取得できる統計情報の構造が一部変更されています。

- v4.0.0よりも古いAndroid SDKを用いたアプリをGoogle Play Storeに公開している場合に、「脆弱性のあるWebRTCバージョン」というエラーメッセージが表示される事象が報告されております。
  - 既知の脆弱性についてはv2.0.2にて対応しておりますが、出来る限り最新のSDKをご利用ください。
  - このエラーメッセージは、Android SDKを今回リリースしたv4.0.0に更新していただくことで解消いたします。

## [Version 3.1.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v3.1.1) - 2022-09-07

### Fixed

- 切断処理を実行した際に、アプリケーションがクラッシュする可能性がある不具合を修正しました。

## [Version 3.1.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v3.1.0) - 2022-05-10

### Added

- サーバへリクエストを送信する際のリクエストヘッダに、SNIの情報を追加しました。

## [Version 3.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v3.0.1) - 2021-07-09

### Fixed

- ビデオコーデックのVP8をハードウェアデコードする際に稀にクラッシュしてしまう問題を修正

## [Version 3.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v3.0.0) - 2021-05-27

### Breaking Changed

- 内部で利用しているWebRTCライブラリを更新し、動作の安定性を向上させました。
  - Chrome71以前、iOS/Android SDK 1.0.6以前、WebRTC Gateway v0.1.0以前との疎通ができなくなります。 ([参考](https://support.skyway.io/hc/articles/900005631283))
- カメラが3つ以上ある端末において `switchCamera` 実行時に、前面カメラと背面カメラが交互に切り替わるようにしました。

### Fixed

- `Peer` がシグナリングサーバと自動再接続した際に、各種操作が正常に動作しない不具合を修正しました。
- Peer認証に利用されるCredentialの更新後に `Peer` がシグナリングサーバと自動再接続する際に、更新前の `Credential` を利用してしまう不具合を修正しました。
- ProGuard利用時にアプリがクラッシュする不具合を修正しました。
- `Canvas.ScalingEnum.ASPECT_FIT`が正常に動作しない不具合を修正しました。
- セキュリティの強化を実施しました。

## [Version 2.6.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.6.0) - 2021-02-16

### Deprecated

- `Peer`の`reconnect`メソッドを非推奨にしました。代わりに`Peer`の再作成を推奨します。
- `Peer`の`disconnect`メソッドを非推奨にしました。代わりに`Peer`の`destroy`メソッドの使用を推奨します。
- `PeerEventEnum`の`DISCONNECTED`イベントを非推奨にしました。代わりに`PeerEventEnum`の`CLOSE`イベントの使用を推奨します。
- `RoomEventEnum`の`REMOVE_STREAM`イベントを非推奨にしました。代わりに`RoomEventEnum`の`PEER_LEAVE`イベントの使用を推奨します。


## [Version 2.5.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.5.0) - 2021-02-02

### Changed

- `dataChannel.send`の送信間隔を変更し、100msec未満の間隔で連続送信する際も遅延なくデータの送信を行うことができるようになりました。

## [Version 2.4.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.4.0) - 2021-01-19

### Fixed

- シグナリングサーバへの接続プロセスを修正し、`Peer` からディスパッチャーサーバへのリクエストが失敗した場合に再接続するようにしました。

## [Version 2.3.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.3.0) - 2020-12-22

### Added

- Peerの存在を確認することができる `fetchPeerExists` メソッドを追加しました。1秒に1回利用することが可能です。

### Fixed

- PeerIDを指定せずに生成したPeerが、モバイル端末のネットワーク切断等でシグナリングサーバと自動再接続する際に、PeerIDが変わってしまう不具合を修正しました。
- APIキー認証 (Peer認証) に使用されるCredentialの更新後にPeerがシグナリングサーバと自動再接続する際、再接続に失敗する不具合を修正しました。
- Peerがシグナリングサーバと自動再接続する際に、Peerのopenイベントが再発火する不具合を修正しました。

## [Version 2.2.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.2.0) - 2020-11-16

### Added

- Peer オプションに `tryReconnectMedia` と `tryReconnectData` のオプションを追加しました。このオプションを有効にすることで、WebRTC通信が一時的な切断状態になった場合に自動再接続を試行するようになります。デフォルトではこのオプションは無効です。

## [Version 2.1.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.1.0) - 2020-11-10

### Added
- `Connection.close` に `forceClose` オプションを追加しました。このオプションを有効にすると、接続相手においても `Connection` が即座にクローズします。

### Deprecated
- `forceClose` のデフォルト値 `false` は将来のバージョンでは `true` に変更される可能性があります。

## [Version 2.0.4](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.4) - 2020-10-05

### Fixed

- 通信状態が不安定等の理由により、WebRTC通信が一時的な切断状態になった場合に、自動で再接続を試行するように修正しました。

## [Version 2.0.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.3) - 2020-08-31

### Fixed

- メディア通信の疎通を一定回数以上繰り返すとアプリがクラッシュする不具合を修正しました。
- 特定の機種でgetUserMedia()を繰り返し実行するとクラッシュする不具合を修正しました。

## [Version 2.0.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.2) - 2020-06-30

### Fixed

- SCTPに関する脆弱性への対応を行いました。

## [Version 2.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.1) - 2020-06-22

### Fixed

- 特定の端末でTLS サーバー証明書の検証時にクラッシュする不具合を修正しました。

## [Version 2.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.0) - 2020-06-09

### Breaking Changes

- `PeerError.message`をpublicプロパティからprivateプロパティに変更しました。
    - `PeerError.message`を取得するには、`PeerError.getMessage()`を使用してください。

- `MeshRoom`, `SFURoom`の`send`関数を使用してデータを送信する際、その送信間隔に制限を設けました。
  - 連続してデータを送信する頻度は 100 ミリ秒に 1 回までに制限されます。
  - 制限を超えたデータはキューイングされ、100 ミリ秒ごとに順次送信されます。

## [Version 1.3.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.3.0) - 2020-05-27

### Added

- 端末画面のメディアストリームを取得する`getDisplayMedia`を追加しました。

## [Version 1.2.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.2.0) - 2020-04-28

### Added

- `MediaConnection`, `DataConnection`に接続中の統計情報を取得する`getStats`を追加しました。

### Fixed

- 同じ`MeshRoom`に対して多数のユーザが同時に`Peer.joinRoom`した際に、エラーが発生する不具合を修正しました。

### Modified

- `MeshRoom`, `SFURoom`の`send`関数において、送信可能なデータのサイズ上限を20MBに変更しました。

## [Version 1.1.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.3) - 2019-09-02

### Fixed

- 呼び出しタイミングによって、MeshRoom.close()がデッドロックに陥り、アプリをフリーズさせる可能性がある不具合を修正しました。

## [Version 1.1.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.2) - 2019-05-20

### Modified
- Canvas.ScalingEnum.FILLをdeprecatedに変更しました。

### Fixed

- Canvas.ScalingEnum.ASPECT_FITが正常に動作しない不具合を修正しました。
- SFU RoomとMesh Roomのサンプルアプリケーションで映像が表示されない不具合を修正しました。
- 同一のCanvasに対して、removeVideoRenderer、addVideoRendererを実行すると表示が崩れる不具合を修正しました。

## [Version 1.1.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.1) - 2019-04-09

### Modified

- API認証エラー発生を示すPeerErrorEnum.AUTHENTICATIONをPeerErrorに追加しました。
- PeerクラスにSDKのバージョンを確認するためのメソッド`getSdkVersion`を追加しました。

### Fixed

- v1.1.0にてAPI認証に失敗する不具合を修正しました。
- listAllPeers呼び出し時に、取得件数が512より大きい時にクラッシュする不具合を修正しました。

## [Version 1.1.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.0) - 2019-03-18

### Modified
- 動作環境をAndroid 5.0(API21)以上に変更しました。

### Fixed
- FirefoxからAndroidへの発信し、DataConnectionで文字列を送信するとクラッシュする不具合を修正しました。

## [Version 1.0.6](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.6) - 2018-09-04

### Fixed

- Peer.destroy()後にgetUserMedia()を呼んだ場合、クラッシュする不具合を解消しました。
- v.1.0.5とは別の要因により、サーバへの接続失敗時に稀にクラッシュする不具合を解消しました。

## [Version 1.0.5](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.5) - 2018-08-09

### Fixed

- サーバへの接続失敗時に稀にクラッシュする不具合を解消しました。

## [Version 1.0.4](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.4) - 2018-04-10

### Fixed

- disconnectメソッド後にdestroyメソッドを呼ぶとクラッシュする不具合を解消しました。

## [Version 1.0.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.3) - 2018-03-16

### Fixed

- reconnectメソッドが正しく動作しない不具合を解消しました。
- listAllPeersメソッドのAPIエラー時の不具合を解消しました。
- DataConnectionの終了処理の不具合を解消しました。
- DataConnectionのメモリリークを解消しました。
- DataConnectionの確立時にクラッシュする事がある不具合を解消しました。
- MeshRoomから退室後、再入室できない事がある不具合を解消しました。

## [Version 1.0.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.2) - 2017-10-27

### Fixed

- フロントカメラを持たない端末の場合、バックカメラを使用することで、アプリが落ちる不具合を解消しました。
- デバッグログレベルの指定が無効になっていた不具合を解消しました。
- IPv6環境下での動作不具合を解消しました。

## [Version 1.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.1) - 2017-09-15

### Fixed

- Media/DataConnectionクラスのmetadataオプションが設定されない不具合を解消しました。

## [Version 1.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.0) - 2017-09-05

- first release
