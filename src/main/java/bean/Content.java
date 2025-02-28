package bean;

/**
 * 作者: 86199
 * 描述: 网络请求的数据类
 * 日期: 2025/2/28
 */
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("choices")
    private Choice[] choices;

    public static class Choice {
        @SerializedName("message")
        private Message message;

        public static class Message {
            @SerializedName("content")
            private String content;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    public String getContent() {
        return choices != null && choices.length > 0 ? choices[0].getMessage().getContent() : null;
    }

    public void setChoices(Choice[] choices) {
        this.choices = choices;
    }
}
