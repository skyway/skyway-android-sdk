package com.ntt.ecl.webrtc.sample_p2p_videochat;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 *
 * PeerListDialogFragment.java
 *
 */
public class PeerListDialogFragment extends DialogFragment
		implements AdapterView.OnItemClickListener {

	public interface PeerListDialogFragmentListener {
		void onItemClick(String item);
	}

	private ListView _lvList;

	private PeerListDialogFragmentListener	_listener;
	private ArrayList<String> _items;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Window window = getDialog().getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);

		Context context = inflater.getContext();
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point ptSize = new Point();
		display.getSize(ptSize);

		window.setLayout(ptSize.x * 2 / 3, ptSize.y * 2 / 3);

		View vwDialog = inflater.inflate(R.layout.fragment_dialog_peerlist, container, false);
		_lvList = (ListView)vwDialog.findViewById(R.id.listView);
		_lvList.setOnItemClickListener(this);

		return vwDialog;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, _items);
		_lvList.setAdapter(adapter);
	}

	@Override
	public void onDestroyView()	{
		_listener = null;
		_lvList = null;
		_items = null;

		super.onDestroyView();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (null != _listener)	{
			String item = _items.get(position);
			_listener.onItemClick(item);
		}

		dismiss();
	}

	public void setListener(PeerListDialogFragmentListener listener)
	{
		_listener = listener;
	}
	public void setItems(ArrayList<String> list)
	{
		_items = list;
	}
}