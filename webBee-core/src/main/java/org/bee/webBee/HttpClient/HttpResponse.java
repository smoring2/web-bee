package org.bee.webBee.HttpClient;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bee.webBee.linker.Request;
import org.bee.webBee.processor.Setting;
import org.bee.webBee.processor.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 封装HTTP响应
 *
 * data 2017-03-25   16:55
 * E-mail   sis.nonacosa@gmail.com
 * @author sis.nonacosa
 */
public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private Request request;

    private Task task;

    public HttpResponse(Request request, Task task){
        this.request = request;
        this.task = task;
    }
    public CloseableHttpResponse getResponse()   {
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            Setting setting = task.getSetting();

            HttpRequestBase httpMethod ;
            httpMethod = HttpClientPool.getInstance().generateHttpMethod(request,task,null);

            HttpClientBuilder httpClientBuilder = HttpClientPool.getInstance().generateClient(setting,httpMethod);
            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
             closeableHttpResponse = closeableHttpClient.execute(httpMethod);
        } catch (IOException e) {
            logger.error("Http request failed: {}", e.getMessage(), e);
        }
        return closeableHttpResponse;
    }

    public static HttpResponse getInstance(Request request, Task task){
        return new HttpResponse(request,task);
    }
}
