package io.devsummit.android.Helpers;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by ganesh on 06/10/17.
 */

public class RealmHelper {
    public void receiveData(Object responseData) {
        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate((RealmModel) responseData);
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }
}
