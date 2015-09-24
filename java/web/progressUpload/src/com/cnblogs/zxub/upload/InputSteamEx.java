package com.cnblogs.zxub.upload;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zxub 2006-7-14 ÏÂÎç05:28:36
 */
public class InputSteamEx extends InputStream
{

    private InputStream input = null;
    private StreamReportImpl streamReport = null;

    public InputSteamEx(InputStream input, StreamReportImpl report)
    {
        this.input = input;
        this.streamReport = report;
    }

    public int read() throws IOException
    {
        byte b[] = new byte[1];
        int readNext = -1;
        int readByte = -1;
        if (input != null) readNext = input.read(b);
        if (readNext != -1)
        {
            readByte = b[0];
            streamReport.report(1L);
        }
        else
        {
            streamReport.report(-1L);
        }
        return readByte;
    }

    public int read(byte b[], int off, int len) throws IOException
    {
        int readSize = -1;
        if (input != null) readSize = input.read(b, off, len);
        streamReport.report(readSize);
        return readSize;
    }

    public void close() throws IOException
    {
        super.close();
        if (input != null) input.close();
    }

}
