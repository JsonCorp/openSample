package com.example.readingnetworkstate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;

import static android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET;

/**
 * https://developer.android.com/training/basics/network-ops/reading-network-state#java
 *
 */
public class ReadingNetworkState {

    Context mContext;
    LogView mLogView;

    ReadingNetworkState(Context context, LogView logView) {
        mContext = context;
        mLogView = logView;
    }

    public void gettingInstantaneousState() {
        ConnectivityManager connectivityManager = mContext.getSystemService(ConnectivityManager.class);
        Network currentNetwork = connectivityManager.getActiveNetwork();
        NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(currentNetwork);
        LinkProperties linkProperties = connectivityManager.getLinkProperties(currentNetwork);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            mLogView.log("getTransportInfo : " + caps.getTransportInfo().toString());
//        }
//        mLogView.log("getNetworkSpecifier : " + caps.toString());
//        mLogView.log("linkProperties : " + linkProperties.toString());

        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                mLogView.log("The default network is now: " + network);
            }

            @Override
            public void onLost(Network network) {
                mLogView.log("The application no longer has a default network. The last default network was " + network);
            }

            @Override
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                mLogView.log("The default network changed capabilities: " + networkCapabilities);
            }

            @Override
            public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                mLogView.log("The default network changed link properties: " + linkProperties);
            }
        });
    }

    public void additionalNetworks() {
        NetworkRequest request = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                .addCapability(NET_CAPABILITY_INTERNET)
                .build();

        ConnectivityManager connectivityManager = mContext.getSystemService(ConnectivityManager.class);
        connectivityManager.registerNetworkCallback(request, myNetworkCallback);
    }

    ConnectivityManager.NetworkCallback myNetworkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);

            mLogView.log("myNetworkCallback onAvailable network: " + network);
        }

        @Override
        public void onLosing(@NonNull Network network, int maxMsToLive) {
            super.onLosing(network, maxMsToLive);
            mLogView.log("myNetworkCallback onLosing network: " + network);
            mLogView.log("myNetworkCallback onLosing maxMsToLive: " + maxMsToLive);
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            mLogView.log("myNetworkCallback onLost network: " + network);
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();
            mLogView.log("myNetworkCallback onUnavailable IN");
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            mLogView.log("myNetworkCallback onCapabilitiesChanged network: " + network);
            mLogView.log("myNetworkCallback onCapabilitiesChanged networkCapabilities: " + networkCapabilities);
        }

        @Override
        public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties);
            mLogView.log("myNetworkCallback onLinkPropertiesChanged network: " + network);
            mLogView.log("myNetworkCallback onLinkPropertiesChanged linkProperties: " + linkProperties);
        }

        @Override
        public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
            super.onBlockedStatusChanged(network, blocked);

            mLogView.log("myNetworkCallback onBlockedStatusChanged network: " + network);
            mLogView.log("myNetworkCallback onBlockedStatusChanged blocked: " + blocked);
        }
    };
}
