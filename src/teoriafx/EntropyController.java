/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Alek_G12
 */
public class EntropyController implements Initializable {

    @FXML
    TextField entropy;
    @FXML
    TextField mutual;
    @FXML
    TextField newFrecuency;
    @FXML
    Button addFrecuncy;
    @FXML
    Button calculate;
    @FXML
    ListView frecuencyList;
    @FXML
    ChoiceBox type;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        type.setItems(FXCollections.observableArrayList("Hartleys/simbolo", "Bits/simbolo", "Nats/simbolo"));
        type.getSelectionModel().selectFirst();
        frecuencyList.setOnKeyReleased((KeyEvent event) -> {
            if (event.getCode() == KeyCode.DELETE) {
                frecuencyList.getItems().remove(frecuencyList.getSelectionModel().getSelectedIndex());
            }
        });
    }

    @FXML
    private void addFrecuency() {
        String frecuency = newFrecuency.getText();
        if (frecuency.isEmpty()) {
            TeoriaFX.showAlert("Field Can't be empty");
        } else {
            try {
                double d = Double.parseDouble(frecuency);
                if (d <= 0 || d >= 1) {
                    TeoriaFX.showAlert("Frecuency not in range. It should be between 0 and 1");
                } else {
                    calculate.setDisable(false);
                    frecuencyList.getItems().add(frecuency);
                    newFrecuency.setText("");
                    newFrecuency.requestFocus();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
                TeoriaFX.showAlert("Not a valid number");
            }
        }
    }

    @FXML
    private void calculate() {
        if (!frecuencyList.getItems().isEmpty()) {
            double totalFrecuency = 0;
            ObservableList<String> list = frecuencyList.getItems();
            ArrayList<Double> doubles = new ArrayList<>();
            for (String string : list) {
                double d = Double.parseDouble(string);
                doubles.add(d);
                totalFrecuency += d;
            }
            if (totalFrecuency == 1) {
                double entropyValue = 0, mutualValue = 0;
                switch (type.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        for (double d : doubles) {
                            mutualValue += -Math.log10(d);
                            entropyValue += -Math.log10(d) * d;
                        }
                        break;
                    case 1:
                        for (double d : doubles) {
                            mutualValue += -Math.log10(d) / Math.log10(2);
                            entropyValue += -Math.log10(d) / Math.log10(2) * d;
                        }
                        break;
                    case 2:
                        for (double d : doubles) {
                            mutualValue += -Math.log(d);
                            entropyValue += -Math.log(d) * d;
                        }
                        break;
                }
                entropy.setText(String.format("%.4f " + type.getSelectionModel().getSelectedItem().toString(), entropyValue));
                mutual.setText(String.format("%.4f", mutualValue));
            } else {
                TeoriaFX.showAlert("The sum of all frecuencies must be 1. Please check the list and try again");
            }
        } else {
            TeoriaFX.showAlert("List can't be empty. Add entries");
        }
    }
}
