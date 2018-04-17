package com.overlord.softwarelabexamportal.utils_immutable;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.function.Consumer;

public class Shorter {
    public static TextWatcher textWatcher(Consumer<CharSequence> inputConsumer) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputConsumer.accept(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    public static OnCompleteListener<Void> onCompleteListener(Consumer<Task> taskConsumer) {
        return new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    taskConsumer.accept(task);
                }
                else {
                    Exception exception = task.getException();
                    Log.e("onComplete", exception == null ? "Unknown" : exception.getMessage());
                }
            }
        };
    }
}
