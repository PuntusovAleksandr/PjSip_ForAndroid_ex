package me.boger.pjsua2.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bogerchan on 2016/1/28.
 */
public class StorageUtils {

    public static Executor createStorager(Context context) {
        return new Executor(context);
    }

    public static String getString(Context context, String key) {
        return context.getSharedPreferences(context.getPackageName(), 0).getString(key, null);
    }

    public static class Executor {
        private SharedPreferences.Editor editor;

        @SuppressLint("CommitPrefEdits")
        private Executor(Context context) {
            this.editor = context.getSharedPreferences(context.getPackageName(), 0).edit();
        }

        public Executor putString(String key, String value) {
            editor.putString(key, value);
            return this;
        }

        public void commit() {
            editor.commit();
        }
    }
}
