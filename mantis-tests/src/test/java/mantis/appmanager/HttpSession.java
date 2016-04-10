package mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static mantis.appmanager.AppConsts.*;

public class HttpSession {

    private CloseableHttpClient httpclient;
    private ApplicationManager app;

    public HttpSession(ApplicationManager app) {
        this.app = app;
        httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    public boolean login(String username, String password) throws IOException {
        HttpPost post = new HttpPost(app.getProperty(WEB_BASE_URL_PROP) + LOGIN_PAGE);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(USERNAME_PARAM_NAME, username));
        params.add(new BasicNameValuePair(PASSWORD_PARAM_NAME, password));
        params.add(new BasicNameValuePair(SECURE_SESSION_PARAM_NAME, SECURE_SESSION_PARAM_VALUE));
        params.add(new BasicNameValuePair(RETURN_PARAM_NAME, RETURN_PARAM_VALUE));

        post.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = httpclient.execute(post);
        String body = getTextFrom(response);

        return body.contains(String.format("<span class=\"italic\">%s</span>", username));
    }

    public boolean isLoggedInAs(String username) throws IOException {
        HttpGet get = new HttpGet(app.getProperty(WEB_BASE_URL_PROP) + INDEX_PAGE);

        CloseableHttpResponse response = httpclient.execute(get);
        String body = getTextFrom(response);

        return body.contains(String.format("<span class=\"italic\">%s</span>", username));
    }

    private String getTextFrom(CloseableHttpResponse response) throws IOException {
        try {
            return EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
    }
}
