import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@AllArgsConstructor
public class LinksSuggester {

    private File file;

    public List<Suggest> suggestConfiguration() throws FileNotFoundException {
        List<Suggest> suggestList = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String[] configuration = scanner.nextLine().split("\t");

            if (configuration.length != 3) {
                throw new WrongLinksFormatException("Неверное число параметров, ожидалось - 3!");
            }

            suggestList.add(new Suggest(
                            configuration[0],
                            configuration[1],
                            configuration[2]
            ));
        }

        scanner.close();

        return suggestList;
    }

    public List<Suggest> suggest(List<Suggest> suggestList, String text) {
        return suggestList.stream()
                .filter(p -> StringUtils.containsIgnoreCase(text, p.getKeyWord()))
                .distinct()
                .collect(Collectors.toList());
    }
}
