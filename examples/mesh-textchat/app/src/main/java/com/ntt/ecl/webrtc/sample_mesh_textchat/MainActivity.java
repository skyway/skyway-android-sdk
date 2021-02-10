package com.ntt.ecl.webrtc.sample_mesh_textchat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import io.skyway.Peer.OnCallback;
import io.skyway.Peer.Peer;
import io.skyway.Peer.PeerError;
import io.skyway.Peer.PeerOption;
import io.skyway.Peer.Room;
import io.skyway.Peer.RoomDataMessage;
import io.skyway.Peer.RoomOption;

/**
 *
 * MainActivity.java
 * ECL WebRTC mesh text-chat sample
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
	private Room			_room;
	private ListViewAdapter _adapter;

	private String			_strOwnId;
	private boolean			_bConnected;

	private Handler			_handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		_handler = new Handler(Looper.getMainLooper());

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
			}
		});

		_peer.on(Peer.PeerEventEnum.CLOSE, new OnCallback()	{
			@Override
			public void onCallback(Object object) {
				Log.d(TAG, "[On/Close]");
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

		Button btnSend = (Button) findViewById(R.id.btnSend);
		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Send data
				sendData();
			}
		});

		//
		// Set ListView for text message logs
		//
		ListView listView = (ListView)findViewById(R.id.listView);
		if (null != listView) {
			_adapter = new ListViewAdapter(this);
			listView.setAdapter(_adapter);
		}

	}

	@Override
	protected void onStart() {
		super.onStart();

		// Hide the status bar.
		View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

		// Disable Sleep and Screen Lock
		Window wnd = getWindow();
		wnd.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		wnd.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
		super.onDestroy();
	}

	//
	// Join the room
	//
	void joinRoom() {
		if ((null == _peer) || (null == _strOwnId) || (0 == _strOwnId.length())) {
			Toast.makeText(MainActivity.this, "Your PeerID is null or invalid.", Toast.LENGTH_LONG).show();
			return;
		}

		// Get room name
		EditText edtRoomName = (EditText)findViewById(R.id.txRoomName);
		String roomName = edtRoomName.getText().toString();
		if (TextUtils.isEmpty(roomName)) {
			Toast.makeText(MainActivity.this, "You should input room name.", Toast.LENGTH_LONG).show();
			return;
		}

		RoomOption option = new RoomOption();
		option.mode = RoomOption.RoomModeEnum.MESH;

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
				Toast.makeText(MainActivity.this, "Enter Room: " + roomName, Toast.LENGTH_LONG).show();
			}
		});

		_room.on(Room.RoomEventEnum.CLOSE, new OnCallback() {
			@Override
			public void onCallback(Object object) {
				String roomName = (String)object;
				Log.i(TAG, "Leave Room: " + roomName);
				Toast.makeText(MainActivity.this, "Leave Room: " + roomName, Toast.LENGTH_LONG).show();

				// Unset callbacks
				_room.on(Room.RoomEventEnum.OPEN, null);
				_room.on(Room.RoomEventEnum.CLOSE, null);
				_room.on(Room.RoomEventEnum.ERROR, null);
				_room.on(Room.RoomEventEnum.PEER_JOIN, null);
				_room.on(Room.RoomEventEnum.PEER_LEAVE, null);
				_room.on(Room.RoomEventEnum.STREAM, null);

				_room = null;
				_bConnected = false;
				updateActionButtonTitle();
			}
		});

		_room.on(Room.RoomEventEnum.ERROR, new OnCallback()	{
			@Override
			public void onCallback(Object object) {
				PeerError error = (PeerError) object;
				Log.d(TAG, "RoomEventEnum.ERROR:" + error);
			}
		});

		_room.on(Room.RoomEventEnum.PEER_JOIN, new OnCallback()	{
			@Override
			public void onCallback(Object object) {
				Log.d(TAG, "RoomEventEnum.PEER_JOIN:");

				if (!(object instanceof String)) return;

				final String peerId = (String)object;
				Log.i(TAG, "Join Room: " + peerId);
				Toast.makeText(MainActivity.this, peerId + " has joined.", Toast.LENGTH_LONG).show();
			}
		});
		_room.on(Room.RoomEventEnum.PEER_LEAVE, new OnCallback() {
			@Override
			public void onCallback(Object object) {
				Log.d(TAG, "RoomEventEnum.PEER_LEAVE:");

				if (!(object instanceof String)) return;

				String peerId = (String)object;
				Log.i(TAG, "Leave Room: " + peerId);
				Toast.makeText(MainActivity.this, peerId + " has left.", Toast.LENGTH_LONG).show();
			}
		});
		_room.on(Room.RoomEventEnum.DATA, new OnCallback() {
			@Override
			public void onCallback(Object object) {
				Log.d(TAG, "RoomEventEnum.DATA:");

				if (!(object instanceof RoomDataMessage)) return;

				RoomDataMessage msg = (RoomDataMessage)object;
				ListViewAdapter.ChatData data = new ListViewAdapter.ChatData();
				data.received = new Date();
				data.peerId = msg.src;
				if (msg.data instanceof String)	{
					data.message = (String)msg.data;
				}

				_adapter.add(data);
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
	// Send data
	//
	void sendData() {
		if (null == _peer || null == _room) {
			return;
		}

		EditText txMessage = (EditText) findViewById(R.id.txMessage);
		String data = txMessage.getText().toString();
		if (TextUtils.isEmpty(data)) return;
		_room.send(data);
		txMessage.getEditableText().clear();
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
