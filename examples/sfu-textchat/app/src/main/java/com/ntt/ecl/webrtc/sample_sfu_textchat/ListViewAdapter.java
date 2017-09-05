package com.ntt.ecl.webrtc.sample_sfu_textchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;

public class ListViewAdapter extends ArrayAdapter<ListViewAdapter.ChatData> {
    public static class ChatData {
        public String peerId;
        public String message;
        public Date received;
    }

    final LayoutInflater inflater;

    public ListViewAdapter(Context context) {
        super(context, 0);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isEnabled(int position){
        return false;
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.view_chat, parent, false);
        }

        ChatData data = getItem(position);
        if (null != data) {
            TextView txv;

            txv = (TextView)convertView.findViewById(R.id.txvPeerId);
            if (null != txv) {
                txv.setText(data.peerId);
            }
            txv = (TextView)convertView.findViewById(R.id.txMessage);
            if (null != txv) {
                txv.setText(data.message);
            }
            txv = (TextView)convertView.findViewById(R.id.txvReceived);
            if (null != txv) {
                txv.setText(data.received.toString());
            }
        }

        return convertView;
    }
}
