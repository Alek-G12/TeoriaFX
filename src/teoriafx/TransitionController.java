/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Alek_G12
 */
public class TransitionController implements Initializable {

    @FXML
    VBox cellContainer;
    @FXML
    Slider matrixSize;
    @FXML
    Button calcular;

    TextField[][] dataArray;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        matrixSize.setOnMouseClicked((MouseEvent) -> {
            displayCells();
        });
        matrixSize.setOnKeyReleased((KeyEvent) -> {
            displayCells();
        });
        displayCells();
    }

    private void displayCells() {
        cellContainer.getChildren().clear();
        int size = (int) matrixSize.getValue();
        dataArray = new TextField[size][size + 1];
        for (int i = 0; i < size; i++) {
            HBox hBox = new HBox(10);
            for (int j = 0; j < size + 1; j++) {
                dataArray[i][j] = new TextField();
                hBox.getChildren().add(dataArray[i][j]);
            }
            cellContainer.getChildren().add(hBox);
        }
    }

    @FXML
    private void calcular() {
        if (validateInputs()) {

        }
    }

    private boolean validateInputs() {
        for (TextField[] textFields : dataArray) {
            //Every row
            for (TextField textField : textFields) {
                //Every column
                try {
                    //Try parsing number
                    Double d = Double.parseDouble(textField.getText());
                    if (d < 0 || d >= 1) {
                        //Number outta range
                        TeoriaFX.showAlert("Valor:" + d + " fuera de rango. Revise los valores de la matriz");
                        return false;
                    } else {
                        //Number in range
                    }
                } catch (NumberFormatException e) {
                    //NotANumber
                    TeoriaFX.showAlert("Valores con formato incorrecto. Revise los valores de la matriz");
                    return false;
                }
            }
        }
        return true;
    }
}
