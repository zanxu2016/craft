package info.luckydog.craft.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * ResReaderWrapper
 *
 * @author eric
 * @since 2019/6/6
 */
public class ResReaderWrapper extends HttpServletResponseWrapper {

    private ServletOutputStream outputStream = null;
    private PrintWriter writer = null;
    private CustomServletOutputStream cout = null;
    private byte[] copy;

    public ResReaderWrapper(HttpServletResponse response) {
        super(response);
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (outputStream == null) {
            outputStream = super.getResponse().getOutputStream();
            cout = new CustomServletOutputStream(outputStream);
        }

        return cout;
    }

    public PrintWriter getWriter() throws IOException {

        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            cout = new CustomServletOutputStream(getResponse().getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(cout, getResponse().getCharacterEncoding()), true);
        }

        return writer;
    }

    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
            cout.flush();
        }
    }

    public byte[] getCopy() {
        if (cout != null) {
            return cout.getCopy();
        } else {
            return new byte[0];
        }
    }

    public void setCopy(byte[] copy) {
        this.copy = copy;
    }

    class CustomServletOutputStream extends ServletOutputStream {

        private ByteArrayOutputStream byteArrayOutputStream;

        private OutputStream outputStream;

        private byte[] copy;

        CustomServletOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
            this.byteArrayOutputStream = new ByteArrayOutputStream();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            byteArrayOutputStream.write(b);
        }

        public byte[] getCopy() {
            this.copy = byteArrayOutputStream.toByteArray();
            return copy;
        }

        public void setCopy(byte[] copy) {
            this.copy = copy;
        }
    }
}
