package com.muzammilpeer.ffmpeglayer.manager;


import android.os.AsyncTask;

import com.muzammilpeer.ffmpeglayer.imanager.IBufferStream;
import com.ranisaurus.utilitylayer.logger.Log4a;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by muzammilpeer on 11/2/15.
 */
public class ShellManager {

    private InputStreamAsync inputStreamAsync;
    private ErrorStreamAsync errorStreamAsync;

    private Process mProcess;
    private int pID;
    private BufferedReader mReaderBuffer;
    private BufferedReader mErrorBuffer;
    private BufferedWriter mWriterBuffer;

    private boolean isTaskRunning = false;
    private boolean isLogBuffer = true;

    private static ShellManager ourInstance = new ShellManager();

    public static ShellManager getInstance() {
        return ourInstance;
    }

    private ShellManager() {
    }


    public void execute(String command) {
        executeCallback(command,null);
    }


    public void executeCallback(String command, IBufferStream delegate) {

        try {
            mProcess = Runtime.getRuntime().exec(command);

            if (delegate != null)
            {
                delegate.onStreamWorking("Process Initialization => " + "executeCallback()");
            }else if (isLogBuffer) {
                Log4a.d("Process Initialization => ", "executeCallback()");
            }

            isTaskRunning = true;
            setupOutputStream(delegate);

            inputStreamAsync = new InputStreamAsync();
            inputStreamAsync.execute("");

            errorStreamAsync = new ErrorStreamAsync();
            errorStreamAsync.execute("");

        } catch (IOException e) {
            if (delegate != null)
            {
                delegate.onStreamWorking("Process Initialization Failed => " + "executeCallback()");
            }else if (isLogBuffer) {
                Log4a.d("Process Initialization Failed => ", "executeCallback()");
            }

            isTaskRunning = false;
            Log4a.printException(e);
        }
    }


    //send 'q' to ffmpeg to quit normally
    public void writeToShell(String input)
    {
        if (mProcess != null && mWriterBuffer != null)
        {
            try {
                mWriterBuffer.write(input);
                mWriterBuffer.close();
               //close other buffers after testing
            }catch (IOException e)
            {
                isTaskRunning = false;
                Log4a.printException(e);
            }
        }
    }

    private void setupOutputStream(IBufferStream delegate)
    {
        if (mProcess != null)
        {
            mWriterBuffer = new BufferedWriter(
                    new OutputStreamWriter(mProcess.getOutputStream()));
            if (delegate != null)
            {
                delegate.onStreamWorking("Output Stream => " + "setupOutputStream()");
            }else if (isLogBuffer) {
                Log4a.d("Output Stream => ", "setupOutputStream()");
            }
        }
    }


    private void setupInputStream(IBufferStream delegate)
    {
        if (mProcess != null)
        {
            try {
                //reading input from stream in infinite loop
                mReaderBuffer = new BufferedReader(new InputStreamReader(
                        mProcess.getInputStream()));
                if (delegate != null)
                {
                    delegate.onStreamWorking("Input Stream => " + "setupInputStream()");
                }else if (isLogBuffer) {
                    Log4a.d("Input Stream => ", "setupInputStream()");
                }

                String line;
                while ((line = mReaderBuffer.readLine()) != null) {
                    if (delegate != null)
                    {
                        delegate.onStreamWorking("Input Stream => " + line);
                    }else if (isLogBuffer) {
                        Log4a.d("Input Stream => ", line);
                    }
                }
                mReaderBuffer.close();
            }catch (IOException e)
            {
                Log4a.printException(e);
            }

            //ending the input stream infinite loop
            if (delegate != null)
            {
                delegate.onStreamCompleted("Input Stream => Shutting Down");
            }else if (isLogBuffer) {
                Log4a.d("Input Stream => ", "Shutting Down");
            }
            isTaskRunning = false;
        }
    }


    private void setupErrorStream(IBufferStream delegate)
    {
        if (mProcess != null)
        {
            try {
                //reading input from stream in infinite loop
                mErrorBuffer = new BufferedReader(new InputStreamReader(
                        mProcess.getErrorStream()));

                if (delegate != null)
                {
                    delegate.onStreamWorking("Error Stream => " + "setupErrorStream()");
                }else if (isLogBuffer) {
                    Log4a.d("Error Stream => ", "setupErrorStream()");
                }

                String line;
                while ((line = mErrorBuffer.readLine()) != null) {
                    if (delegate != null)
                    {
                        delegate.onStreamWorking("Error Stream => " + line);
                    }else if (isLogBuffer) {
                        Log4a.d("Error Stream => ", line);
                    }
                }
                mErrorBuffer.close();
            }catch (IOException e)
            {
                Log4a.printException(e);
            }

            //ending the input stream infinite loop
            if (delegate != null)
            {
                delegate.onStreamCompleted("Error Stream => Shutting Down");
            }else if (isLogBuffer) {
                Log4a.d("Error Stream => ", "Shutting Down");
            }
        }
    }

    public int getpID() {
        return pID;
    }

    public boolean isTaskRunning() {
        return isTaskRunning;
    }

    public boolean isLogBuffer() {
        return isLogBuffer;
    }

    public void setLoggingEnable(boolean isLogBuffer) {
        this.isLogBuffer = isLogBuffer;
    }


    class InputStreamAsync extends AsyncTask<String,Void,String>
    {
        IBufferStream mDelegate = null;

        @Override
        protected String doInBackground(String... params) {
            setupInputStream(mDelegate);
            return null;
        }

    }

    class ErrorStreamAsync extends AsyncTask<String,Void,String>
    {
        IBufferStream mDelegate = null;

        @Override
        protected String doInBackground(String... params) {
            setupErrorStream(mDelegate);
            return null;
        }

    }
}
