package com.ntt.ecl.webrtc.sample_p2p_textchat;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.skyway.Peer.DataConnection;
import io.skyway.Peer.OnCallback;
import io.skyway.Peer.Peer;
import io.skyway.Peer.PeerError;
import io.skyway.Peer.PeerOption;
import io.skyway.Peer.ConnectOption;


/**
 *
 * MainActivity.java
 * ECL WebRTC p2p text-chat sample
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
	private DataConnection	_dataConnection;

	private String			_strOwnId;
	private boolean			_bConnected;

	private Handler			_handler;
	private TextView        _tvMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Window wnd = getWindow();
		wnd.addFlags(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		_tvMessage = (TextView) findViewById(R.id.tvMessage);

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

		// CALL (Incoming call)
		_peer.on(Peer.PeerEventEnum.CONNECTION, new OnCallback() {
			@Override
			public void onCallback(Object object) {
				if (!(object instanceof DataConnection)){
					return;
				}

				_dataConnection = (DataConnection)object;
				setDataCallbacks();
				updateActionButtonTitle();
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
				Log.d(TAG, "[On/Error]" + error.message);
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
					// Select remote peer & make a call
					showPeerIDs();
				}
				else {
					// Hang up a connection
					_dataConnection.close();
				}

				v.setEnabled(true);
			}
		});

        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setEnabled(true);
        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (_bConnected) {
                    v.setEnabled(false);

                    Spinner spDataType = (Spinner)findViewById(R.id.spDataType);
                    int iType = spDataType.getSelectedItemPosition();
                    sendData(iType);

                    v.setEnabled(true);
                }
            }
        });
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
	}

	@Override
	protected void onPause() {
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
	// Set callbacks for MediaConnection.MediaEvents
	//
	void setDataCallbacks() {

		_dataConnection.on(DataConnection.DataEventEnum.OPEN, new OnCallback() {
			@Override
			public void onCallback(Object o) {
				_bConnected = true;
				updateActionButtonTitle();
				appendLog("Connected.");
			}
		});

		_dataConnection.on(DataConnection.DataEventEnum.CLOSE, new OnCallback()	{
			@Override
			public void onCallback(Object object) {
				_bConnected = false;
				updateActionButtonTitle();
				unsetDataCallbacks();
				_dataConnection = null;
			}
		});

		_dataConnection.on(DataConnection.DataEventEnum.DATA, new OnCallback()	{
			@Override
			public void onCallback(Object object) {
				String strValue = null;

				if (object instanceof String) {
					strValue = (String) object;
				} else if (object instanceof Double) {
					Double doubleValue = (Double) object;

					strValue = doubleValue.toString();
				} else if (object instanceof ArrayList) {
					ArrayList arrayValue = (ArrayList) object;

					StringBuilder sbResult = new StringBuilder();

					for (Object item : arrayValue) {
						sbResult.append(item.toString()+"\n");
					}

					strValue = sbResult.toString();
				} else if (object instanceof Map) {
					Map mapValue = (Map) object;

					StringBuilder sbResult = new StringBuilder();

					Object[] objKeys = mapValue.keySet().toArray();
					for (Object objKey : objKeys) {
						Object objValue = mapValue.get(objKey);

						sbResult.append(objKey.toString());
						sbResult.append(" = ");
						sbResult.append(objValue.toString()+"\n");
					}

					strValue = sbResult.toString();
				} else if (object instanceof byte[]) {
					Bitmap bmp = null;
					byte[] byteArray = (byte[])object;
					if (byteArray != null) {
						bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
					}

					ImageView ivRcv = (ImageView)findViewById(R.id.ivRcv);
					ivRcv.setImageBitmap(bmp);

					strValue = "Received Image.(Type:byte[])";
				} else if(object instanceof Long){
					Log.d(TAG, "[On/DataConnection.DATA] Long");
					Long longValue = (Long)object;
					strValue = longValue.toString();
				} else if (object instanceof JSONObject) {
					JSONObject json = (JSONObject)object;
					strValue = json.toString();
				} else if (object instanceof JSONArray) {
					JSONArray json = (JSONArray)object;
					strValue = json.toString();
				} else {
					strValue = "DataType: " + object.getClass().getSimpleName();
				}
				appendLog("Remote:"+ strValue);
			}
		});

		_dataConnection.on(DataConnection.DataEventEnum.ERROR, new OnCallback()	{
			@Override
			public void onCallback(Object object) {
				PeerError error = (PeerError) object;
				Log.d(TAG, "[On/MediaError]" + error);
			}
		});

	}

	//
	// Clean up objects
	//
	private void destroyPeer() {
		if (null != _dataConnection)	{
			if (_dataConnection.isOpen()) {
				_dataConnection.close();
			}
			unsetDataCallbacks();
		}

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
	// Unset callbacks for DataConnection.DataEvents
	//
	void unsetDataCallbacks() {
		if(null == _dataConnection){
			return;
		}

		_dataConnection.on(DataConnection.DataEventEnum.OPEN, null);
		_dataConnection.on(DataConnection.DataEventEnum.CLOSE, null);
		_dataConnection.on(DataConnection.DataEventEnum.DATA, null);
		_dataConnection.on(DataConnection.DataEventEnum.ERROR, null);
	}


	//
	// Create a MediaConnection
	//
	void onPeerSelected(String strPeerId) {
		if (null == _peer) {
			return;
		}

		if (null != _dataConnection) {
			_dataConnection.close();
		}

		ConnectOption option = new ConnectOption();
		option.label = "chat";
		_dataConnection = _peer.connect(strPeerId, option);

		if (null != _dataConnection) {
			setDataCallbacks();
			_bConnected = true;
		}

		updateActionButtonTitle();
	}

	//
	// Listing all peers
	//
	void showPeerIDs() {
        if ((null == _peer) || (null == _strOwnId) || (0 == _strOwnId.length())) {
            Toast.makeText(this, "Your PeerID is null or invalid.", Toast.LENGTH_SHORT).show();
            return;
        }

		// Get all IDs connected to the server
        final Context fContext = this;
		_peer.listAllPeers(new OnCallback() {
			@Override
			public void onCallback(Object object) {
				if (!(object instanceof JSONArray)) {
					return;
				}

				JSONArray peers = (JSONArray) object;
				ArrayList<String> _listPeerIds = new ArrayList<>();
				String peerId;

				// Exclude my own ID
				for (int i = 0; peers.length() > i; i++) {
					try {
						peerId = peers.getString(i);
						if (!_strOwnId.equals(peerId)) {
							_listPeerIds.add(peerId);
						}
					} catch(Exception e){
						e.printStackTrace();
					}
				}

				// Show IDs using DialogFragment
				if (0 < _listPeerIds.size()) {
					FragmentManager mgr = getFragmentManager();
					PeerListDialogFragment dialog = new PeerListDialogFragment();
					dialog.setListener(
							new PeerListDialogFragment.PeerListDialogFragmentListener() {
								@Override
								public void onItemClick(final String item) {
									_handler.post(new Runnable() {
										@Override
										public void run() {
											onPeerSelected(item);
										}
									});
								}
							});
					dialog.setItems(_listPeerIds);
					dialog.show(mgr, "peerlist");
				}else{
                    Toast.makeText(fContext, "PeerID list (other than your ID) is empty.", Toast.LENGTH_SHORT).show();
                }
			}
		});

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
					if (false == _bConnected) {
						btnAction.setText("Connect");
					} else {
						btnAction.setText("Disconnect");
					}
				}
			}
		});
	}

	//
    // Send Data
    //
    void sendData(int type){
        if(_dataConnection != null){

        }
        Boolean bResult = false;
        String strMsg = "";
        switch(type){
            case 0:{
                String strData = "Hello WebRTC";
                bResult = _dataConnection.send(strData);
                strMsg = strData;
                break;
            }
            case 1:{
                Double dblData = 3.14;
                bResult = _dataConnection.send(dblData);
                strMsg = dblData.toString();
                break;
            }
            case 2: {
                ArrayList<String> arrData = new ArrayList<String>(){
                    {
                        add("1");
                        add("2");
                        add("3");
                    }
                };
                bResult = _dataConnection.send(arrData);
                strMsg = arrData.toString();
                break;
            }
            case 3:{
                HashMap<String,String> mapData = new HashMap<String,String>(){
                    {
                        put("one","1");
                        put("two","2");
                    }
                };
                bResult = _dataConnection.send(mapData);
                strMsg = mapData.toString();
                break;
            }
            case 4:{
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                try {
                    BufferedInputStream is =new BufferedInputStream(getAssets().open("image.png"));
                    while(true){
                        int len = is.read(buffer);
                        if(len < 0){
                            break;
                        }
                        bo.write(buffer,0,len);
                    }

                    byte[] btValue = bo.toByteArray();
                    ByteBuffer bbValue = ByteBuffer.wrap(btValue);
                    bResult = _dataConnection.send(bbValue);
                    strMsg = "Send Image";
                }catch(IOException e){
                    e.printStackTrace();
                }
                break;
            }
            default:{
                break;
            }
        }

        if(bResult) {
            appendLog("You:" + strMsg);
        }

    }

	//
	// Append a string to tvMessage
	//
	void appendLog(String logText){
		_tvMessage.append(logText+"\n");
	}

}
