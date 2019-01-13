package com.example.mfekr.newswindow.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public abstract class NewsFirebaseJobService extends JobService {


    private AsyncTask<Void, Void, Void> mFetchWeatherTask;


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                completeJob(jobParameters);
            }
        }).start();
        return true;
    }


    public void completeJob(final JobParameters parameters) {
        try {
            //This task takes 2 seconds to complete.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //Tell the framework that the job has completed and doesnot needs to be reschedule
            jobFinished(parameters, false);
        }
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}