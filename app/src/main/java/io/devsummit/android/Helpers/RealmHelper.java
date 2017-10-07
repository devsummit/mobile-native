package io.devsummit.android.Helpers;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmModel;
/**
 * Created by wlisrausr on 06/10/17.
 */

public class RealmHelper {
    public void receiveData(Object responseData) {
        Class cls = responseData.getClass();
        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();
            Object newData = cls.newInstance();
            Method[] methods = newData.getClass().getMethods();
            ArrayList<Method> setters = new ArrayList<>();
            for (int i = 0; i < methods.length; i++) {
                if(methods[i].getName().startsWith("set")){
                    setters.add(methods[i]);
                }
            }

            for (int i = 0; i < setters.size(); i++) {
                Method method = setters.get(i);
                String fieldName = getFieldName(method);
                fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                Field f = newData.getClass().getDeclaredField(fieldName);
                f.setAccessible(true);//Very important, this allows the setting to work.
                method.invoke(newData, getPrivateStatic(newData.getClass(), fieldName, responseData));
            }
            realm.copyToRealmOrUpdate((RealmModel) newData);
            realm.commitTransaction();
            realm.close();
            } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public String getFieldName(Method method) {
        return method.getName().substring(3);
    }

    private static Object getPrivateStatic(Class clazz, String f, Object response) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);

        return field.get(response);
    }

}