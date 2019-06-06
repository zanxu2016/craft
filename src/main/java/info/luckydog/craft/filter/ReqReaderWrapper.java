package info.luckydog.craft.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * ReqReaderWrapper
 *
 * @author eric
 * @since 2019/6/6
 */
public class ReqReaderWrapper extends HttpServletRequestWrapper {

    private byte[] body;

    public ReqReaderWrapper(HttpServletRequest request) {
        super(request);
    }

    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(getBody());

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    public byte[] getBody() throws IOException {
        this.body = new ByteArrayOutputStream(8 * 1024).toByteArray();
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
