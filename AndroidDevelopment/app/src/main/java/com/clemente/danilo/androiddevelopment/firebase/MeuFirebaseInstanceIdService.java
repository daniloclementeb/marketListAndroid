package com.clemente.danilo.androiddevelopment.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by logonrm on 02/09/2017.
 */

public class MeuFirebaseInstanceIdService  extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshdToken) {
        Log.d(TAG, "Refreshed token: " + refreshdToken);
    }

}
