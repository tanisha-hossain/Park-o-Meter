public class color {
    
    // ANSI Escape Codes for Text Color
       public static final String redText = "\u001B[31m";
       public static final String blueText = "\u001B[34m";
       public static final String cyanText = "\u001B[36m";
       public static final String greenText = "\u001B[32m";
       public static final String whiteText = "\u001B[37m";
       public static final String yellowText = "\u001B[33m";
       public static final String magentaText = "\u001B[35m";
       public static final String blackText = "\u001B[30m";

   // ANSI Escape Codes for Background Color
       public static final String redBG = "\u001B[41m";
       public static final String blueBG = "\u001B[44m";
       public static final String cyanBG = "\u001B[46m";
       public static final String blackBG = "\u001B[40m";
       public static final String greenBG = "\u001B[42m";
       public static final String whiteBG = "\u001B[47m";
       public static final String yellowBG = "\u001B[43m";
       public static final String magentaBG = "\u001B[45m";
       
    // Reset Code
    public static final String RESET = "\u001B[0m";
    
    // Bold
    public static final String boldText = "\033[0;1m";
    public static final String unbold = "\033[0;0m";

    // Private constructor to prevent instantiation
    private color() {
    }
}
