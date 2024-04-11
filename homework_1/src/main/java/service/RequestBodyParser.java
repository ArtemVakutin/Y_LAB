package service;

public class RequestBodyParser {

    public static String[] parseRequest(String requestBody) {
        String[] words = requestBody.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("\"", "");
        }
        return words;
    }
}
