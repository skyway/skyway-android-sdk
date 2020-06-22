# SkyWay Android SDK release notes

[日本語](./release-notes.md)

## [Version 2.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.1)

### Fixed

- Fixed a crash when verifying a TLS server certificate on certain devices. 


## [Version 2.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v2.0.0)

### Breaking Changes

- Changed `PeerError.message` to private property not public.
    - Use `PeerError.getMessage()` to acceess `PeerError.message`.

- Limited the interval between consecutive data sendings by using SFU/MeshRoom.send().
  - The frequency of consecutive data sending is limited to once every 100 msec.
  - The data that exceeds the limit is queued and sent sequentially every 100 msec.

## [Version 1.3.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.3.0)

### Added

- Added `getDisplayMedia` to get device screen stream.

## [Version 1.2.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.2.0)

### Added

- Added `getStats` function on `MediaConnection`, `DataConnection` for getting statistics of connection.

### Fixed

- Fixed a bug that error occurs when many users `Peer.joinRoom` at the same time in Mesh room.

### Modified

- Changed the max size of data to 20MB when calling `MeshRoom.send` or `SFURoom.send`.

## [Version 1.1.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.3)

### Fixed

- Fixed a bug that could cause MeshRoom.close() to deadlock and freeze the app depending on the timing of calling it.

## [Version 1.1.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.2)

### Modified

- Deprecated Canvas.ScalingEnum.FILL.

### Fixed

- Fixed a bug that Canvas.ScalingEnum.ASPECT_FIT does not work properly.
- Fixed a bug that stream are not shown in sample application of sfu room and that of mesh room.
- Fixed a bug that the display collapses when using removeVideoRenderer and addVideoRenderer for the same Canvas.

## [Version 1.1.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.1)

### Modified

- Added PeerErrorEnum.AUTHENTICATION to PeerError indicating that API authentication error occurred.
- Added the method `getSdkVersion` to check the SDK version in the Peer class.

### Fixed

- Fixed a bug that API authentication fails in v1.1.0.
- Fixed a bug that crashes when the number of acquired items is larger than 512 when calling listAllPeers.

## [Version 1.1.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.1.0)

### Modified
- Target Android API is now 21.

### Fixed
- Fixed bug that causes a crash of the app when connecting DataConnection between Android app and Firefox 63.0 and later.

## [Version 1.0.6](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.6)

### Fixed

- Fixed bug that causes crash of the app when call getUserMedia() after calling Peer.destroy().
- Fixed bug that rarely causes crash of the app when failed to connect the server by the other factor of v1.0.5.

## [Version 1.0.5](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.5)

### Fixed

- Fixed bug that rarely causes crash of the app when connection to server fails.

## [Version 1.0.4](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.4)

### Fixed

- Fixed bug that causes crash of the app when call destroy after calling disconnect.

## [Version 1.0.3](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.3)

### Fixed

- Fixed bug in reconnect method.
- Fixed bug in listAllPeers method when the API fails.
- Fixed bug in finalizing process of DataConnection.
- Fixed possible memory leak in DataConnection.
- Fixed bug that causes crash of the app when connecting DataConnection.
- Fixed bug that re-joining to the same room after leaving it may fail.

## [Version 1.0.2](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.2)

### Fixed

- Fixed bug that the application halts when the device doesn't have a front camera.
- Fixed bug that the debugging log level is ignored.
- Fixed bug in an IPv6 environment.

## [Version 1.0.1](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.1)

### Fixed

- Fixed bug that metadata on Media/DataConnection class was not set.

## [Version 1.0.0](https://github.com/skyway/skyway-android-sdk/releases/tag/v1.0.0)

- first release
