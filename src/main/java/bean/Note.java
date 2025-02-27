package bean;
/**
 * 作者: 86199
 * 描述: note数据类
 * 日期: 2025/2/27
 */
public  class Note {
    int id;
    String title;
    String content;
    String crateTime;

    public Note() {
    }

    public Note(int id, String title, String content, String crateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.crateTime = crateTime;
    }
    public Note( String title, String content, String crateTime) {
        this.title = title;
        this.content = content;
        this.crateTime = crateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(String crateTime) {
        this.crateTime = crateTime;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", crateTime='" + crateTime + '\'' +
                '}';
    }
}
