import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class Console {
    private static boolean VT_ENABLED = false;
    private static String ESC = "\u001B[";

    private static void vt100() {
        if (VT_ENABLED) return;
        if (System.getProperty("os.name").startsWith("Windows")) {
            // Set output mode to handle virtual terminal sequences
            Function GetStdHandleFunc = Function.getFunction("kernel32", "GetStdHandle");
            DWORD STD_OUTPUT_HANDLE = new DWORD(-11);
            HANDLE hOut = (HANDLE) GetStdHandleFunc.invoke(HANDLE.class, new Object[]{STD_OUTPUT_HANDLE});

            DWORDByReference p_dwMode = new DWORDByReference(new DWORD(0));
            Function GetConsoleModeFunc = Function.getFunction("kernel32", "GetConsoleMode");
            GetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, p_dwMode});

            DWORD dwMode = p_dwMode.getValue();
            int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
            dwMode.setValue(dwMode.intValue() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
            Function SetConsoleModeFunc = Function.getFunction("kernel32", "SetConsoleMode");
            SetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, dwMode});
            VT_ENABLED = true;
        }
    }

    public static void cls() {
        home();
        eos();
    }

    public static void gotoXY(int x, int y) {
        vt100();
        System.out.print(ESC + y + ";" + x + "H");
    }

    public static void home() {
        vt100();
        System.out.print(ESC + "H");
    }

    public static void eos() {
        vt100();
        System.out.print(ESC + "J");
    }

    public static void eol() {
        vt100();
        System.out.print(ESC + "K");
    }

    public static void color(int color) {
        Function GetStdHandleFunc = Function.getFunction("kernel32", "GetStdHandle");
        DWORD STD_OUTPUT_HANDLE = new DWORD(-11);
        HANDLE hOut = (HANDLE) GetStdHandleFunc.invoke(HANDLE.class, new Object[]{STD_OUTPUT_HANDLE});

        DWORD COLOR = new DWORD(color);
        Function SetConsoleTextAttribute = Function.getFunction("kernel32", "SetConsoleTextAttribute");
        SetConsoleTextAttribute.invoke(BOOL.class, new Object[]{hOut, COLOR});
    }

    public static void viewColors() {
        for (int i = 0; i < 16 * 16; i++) {
            color(i);
            var output = i + " = " + getBackground(i) + " BG & " + getForeground(i) + " Text";
            System.out.println(output);
        }
    }

    private static String getForeground(int i) {
        return getColor(i % 15);
    }

    private static String getBackground(int i) {
        return getColor(i / 15);
    }

    private static String getColor(int i) {
        switch (i) {
            case 0: return "Black";
            case 1: return "Blue";
            case 2: return "Green";
            case 3: return "Aqua";
            case 4: return "Red";
            case 5: return "Purple";
            case 6: return "Yellow";
            case 7: return "White";
            case 8: return "Gray";
            case 9: return "Light Blue";
            case 10: return "Light Green";
            case 11: return "Light Aqua";
            case 12: return "Light Red";
            case 13: return "Light Purple";
            case 14: return "Light Yellow";
            case 15: return "Bright White";
            default: return "";
        }
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {

        }
    }
}