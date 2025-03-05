package util;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 作者: 86199
 * 描述: 网络请求工具类
 * 日期: 2025/2/28
 */
public class ApiClient {
    private static final String API_URL = "https://api.deepseek.com/chat/completions";
    private static final String API_KEY = "sk-deaa5c5d4db741a9bcb597b938adbec1";
    // 创建一个包含搜索消息的 JSON 字符串

    public static void search(String query, okhttp3.Callback callback) throws IOException {
        String jsonBody = "{\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"content\": \"You are a helpful assistant\",\n" +
                "      \"role\": \"system\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"content\": \"" + query + "\",\n" + // 插入搜索的消息
                "      \"role\": \"user\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"model\": \"deepseek-chat\",\n" +
                "  \"frequency_penalty\": 0,\n" +
                "  \"max_tokens\": 2048,\n" +
                "  \"presence_penalty\": 0,\n" +
                "  \"response_format\": {\n" +
                "    \"type\": \"text\"\n" +
                "  },\n" +
                "  \"stop\": null,\n" +
                "  \"stream\": false,\n" +
                "  \"stream_options\": null,\n" +
                "  \"temperature\": 1,\n" +
                "  \"top_p\": 1,\n" +
                "  \"tools\": null,\n" +
                "  \"tool_choice\": \"none\",\n" +
                "  \"logprobs\": false,\n" +
                "  \"top_logprobs\": null\n" +
                "}";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonBody);
        Request request = new Request.Builder()
                .url(API_URL)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer "+API_KEY)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
