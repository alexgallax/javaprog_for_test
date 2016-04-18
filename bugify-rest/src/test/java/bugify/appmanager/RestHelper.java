package bugify.appmanager;

import bugify.model.Issue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.Set;

import static bugify.appmanager.consts.AppConsts.*;

public class RestHelper {

    private ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Issue> getIssues() throws IOException {
        String json = getExecutor()
                .execute(Request.Get(app.getProperty(REST_URL_PROP) + "/issues.json"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");

        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    public Issue getIssue(int issueId) throws IOException {
        String json = getExecutor()
                .execute(Request.Get(app.getProperty(REST_URL_PROP)
                        + String.format("/issues/%s.json", issueId)))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");

        Set<Issue> issueSet = new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());

        return issueSet.iterator().next();
    }

    public int createIssue(Issue issue) throws IOException {
        String json = getExecutor()
                .execute(Request.Post(app.getProperty(REST_URL_PROP + "/issues.json"))
                        .bodyForm(new BasicNameValuePair("subject", issue.getSubject()),
                                new BasicNameValuePair("description", issue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);

        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth(app.getProperty(REST_KEY_PROP), "");
    }
}
