package eu.pixliesearth.lib.net.ranktw.DiscordWebHooks;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import eu.pixliesearth.lib.net.ranktw.DiscordWebHooks.connection.Response;
import eu.pixliesearth.lib.net.ranktw.DiscordWebHooks.connection.WebhookException;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DiscordWebhook {
    private static final Gson gson = new Gson();
    private String webhook;

    public DiscordWebhook(String webhook) {
        this.webhook = webhook;
    }

    public void sendMessage(Payload dm) {
        new Thread(() -> {
            String strResponse = HttpRequest.post(webhook)
                    .acceptJson()
                    .contentType("application/json")
                    .header("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11") // Why? Because discordapp.com blocks the default User Agent
                    .send(gson.toJson(dm))
                    .body();

            if (!strResponse.isEmpty()) {
                Response response = gson.fromJson(strResponse, Response.class);
                try {
                    if (response.getMessage().equals("You are being rate limited.")) {
                        throw new WebhookException(response.getMessage());
                    }
                } catch (Exception e) {
                    throw new WebhookException(strResponse);
                }
            }
        }).start();
    }
    public void sendMessage(File... files) {
        new Thread(() -> {
            FileInputStream fis = null;
            try {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

                // server back-end URL
                HttpPost httppost = new HttpPost(webhook);
                MultipartEntity entity = new MultipartEntity();
                // set the file input stream and file name as arguments
                for (int i = 0; i < files.length; i++) {
                    File inFile = files[i];
                    fis = new FileInputStream(inFile);
                    entity.addPart("file"+i, new InputStreamBody(fis, inFile.getName()));
                }
                entity.addPart("payload_json", new StringBody("{\"content\": \"<@&533839633537171487>\"}", Consts.UTF_8));
                httppost.setEntity(entity);
                // execute the request
                HttpResponse response = httpclient.execute(httppost);

                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity responseEntity = response.getEntity();
                String responseString = EntityUtils.toString(responseEntity, "UTF-8");
                if (statusCode==400) throw new WebhookException(responseString);
                System.out.println("[" + statusCode + "] " + responseString);
            } catch (ClientProtocolException e) {
                System.err.println("Unable to make connection");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Unable to read file");
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) fis.close();
                } catch (IOException ignored) {
                }
            }
        }).start();
    }
    public void sendMessage(Payload dm,File... files) {
        new Thread(() -> {
            FileInputStream fis = null;
            try {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

                // server back-end URL
                HttpPost httppost = new HttpPost(webhook);
                MultipartEntity entity = new MultipartEntity();
                // set the file input stream and file name as arguments
                for (int i = 0; i < files.length; i++) {
                    File inFile = files[i];
                    fis = new FileInputStream(inFile);
                    entity.addPart("file"+i, new InputStreamBody(fis, inFile.getName()));
                }
                entity.addPart("payload_json", new StringBody(gson.toJson(dm), Consts.UTF_8));
                httppost.setEntity(entity);
                // execute the request
                HttpResponse response = httpclient.execute(httppost);

                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity responseEntity = response.getEntity();
                String responseString = EntityUtils.toString(responseEntity, "UTF-8");
                if (statusCode==400) throw new WebhookException(responseString);
                System.out.println("[" + statusCode + "] " + responseString);
            } catch (ClientProtocolException e) {
                System.err.println("Unable to make connection");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Unable to read file");
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) fis.close();
                } catch (IOException ignored) {
                }
            }
        }).start();
    }
    public void sendMessageWithSlackFormatting(Payload dm) {
        new Thread(() -> {
            String strResponse = HttpRequest.post(webhook+"/slack")
                    .acceptJson()
                    .contentType("application/json")
                    .header("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11") // Why? Because discordapp.com blocks the default User Agent
                    .send(gson.toJson(dm))
                    .body();

            if (!strResponse.isEmpty()) {
                Response response = gson.fromJson(strResponse, Response.class);
                try {
                    if (response.getMessage().equals("You are being rate limited.")) {
                        throw new WebhookException(response.getMessage());
                    }
                } catch (Exception e) {
                    throw new WebhookException(strResponse);
                }
            }
        }).start();
    }
    public String sendGet() {
        return (HttpRequest.get(webhook)
                .acceptJson()
                .contentType("application/json")
                .header("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11") // Why? Because discordapp.com blocks the default User Agent
                .body());
    }
    public void sendDelete(){
        new Thread(() -> {
            String strResponse = HttpRequest.delete(webhook)
                    .acceptJson()
                    .contentType("application/json")
                    .header("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11") // Why? Because discordapp.com blocks the default User Agent
                    .body();
            if (!strResponse.isEmpty()) {
                throw new WebhookException(strResponse);
            }
        }).start();
    }
    public static String sendJsonToWebhook(String url, String json) throws MalformedURLException {
        return HttpRequest.post(new URL(url))
                .acceptJson()
                .contentType("application/json")
                .header(HttpRequest.HEADER_USER_AGENT, "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11") // Why? Because discordapp.com blocks the default User Agent
                .send(json)
                .body();
    }

}
