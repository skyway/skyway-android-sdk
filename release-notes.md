# SkyWay Android SDK release notes

[English](./release-notes.en.md)

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
