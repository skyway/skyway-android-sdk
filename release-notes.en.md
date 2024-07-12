# SkyWay Android SDK release notes

[日本語](./release-notes.md)

## [Version 4.0.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v4.0.3) - 2024-07-12

### Fixed

- Fix rules for proguard.

## [Version 4.0.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v4.0.2) - 2024-06-05

### Changed

- New SkyWay and old SkyWay can be used in one app.
  - However, simultaneous use is not supported.
  - Before switching old or new SkyWay, please complete SDK termination process (call `Dispose`/`Destory`, free the object).


## [Version 4.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v4.0.1) - 2022-12-02

### Fixed

- Fixed a bug that `Canvas.ScalingEnum.ASPECT_FIT` does not work properly.

## [Version 4.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v4.0.0) - 2022-11-11

### Breaking Changed
- Updated internal WebRTC libraries to improve stability.
  - The structure of statistics that can be obtained using the `getStats()` method has been partially changed.

- It has been reported that an error message "Vulnerable WebRTC version" is displayed when an application using an Android SDK older than v4.0.0 is published to the Google Play Store.
  - Known vulnerabilities have been addressed in v2.0.2, but please use the latest SDK as much as possible.
  - This error message will be resolved by updating the Android SDK to v4.0.0.

## [Version 3.1.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v3.1.1) - 2022-09-07

### Fixed

- Fixed a bug that could cause the application to crash after disconnecting the connection.

## [Version 3.1.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v3.1.0) - 2022-05-10

### Added

- Added SNI information to the request header when sending a request to the server.

## [Version 3.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v3.0.1) - 2021-07-09

### Fixed

- Fixed a rare crash when decode VP8 video codec by hardware decoder

## [Version 3.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v3.0.0) - 2021-05-27

### Breaking Changed

