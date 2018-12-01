package com.example.mfekr.newswindow.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class NewsRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NewsRemoteFactory(this.getApplicationContext(),intent);
    }
}
