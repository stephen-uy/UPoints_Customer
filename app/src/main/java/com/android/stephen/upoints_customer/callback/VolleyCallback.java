package com.android.stephen.upoints_customer.callback;

import com.android.stephen.upoints_customer.utils.Task;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public interface VolleyCallback {
    void onResponseReady(Task task, LinkedHashMap<String, String> response);
    void onResponseReady(Task task, LinkedList<LinkedHashMap<String, String>> response);
}
