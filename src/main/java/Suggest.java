import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class Suggest {

    private String keyWord;
    private String title;
    private String url;

    public String getKeyWord() {
        return keyWord;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Suggest{" +
                "keyWord='" + keyWord + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Suggest)) return false;
        Suggest suggest = (Suggest) o;
        return Objects.equals(
                    getKeyWord(),
                    suggest.getKeyWord()) && Objects.equals(getTitle(),
                    suggest.getTitle()) && Objects.equals(getUrl(),
                    suggest.getUrl()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                    getKeyWord(),
                    getTitle(),
                    getUrl()
        );
    }
}
