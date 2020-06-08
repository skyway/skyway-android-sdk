# SkyWay Android SDK release notes

[English](./release-notes.en.md)

## [Version 2.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.0)

### Breaking Changes

- `PeerError.message`をpublicプロパティからprivateプロパティに変更しました。
    - `PeerError.message`を取得するには、`PeerError.getMessage()`を使用してください。

### Modified

- `MeshRoom`, `SFURoom`の`send`関数を使用してデータを送信する際、その送信間隔に制限を設けました。
  - 連続してデータを送信する頻度は 100 ミリ秒に 1 回までに制限されます。
  - 制限を超えたデータはキューイングされ、100 ミリ秒ごとに順次送信されます。

## [Version 1.3.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.3.0)

### Added

- 端末画面のメディアストリームを取得する`getDisplayMedia`を追加しました

## [Version 1.2.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.2.0)

### Added

- `MediaConnection`, `DataConnection`に接続中の統計情報を取得する`getStats`を追加しました。

### Fixed

- 同じ`MeshRoom`に対して多数のユーザが同時に`Peer.joinRoom`した際に、エラーが発生する不具合を修正しました

### Modified

- `MeshRoom`, `SFURoom`の`send`関数において、送信可能なデータのサイズ上限を20MBに変更しました

## [Version 1.1.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.3)

### Fixed

- 呼び出しタイミングによって、MeshRoom.close()がデッドロックに陥り、アプリをフリーズさせる可能性がある不具合を修正しました。

## [Version 1.1.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.2)

### Modified
- Canvas.ScalingEnum.FILLをdeprecatedに変更しました。

### Fixed

- Canvas.ScalingEnum.ASPECT_FITが正常に動作しない不具合を修正しました。
- SFU RoomとMesh Roomのサンプルアプリケーションで映像が表示されない不具合を修正しました。
- 同一のCanvasに対して、removeVideoRenderer、addVideoRendererを実行すると表示が崩れる不具合を修正しました。

## [Version 1.1.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.1)

### Modified

- API認証エラー発生を示すPeerErrorEnum.AUTHENTICATIONをPeerErrorに追加しました。
- PeerクラスにSDKのバージョンを確認するためのメソッド`getSdkVersion`を追加しました。

### Fixed

- v1.1.0にてAPI認証に失敗する不具合を修正しました。
- listAllPeers呼び出し時に、取得件数が512より大きい時にクラッシュする不具合を修正しました。

## [Version 1.1.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.0)

### Modified
- 動作環境をAndroid 5.0(API21)以上に変更しました。

### Fixed
- FirefoxからAndroidへの発信し、DataConnectionで文字列を送信するとクラッシュする不具合を修正しました。

## [Version 1.0.6](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.6)

### Fixed

- Peer.destroy()後にgetUserMedia()を呼んだ場合、クラッシュする不具合を解消しました。
- v.1.0.5とは別の要因により、サーバへの接続失敗時に稀にクラッシュする不具合を解消しました。

## [Version 1.0.5](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.5)

### Fixed

- サーバへの接続失敗時に稀にクラッシュする不具合を解消しました。

## [Version 1.0.4](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.4)

### Fixed

- disconnectメソッド後にdestroyメソッドを呼ぶとクラッシュする不具合を解消しました。

## [Version 1.0.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.3)

### Fixed

- reconnectメソッドが正しく動作しない不具合を解消しました。
- listAllPeersメソッドのAPIエラー時の不具合を解消しました。
- DataConnectionの終了処理の不具合を解消しました。
- DataConnectionのメモリリークを解消しました。
- DataConnectionの確立時にクラッシュする事がある不具合を解消しました。
- MeshRoomから退室後、再入室できない事がある不具合を解消しました。

## [Version 1.0.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.2)

### Fixed

- フロントカメラを持たない端末の場合、バックカメラを使用することで、アプリが落ちる不具合を解消しました。
- デバッグログレベルの指定が無効になっていた不具合を解消しました。
- IPv6環境下での動作不具合を解消しました。

## [Version 1.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.1)

### Fixed

- Media/DataConnectionクラスのmetadataオプションが設定されない不具合を解消しました。

## [Version 1.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.0)

- first release
