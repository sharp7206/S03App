package com.app.s03.cmn.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;

import com.app.s03.cmn.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComRequestWrapper extends HttpServletRequestWrapper {
    private byte[] rawData;
    private String bodyData;

    @SuppressFBWarnings(value = "SERVLET_CONTENT_TYPE", justification = "중요 항목 아님")
    public ComRequestWrapper(HttpServletRequest request) {
        super(request);

        // multipart/form-data 유형인 경우는 표준전문(JSON) 아님
        if (request.getContentType() != null
                && request.getContentType().contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            return;
        }

        BufferedReader reader = null;
        try {
            InputStream is = request.getInputStream();
            this.rawData = IOUtils.toByteArray(is);

            reader = this.getReader();
            this.bodyData = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException ex) {
            log.error("Error Reading the Request Body...", ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    log.error("Error Closing BufferedReader...", ex);
                }
            }
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bis = new ByteArrayInputStream(this.rawData);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bis.read();
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // Empty
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
    }

    /**
     * @return the bodyData
     */
    public String getBodyData() {
        return bodyData;
    }

}
