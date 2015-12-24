package com.muzammilpeer.ffmpeglayer.helpers;

import com.ranisaurus.utilitylayer.logger.Log4a;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by muzammilpeer on 11/2/15.
 */
public class StreamUtil {
    static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log4a.printException(e);
            }
        }
    }

    static void close(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                Log4a.printException(e);
            }
        }
    }
}