- Updated internal WebRTC libraries to improve stability.
  - This version fails to negotiate and communicate with Chrome 71 or earlier, Android SDK v1.0.6 or earlier, iOS SDK v1.0.6 or earlier, and WebRTC Gateway v0.1.0 or earlier.
 ([Ref](https://support.skyway.io/hc/articles/900005631283))
- The front and rear cameras are switched alternately when `switchCamera` is executed on devices with three or more cameras.

### Fixed

- Fixed a bug that various operations did not work properly when the `Peer` automatically reconnected to a signaling server.
- Fixed a bug that after updating a credential used for Peer authentication, caused a `Peer` uses old credential when it automatically reconnected to a signaling server.
- Fixed a bug that caused the app to crash when using ProGuard.
- Fixed a bug that `Canvas.ScalingEnum.ASPECT_FIT` does not work properly.
- Enhanced security.

## [Version 2.6.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.6.0) - 2021-02-16

### Deprecated

- Deprecated `reconnect` method of `Peer`. Recreate `Peer` instance instead.
- Deprecated `disconnect` method of `Peer`. Use `disconnect` method of `Peer` instead.
- Deprecated `DISCONNECTED` event of `PeerEventEnum`. Use `CLOSE` event of `PeerEventEnum` instead.
- Deprecated `REMOVE_STREAM` event of `RoomEventEnum`. Use `PEER_LEAVE` evnet of `RoomEventEnum` instead.


## [Version 2.5.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.5.0) - 2021-02-02

### Changed

- Changed `dataChannel.send()` interval so that multiple data can be transmitted without delay even when those data are continuously transmitted at an intervals of less than 100 msec.

## [Version 2.4.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.4.0) - 2021-01-19

### Fixed

- Fixed a connecting process to signaling server so that `Peer` would reconnect when a request to the dispatcher server failed.

## [Version 2.3.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.3.0) - 2020-12-22

### Added

- Added `fetchPeerExists` API to fetch whether a peer exists. You can call this API once per second per peer.

### Fixed

- Fixed a bug that the PeerID would change when a Peer created without specifying PeerID automatically reconnected to SkyWay's signaling server due to reasons such as network disconnection of the mobile device.
- Fixed a bug that after updating a credential used for API key authentication (Peer authentication), caused a Peer to fail to reconnect to SkyWay's signaling server when it automatically reconnected.
- Fixed a bug that caused the open event of Peer to fire again when the Peer automatically reconnected to SkyWay's signaling server.

## [Version 2.2.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.2.0) - 2020-11-16

### Added

- Added `tryReconnectMedia` and `tryReconnectData` option to `PeerOption`. This enables to try to reconnect automatically when the WebRTC communication state is temporarily disconnected due to unstable communication or other reasons. The default value is false.

## [Version 2.1.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.1.0) - 2020-11-10

### Added
- Add an `forceClose` option when calling `Connection.close` to signal intention to disconnection to the remote peer instantly.

### Deprecated
- The `false` default value of `forceClose` is deprecated and may be changed to `true` in future versions.

## [Version 2.0.4](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.4) - 2020-10-05

### Fixed
- Fixed to automatically try to reconnect when WebRTC communication state is temporarily disconnected due to unstable communication or other reasons.

## [Version 2.0.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.3) - 2020-08-31

### Fixed

- Fixed a bug that caused the app to crash after repeating establishing media communication more than a certain number of times.
- Fixed a bug that caused a crash when repeatedly executing getUserMedia() on certain models.

## [Version 2.0.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.2) - 2020-06-30

### Fixed

- Addressed a vulnerability in SCTP.

## [Version 2.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.1) - 2020-06-22

### Fixed

- Fixed a crash when verifying a TLS server certificate on certain devices.

## [Version 2.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.0) - 2020-06-09

### Breaking Changes

- Changed `PeerError.message` to private property not public.
    - Use `PeerError.getMessage()` to acceess `PeerError.message`.

- Limited the interval between consecutive data sendings by using SFU/MeshRoom.send().
  - The frequency of consecutive data sending is limited to once every 100 msec.
  - The data that exceeds the limit is queued and sent sequentially every 100 msec.

## [Version 1.3.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.3.0) - 2020-05-27

### Added

- Added `getDisplayMedia` to get device screen stream.

## [Version 1.2.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.2.0) - 2020-04-28

### Added

- Added `getStats` function on `MediaConnection`, `DataConnection` for getting statistics of connection.

### Fixed

- Fixed a bug that error occurs when many users `Peer.joinRoom` at the same time in Mesh room.

### Modified

- Changed the max size of data to 20MB when calling `MeshRoom.send` or `SFURoom.send`.

## [Version 1.1.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.3) - 2019-09-02

### Fixed

- Fixed a bug that could cause MeshRoom.close() to deadlock and freeze the app depending on the timing of calling it.

## [Version 1.1.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.2) - 2019-05-20

### Modified

- Deprecated Canvas.ScalingEnum.FILL.

### Fixed

- Fixed a bug that Canvas.ScalingEnum.ASPECT_FIT does not work properly.
- Fixed a bug that stream are not shown in sample application of sfu room and that of mesh room.
- Fixed a bug that the display collapses when using removeVideoRenderer and addVideoRenderer for the same Canvas.

## [Version 1.1.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.1) - 2019-04-09

### Modified

- Added PeerErrorEnum.AUTHENTICATION to PeerError indicating that API authentication error occurred.
- Added the method `getSdkVersion` to check the SDK version in the Peer class.

### Fixed

- Fixed a bug that API authentication fails in v1.1.0.
- Fixed a bug that crashes when the number of acquired items is larger than 512 when calling listAllPeers.

## [Version 1.1.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.0) - 2019-03-18

### Modified
- Target Android API is now 21.

### Fixed
- Fixed bug that causes a crash of the app when connecting DataConnection between Android app and Firefox 63.0 and later.

## [Version 1.0.6](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.6) - 2018-09-04

### Fixed

- Fixed bug that causes crash of the app when call getUserMedia() after calling Peer.destroy().
- Fixed bug that rarely causes crash of the app when failed to connect the server by the other factor of v1.0.5.

## [Version 1.0.5](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.5) - 2018-08-09

### Fixed

- Fixed bug that rarely causes crash of the app when connection to server fails.

## [Version 1.0.4](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.4) - 2018-04-10

### Fixed

- Fixed bug that causes crash of the app when call destroy after calling disconnect.

## [Version 1.0.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.3) - 2018-03-16

### Fixed

- Fixed bug in reconnect method.
- Fixed bug in listAllPeers method when the API fails.
- Fixed bug in finalizing process of DataConnection.
- Fixed possible memory leak in DataConnection.
- Fixed bug that causes crash of the app when connecting DataConnection.
- Fixed bug that re-joining to the same room after leaving it may fail.

## [Version 1.0.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.2) - 2017-10-27

### Fixed

- Fixed bug that the application halts when the device doesn't have a front camera.
- Fixed bug that the debugging log level is ignored.
- Fixed bug in an IPv6 environment.

## [Version 1.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.1) - 2017-09-15

### Fixed

- Fixed bug that metadata on Media/DataConnection class was not set.

## [Version 1.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.0) - 2017-09-05

- first release
