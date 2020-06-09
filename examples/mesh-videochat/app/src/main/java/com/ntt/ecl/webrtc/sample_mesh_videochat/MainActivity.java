package com.ntt.ecl.webrtc.sample_mesh_videochat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import io.skyway.Peer.*;
import io.skyway.Peer.Browser.Canvas;
import io.skyway.Peer.Browser.MediaConstraints;
import io.skyway.Peer.Browser.MediaStream;
import io.skyway.Peer.Browser.Navigator;

/**
 *
 * MainActivity.java
 * ECL WebRTC mesh video-chat sample
 *
 */
public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();

	//
	// Set your APIkey and Domain
	//
	private static final String API_KEY = "yourAPIKEY";
	private static final String DOMAIN = "yourDomain";

	private Peer			_peer;
	private MediaStream		_localStream;
	private Room            _room;
	private RemoteViewAdapter   _adapter;

	private String			_strOwnId;
	private boolean			_bConnected;

	private Handler			_handler;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Window wnd = getWindow();
		wnd.addFlags(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		_handler = new Handler(Looper.getMainLooper());
		final Activity activity = this;

		//
		// Initialize Peer
		//
		PeerOption option = new PeerOption();
		option.key = API_KEY;
		option.domain = DOMAIN;
		_peer = new Peer(this, option);

		//
		// Set Peer event callbacks
		//

		// OPEN
		_peer.on(Peer.PeerEventEnum.OPEN, new OnCallback() {
			@Override
			public void onCallback(Object object) {

				// Show my ID
				_strOwnId = (String) object;
				TextView tvOwnId = (TextView) findViewById(R.id.tvOwnId);
				tvOwnId.setText(_strOwnId);

				// Request permissions
				if (ContextCompat.checkSelfPermission(activity,
						Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity,
						Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},0);
				}
				else {
					// Get a local MediaStream & show it
					startLocalStream();
				}

			}
		});

		_peer.on(Peer.PeerEventEnum.CLOSE, new OnCallback()	{
			@Override
			public void onCallback(Object object) {
				Log.d(TAG, "[On/Close]");
			}
		});
		_peer.on(Peer.PeerEventEnum.DISCONNECTED, new OnCallback() {
			@Override
			public void onCallback(Object object) {
				Log.d(TAG, "[On/Disconnected]");
			}
		});
		_peer.on(Peer.PeerEventEnum.ERROR, new OnCallback() {
			@Override
			public void onCallback(Object object) {
				PeerError error = (PeerError) object;
				Log.d(TAG, "[On/Error]" + error.getMessage());
			}
		});


		//
		// Set GUI event listeners
		//

		Button btnAction = (Button) findViewById(R.id.btnAction);
		btnAction.setEnabled(true);
		btnAction.setOnClickListener(new View.OnClickListener()	{
			@Override
			public void onClick(View v)	{
				v.setEnabled(false);

				if (!_bConnected) {
					// Join room
					joinRoom();
				}
				else {
					// Leave room
					leaveRoom();
				}

				v.setEnabled(true);
			}
		});

		Button switchCameraAction = (Button)findViewById(R.id.switchCameraAction);
		switchCameraAction.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)	{
				if(null != _localStream){
					Boolean result = _localStream.switchCamera();
					if(true == result)	{
						//Success
					}
					else {
						//Failed
					}
				}

			}
		});

		//
		// Set GridView for Remote Video Stream
		//
		GridView grdRemote = (GridView)findViewById(R.id.grdRemote);
		if (null != grdRemote)
		{
			_adapter = new RemoteViewAdapter(this);
			grdRemote.setAdapter(_adapter);
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 0: {
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					startLocalStream();
				}
				else {
					Toast.makeText(this,"Failed to access the camera and microphone.\nclick allow when asked for permission.", Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		// Disable Sleep and Screen Lock
		Window wnd = getWindow();
		wnd.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		wnd.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Set volume control stream type to WebRTC audio.
		setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
	}

	@Override
	protected void onPause() {
		// Set default volume control stream type.
		setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
		super.onPause();
	}

	@Override
	protected void onStop()	{
		// Enable Sleep and Screen Lock
		Window wnd = getWindow();
		wnd.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		wnd.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		destroyPeer();
		super.onDestroy();
	}

	//
	// Get a local MediaStream & show it
	//
	void startLocalStream() {
		Navigator.initialize(_peer);
		MediaConstraints constraints = new MediaConstraints();
		_localStream = Navigator.getUserMedia(constraints);

		Canvas canvas = (Canvas) findViewById(R.id.svLocalView);
		_localStream.addVideoRenderer(canvas,0);
	}

	//
	// Clean up objects
	//
	private void destroyPeer() {
		leaveRoom();

		if (null != _localStream) {
			Canvas canvas = (Canvas) findViewById(R.id.svLocalView);
			_localStream.removeVideoRenderer(canvas,0);
			_localStream.close();
		}

		Navigator.terminate();

		if (null != _peer) {
			unsetPeerCallback(_peer);
			if (!_peer.isDisconnected()) {
				_peer.disconnect();
			}

			if (!_peer.isDestroyed()) {
				_peer.destroy();
			}

			_peer = null;
		}
	}

	//
	// Unset callbacks for PeerEvents
	//
	void unsetPeerCallback(Peer peer) {
		if(null == _peer){
			return;
		}

		peer.on(Peer.PeerEventEnum.OPEN, null);
		peer.on(Peer.PeerEventEnum.CONNECTION, null);
		peer.on(Peer.PeerEventEnum.CALL, null);
		peer.on(Peer.PeerEventEnum.CLOSE, null);
		peer.on(Peer.PeerEventEnum.DISCONNECTED, null);
		peer.on(Peer.PeerEventEnum.ERROR, null);
	}

	//
	// Join the room
	//
	void joinRoom() {
		if ((null == _peer) || (null == _strOwnId) || (0 == _strOwnId.length())) {
			Toast.makeText(this, "Your PeerID is null or invalid.", Toast.LENGTH_SHORT).show();
			return;
		}

		// Get room name
		EditText edtRoomName = (EditText)findViewById(R.id.txRoomName);
		String roomName = edtRoomName.getText().toString();
		if (TextUtils.isEmpty(roomName)) {
			Toast.makeText(this, "You should input room name.", Toast.LENGTH_SHORT).show();
			return;
		}

		RoomOption option = new RoomOption();
		option.mode = RoomOption.RoomModeEnum.MESH;
		option.stream = _localStream;

		// Join Room
		_room = _peer.joinRoom(roomName, option);
		_bConnected = true;

		//
		// Set Callbacks
		//
		_room.on(Room.RoomEventEnum.OPEN, new OnCallback() {
			@Override
			public void onCallback(Object object) {
				if (!(object instanceof String)) return;

				String roomName = (String)object;
				Log.i(TAG, "Enter Room: " + roomName);
				Toast.makeText(MainActivity.this, "Enter Room: " + roomName, Toast.LENGTH_SHORT).show();
			}
		});

		_room.on(Room.RoomEventEnum.CLOSE, new OnCallback() {
			@Override
			public void onCallback(Object object) {
				String roomName = (String)object;
				Log.i(TAG, "Leave Room: " + roomName);
				Toast.makeText(MainActivity.this, "Leave Room: " + roomName, Toast.LENGTH_LONG).show();

				// Remove all streams
				_adapter.removeAllRenderers();

				// Unset callbacks
				_room.on(Room.RoomEventEnum.OPEN, null);
				_room.on(Room.RoomEventEnum.CLOSE, null);
				_room.on(Room.RoomEventEnum.ERROR, null);
				_room.on(Room.RoomEventEnum.PEER_JOIN, null);
				_room.on(Room.RoomEventEnum.PEER_LEAVE, null);
				_room.on(Room.RoomEventEnum.STREAM, null);
				_room.on(Room.RoomEventEnum.REMOVE_STREAM, null);

				_room = null;
				_bConnected = false;
				updateActionButtonTitle();
			}
		});

		_room.on(Room.RoomEventEnum.ERROR, new OnCallback()
		{
			@Override
			public void onCallback(Object object)
			{
				PeerError error = (PeerError) object;
				Log.d(TAG, "RoomEventEnum.ERROR:" + error);
			}
		});

		_room.on(Room.RoomEventEnum.PEER_JOIN, new OnCallback()
		{
			@Override
			public void onCallback(Object object)
			{
				Log.d(TAG, "RoomEventEnum.PEER_JOIN:");

				if (!(object instanceof String)) return;

				String peerId = (String)object;
				Log.i(TAG, "Join Room: " + peerId);
				Toast.makeText(MainActivity.this, peerId + " has joined.", Toast.LENGTH_LONG).show();
			}
		});
		_room.on(Room.RoomEventEnum.PEER_LEAVE, new OnCallback()
		{
			@Override
			public void onCallback(Object object)
			{
				Log.d(TAG, "RoomEventEnum.PEER_LEAVE:");

				if (!(object instanceof String)) return;

				String peerId = (String)object;
				Log.i(TAG, "Leave Room: " + peerId);
				Toast.makeText(MainActivity.this, peerId + " has left.", Toast.LENGTH_LONG).show();

				_adapter.remove(peerId);
			}
		});

		_room.on(Room.RoomEventEnum.STREAM, new OnCallback()
		{
			@Override
			public void onCallback(Object object)
			{
				Log.d(TAG, "RoomEventEnum.STREAM: + " + object);

				if (!(object instanceof MediaStream)) return;

				final MediaStream stream = (MediaStream)object;
				Log.d(TAG, "peer = " + stream.getPeerId() + ", label = " + stream.getLabel());

				_adapter.add(stream);
			}
		});

		_room.on(Room.RoomEventEnum.REMOVE_STREAM, new OnCallback()
		{
			@Override
			public void onCallback(Object object)
			{
				Log.d(TAG, "RoomEventEnum.REMOVE_STREAM: " + object);

				if (!(object instanceof MediaStream)) return;

				final MediaStream stream = (MediaStream)object;
				Log.d(TAG, "peer = " + stream.getPeerId() + ", label = " + stream.getLabel());

				_adapter.remove(stream);
			}
		});

		// Update UI
		updateActionButtonTitle();
	}

	//
	// Leave the room
	//
	void leaveRoom() {
		if (null == _peer || null == _room) {
			return;
		}
		_room.close();
	}

	//
	// Update actionButton title
	//
	void updateActionButtonTitle() {
		_handler.post(new Runnable() {
			@Override
			public void run() {
				Button btnAction = (Button) findViewById(R.id.btnAction);
				if (null != btnAction) {
					if (!_bConnected) {
						btnAction.setText("Join Room");
					} else {
						btnAction.setText("Leave Room");
					}
				}
			}
		});
	}

}
