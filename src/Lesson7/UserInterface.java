package Lesson7;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class UserInterface {

    private final Controller controller = new Controller();

    public void runApplication() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите широту города ");
            String lat1 = scanner.nextLine();
            System.out.println("Введите долготу города ");
            String lon1 = scanner.nextLine();

            setGlobalCity(lat1, lon1);

            System.out.println("Введите ответ: 1 - Получить текущую погоду, " +
                    "2 - Получить погоду на следующие 5 дней, " + "3 - Получить информацию об истории запросов, " +
                    "выход (exit) - завершить работу");
            String result = scanner.nextLine();

            checkIsExit(result);

            try {
                validateUserInput(result);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            try {
                notifyController(result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void checkIsExit(String result) {
        if (result.toLowerCase().equals("выход") || result.toLowerCase().equals("exit")) {
            System.out.println("Завершаю работу");
            System.exit(0);
        }
    }

    private void setGlobalCity(String lat, String lon) {
        ApplicationGlobalState.getInstance().setSelectedLat(lat);
        ApplicationGlobalState.getInstance().setSelectedLon(lon);
    }


    private void validateUserInput(String userInput) throws IOException {
        if (userInput == null || userInput.length() != 1) {
            throw new IOException("Incorrect user input: expected one digit as answer, but actually get " + userInput);
        }
        int answer = 0;
        try {
            answer = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            throw new IOException("Incorrect user input: character is not numeric!");
        }
    }

    private void notifyController(String input) throws IOException, SQLException {
        controller.onUserInput(input);
    }

}