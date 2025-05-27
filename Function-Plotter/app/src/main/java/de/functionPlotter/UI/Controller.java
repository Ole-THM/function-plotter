package de.functionPlotter.UI;

import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;
import de.functionPlotter.ParsingEngine.Parser.Parser;
import de.functionPlotter.Plot.PlotUtils.ColoredNode;
import de.functionPlotter.Plot.PlotUtils.RGB;
import de.functionPlotter.Plot.PlotUtils.XYRange;
import de.functionPlotter.Plot.Plotter;
import de.functionPlotter.Utils.GlobalContext;
import de.functionPlotter.Utils.Variable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;

import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private int numberOfExpressions = 1;
    private int numberOfVariables = 1;

    private final Color[] colors = {
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE,
            Color.PURPLE, Color.CYAN, Color.MAGENTA, Color.BROWN, Color.GRAY,
            Color.PINK, Color.LIGHTBLUE, Color.LIGHTGREEN, Color.LIGHTYELLOW,
            Color.AQUA
    };

    @FXML
    private AnchorPane functionAnchorPane;

    @FXML
    private AnchorPane variableAnchorPane;

    @FXML
    private TextField fTextField;

    @FXML
    private TextField aTextField;

    // Range TextFields

    @FXML
    private TextField xMinTextField;

    @FXML
    private TextField xMaxTextField;

    @FXML
    private TextField yMinTextField;

    @FXML
    private TextField yMaxTextField;

    @FXML
    private ColorPicker fColorPicker;

    @FXML
    private Button plotButton;

    @FXML
    private Button addExpressionTextFieldButton;

    @FXML
    private Button saveVariablesButton;

    @FXML
    private Button addVariableTextFieldButton;

    @FXML
    private WebView webView;

    @FXML
    private void plotButtonHandler() throws ParseException {
        System.out.println("You clicked me!");
        System.out.println("f: " + fTextField.getText());
        GlobalContext.xyRange = this.getRangeValuesOrDefault();
        Plotter.plot(this.getNodesAndColors());
//        webView.getEngine().load(Objects.requireNonNull(getClass().getResource("/output.svg")).toExternalForm()); // load save svg file
        webView.getEngine().loadContent(GlobalContext.outputString.toString());
    }

    @FXML
    private void addExpressionTextFieldButtonHandler() {
        if (this.numberOfExpressions < 10) { // Limit to 10 expressions
            this.numberOfExpressions++;
        } else {
            System.out.println("Maximum number of expressions reached.");
            return;
        }

        String newIdentifier = "" + (char) ('e' + this.numberOfExpressions);

        Text t = new Text(newIdentifier + "(x)");
        t.setLayoutX(3.0);
        t.setLayoutY(this.numberOfExpressions * 40.0 + 15.0);
        t.setTextAlignment(TextAlignment.CENTER);
        t.setId(newIdentifier + "Text");

        TextField tf = new TextField();
        tf.setLayoutX(25.0);
        tf.setLayoutY(this.numberOfExpressions * 40.0);
        tf.setId(newIdentifier + "TextField");

        ColorPicker cp = new ColorPicker(this.colors[this.numberOfExpressions % this.colors.length]); // modulo not necessary because the current implementation limits the number of expressions to 10
        cp.setLayoutX(190.0);
        cp.setLayoutY(this.numberOfExpressions * 40.0);
        cp.setPrefHeight(26.0);
        cp.setPrefWidth(28.0);
        cp.setId(newIdentifier + "ColorPicker");

        tf.setPromptText("Enter function");
        functionAnchorPane.getChildren().addAll(t, tf, cp);
        this.plotButton.setLayoutY((this.numberOfExpressions + 2) * 40.0);
        this.addExpressionTextFieldButton.setLayoutY((this.numberOfExpressions + 1) * 40.0);
        tf.requestFocus();
    }

    private void saveVariablesButtonHandler() {
        for (Variable variable : this.getVariables()) {
            GlobalContext.VARIABLES.add(variable);
        }
        System.out.println("Variables saved: " + GlobalContext.VARIABLES);
    }

    private void addVariableTextFieldButtonHandler() {
        if (this.numberOfVariables < 15) { // Limit to 15 variables
            this.numberOfVariables++;
        } else {
            System.out.println("Maximum number of variables reached.");
            return;
        }

        String newIdentifier = "" + (char) ('a' + this.numberOfVariables - 1);

        Text t = new Text(newIdentifier);
        t.setTextAlignment(TextAlignment.CENTER);
        t.setLayoutX(3.0);
        t.setLayoutY(this.numberOfVariables * 40.0 + 15.0);
        t.setId(newIdentifier + "Text");

        TextField tf = new TextField();
        tf.setPromptText("Your Expression here");
        tf.setLayoutX(25.0);
        tf.setLayoutY(this.numberOfVariables * 40.0);
        tf.setId(newIdentifier + "TextField");

        variableAnchorPane.getChildren().addAll(t, tf);
        this.saveVariablesButton.setLayoutY((this.numberOfVariables + 2) * 40.0);
        this.addVariableTextFieldButton.setLayoutY((this.numberOfVariables + 1) * 40.0);
        tf.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        plotButton.setOnAction(event -> {
            try {
                plotButtonHandler();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        addExpressionTextFieldButton.setOnAction(event -> {
            try {
                addExpressionTextFieldButtonHandler();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        saveVariablesButton.setOnAction(event -> {
            try {
                saveVariablesButtonHandler();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        addVariableTextFieldButton.setOnAction(event -> {
            try {
                addVariableTextFieldButtonHandler();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private RGB getRGBFromColor(Color color) {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);
        return new RGB(red, green, blue);
    }

    private Variable[] getVariables() {
        ArrayList<Variable> variables = new ArrayList<>();
        for (int i = 0; i < this.numberOfVariables; i++) {
            TextField textField = (TextField) variableAnchorPane.lookup("#" + (char) ('a' + i) + "TextField");
            if (textField != null) {
                String expression = textField.getText();
                if (!expression.isEmpty()) {
                    try {
                        ASTNodeI ast = Parser.parse(expression);
                        variables.add(new Variable("" + (char) ('a' + i), ast));
                    } catch (ParseException e) {
                        System.err.println("Error parsing variable expression: " + expression);
                    }
                }
            } else {
                System.err.println("TextField für Variable " + (char) ('a' + i) + "TextField" + " nicht gefunden.");
            }
        }
        return variables.toArray(new Variable[0]);
    }

    private ColoredNode[] getNodesAndColors() {
        ArrayList<ColoredNode> coloredNodes = new ArrayList<>();
        for (int i = 0; i < this.numberOfExpressions; i++) {
            TextField textField = (TextField) functionAnchorPane.lookup("#" + (char) ('f' + i) + "TextField");
            ColorPicker colorPicker = (ColorPicker) functionAnchorPane.lookup("#" + (char) ('f' + i) + "ColorPicker");
            if (textField != null && colorPicker != null) {
                String expression = textField.getText();
                if (!expression.isEmpty()) {
                    try {
                        ASTNodeI ast = Parser.parse(expression);
                        coloredNodes.add(new ColoredNode(
                                ast,
                                this.getRGBFromColor(colorPicker.getValue())
                        ));
                    } catch (ParseException e) {
                        System.err.println("Error parsing expression: " + expression);
                    }
                }
            } else {
                System.err.println("TextField für Ausdruck " + (char) ('f' + i) + "TextField" + " nicht gefunden.");
            }
        }
        return coloredNodes.toArray(new ColoredNode[0]);
    }

    private XYRange getRangeValuesOrDefault() {
        double xMin;
        try {
            xMin = Double.parseDouble(xMinTextField.getText());
        } catch (NumberFormatException e) {
            xMin = 10.0; // Default value if parsing fails
        }
        double xMax;
        try {
            xMax = Double.parseDouble(xMaxTextField.getText());
        } catch (NumberFormatException e) {
            xMax = 10.0; // Default value if parsing fails
        }
        double yMin;
        try {
            yMin = Double.parseDouble(yMinTextField.getText());
        } catch (NumberFormatException e) {
            yMin = 10.0; // Default value if parsing fails
        }
        double yMax;
        try {
            yMax = Double.parseDouble(yMaxTextField.getText());
        } catch (NumberFormatException e) {
            yMax = 10.0; // Default value if parsing fails
        }

        return new XYRange(xMin, xMax, yMin, yMax);
    }
}

