package de.functionPlotter.ParsingEngine.Parser;

public class Parser {

    private InfixParser infixParser;
    private UPNParser upnParser;

    public Parser() {
        this.infixParser = new InfixParser();
        this.upnParser = new UPNParser();
    }
}
