import java.util.Arrays;
import java.util.Scanner;

/**
 * Piškvorky
 */
public class Main {
    /** Pole v ktorom je uložený obsah hracej plochy **/
    public static char[][] charArea = new char[3][3];

    /** Premenná v ktorej je uložené označenie hráča **/
    public static char player;

    /** Premenná v ktorej je uložené kolo hry **/
    public static int round = 1;

    /**
     * Metóda vykreslí hraciu plochu
     */
    public static void drawArea() {
        for (int i = 0; i < charArea.length; i++) {
            for (int j = 0; j < charArea[0].length; j++) {
                System.out.print(charArea[i][j]);
                if (!(j == charArea[0].length - 1)) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (!(i == charArea.length - 1)) {
                System.out.println("-----");
            }
        }
    }

    /**
     * Metóda zisťuje či je daný znak v hracom poli prázdny,
     * ak je prázdny, tak mu priradí hodnotu hráčovho ťahu.
     * @param i - riadok v hracom poli
     * @param j - stlpec v hracom poli
     * @return - vracia true pokiaľ je hracie pole prázdne, a false ak je obsadené
     */
    public static boolean isEmpty(int i, int j) {
        if (charArea[i][j] == ' ') {
            charArea[i][j] = player;
            return true;
        } else {
            return false;
        }
    }
    // TO DO Zistiť ako by sa to dalo lepšie prepracovať...
    /**
     * Metóda priradí číslu ktoré zadal užívatel pozíciu v
     * hracom poli a vráti true pokiaľ je pozícia v poli nastavená
     * na užívateľovu hodnotu, ak nie vráti false
     * @param number - číslo, ktoré zadal používateľ
     * @return - vráti true ak sa miesto v hracom poli nastavilo na hodnotu ktorú hráč zadal
     */
    public static boolean isSetCharArea(int number) {
        return switch (number) {
            case 1 -> isEmpty(0, 0);
            case 2 -> isEmpty(0, 1);
            case 3 -> isEmpty(0, 2);
            case 4 -> isEmpty(1, 0);
            case 5 -> isEmpty(1, 1);
            case 6 -> isEmpty(1, 2);
            case 7 -> isEmpty(2, 0);
            case 8 -> isEmpty(2, 1);
            case 9 -> isEmpty(2, 2);
            default -> false;
        };
    }

    /**
     * Metóda zisťuje či nastala výhra
     * @param area - hracie pole
     * @return - vracia true v prípade výhry
     */
    public static boolean isWinning(char[][] area) {
        // Kontrola riadkov, stĺpcov a uhlopriečok pre výhru
        for (int i = 0; i < area.length; i++) {
            if (area[i][0] != ' ' && area[i][0] == area[i][1] && area[i][1] == area[i][2]) {
                return true; // Výhra v riadku
            }
            if (area[0][i] != ' ' && area[0][i] == area[1][i] && area[1][i] == area[2][i]) {
                return true; // Výhra v stĺpci
            }
        }

        if (area[0][0] != ' ' && area[0][0] == area[1][1] && area[1][1] == area[2][2]) {
            return true; // Výhra na hlavnej diagonále
        }

        if (area[0][2] != ' ' && area[0][2] == area[1][1] && area[1][1] == area[2][0]) {
            return true; // Výhra na vedľajšej diagonále
        }
        return false;
    }

    public static void main(String[] args) {
        // naplnenie poľa ' '
        for (char[] chars : charArea) {
            Arrays.fill(chars, ' ');
        }

        String userInput;
        Scanner scanner = new Scanner(System.in);

        // vykreslenie hracej plochy
        drawArea();


        while (true) {
            // nastavenie hráča
            if (round % 2 == 0) {
                player = 'O';
            } else {
                player = 'X';
            }

            System.out.println("Hráč " + player + " zadaj číslo od 1-9 alebo q pre koniec: ");

            userInput = scanner.nextLine();

            // ukončenie hry po stlačení q
            if (userInput.equals("q")) {
                break;
            }

            try {
                int number = Integer.parseInt(userInput);

                if (isSetCharArea(number)) {
                    // vykreslenie hracej plochy
                    drawArea();
                    // testovanie výhry
                    if (isWinning(charArea)) {
                        System.out.println("Hráč " + player + " vyhral. Gratulujem!!");
                        break;
                    }
                    // zisťovanie remízy
                    if (round == charArea.length * charArea[0].length) {
                        System.out.println("REMÍZA");
                        break;
                    }
                    round++;
                } else {
                    System.out.println("Táto pozícia je už obsadená alebo si zadal číslo mimo rozsahu!!!");
                }

            } catch (Exception e) {
                System.out.println("Zadaj číslo alebo q ak chceš skončiť.");
            }
        }
    }
}