package com.example.mfekr.newswindow.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class NewsFirebaseJobService extends JobService {

    //  COMPLETED (4) Declare an ASyncTask field called mFetchWeatherTask
    private AsyncTask<Void, Void, Void> mFetchWeatherTask;

//  COMPLETED (5) Override onStartJob and within it, spawn off a separate ASyncTask to sync weather data

    /**
     * The entry point to your Job. Implementations should offload work to another thread of
     * execution as soon as possible.
     * <p>
     * This is called by the Job Dispatcher to tell us we should start our job. Keep in mind this
     * method is run on the application's main thread, so we need to offload work to a background
     * thread.
     *
     * @return whether there is more work remaining.
     */
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        @Override
        public boolean onStartJob ( final JobParameters params){
            //Offloading work to a new thread.
            new Thread(new Runnable() {
                @Override
                public void run() {
                    completeJob(params);
                }
            }).start();
            return true;
        }

        @Override
        public boolean onStopJob (JobParameters params){
            return false;
        }

        public void completeJob ( final JobParameters parameters){
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
    }
}