/*
 * MIT License
 *
 * Copyright (c) 2016 Eric
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.EricSun.EricWidget.Framework.Network;


import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MySSLSocketFactory extends SSLSocketFactory {

   public SSLContext sslContext = SSLContext.getInstance("SSL");

    public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException,
            KeyManagementException,
            KeyStoreException,
            UnrecoverableKeyException {
//        super(truststore);
        super();

        TrustManager tm = new X509TrustManager() {

            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] arg0, String arg1)
                    throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] arg0, String arg1)
                    throws java.security.cert.CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                return null;
				return new java.security.cert.X509Certificate[0];
            }
        };

        sslContext.init(null, new TrustManager[]{tm}, null);
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    @Override
    public Socket createSocket(Socket socket,
                               String host,
                               int port,
                               boolean autoClose) throws IOException, UnknownHostException {
        injectHostname(socket, host);
        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host,
                               int port,
                               InetAddress localHost,
                               int localPort) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return sslContext.getSocketFactory().createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress address,
                               int port,
                               InetAddress localAddress,
                               int localPort) throws IOException {
        return sslContext.getSocketFactory().createSocket(address, port, localAddress, localPort);
    }

    private void injectHostname(Socket socket, String host) {
        try {
            Field field = InetAddress.class.getDeclaredField("hostName");
            field.setAccessible(true);
            field.set(socket.getInetAddress(), host);
        } catch (Exception ignored) {
        }
    }
}
