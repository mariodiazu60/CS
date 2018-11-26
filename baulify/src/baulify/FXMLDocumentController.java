/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baulify;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 *
 * @author tonih
 */
public class FXMLDocumentController implements Initializable {
    
    public FXMLDocumentController() {}
    
    public static File selectedDirectory;
    public static List<File> selectedFiles;
    public static String clave;
    

    @FXML
    private Button elegir;
    
    @FXML
    public ListView lista;
    
    @FXML
    private Button guardar;
    
    @FXML
    private Text ruta;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private Button cifrar;
    
    @FXML
    private Button descifrar;
    
    FileChooser fc = null;
    public void ElegirAction(ActionEvent event) {
        fc = new FileChooser();
        selectedFiles = fc.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            for (int i=0; i<selectedFiles.size(); i++) {
                lista.getItems().add(selectedFiles.get(i).getAbsoluteFile());
            }
        } else {
            System.out.println("El archivo no es válido");
        }
    }
    

    
    public void EnRutarAction(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        selectedDirectory = dc.showDialog(null);
        if (selectedDirectory != null) {
            ruta.setText(selectedDirectory.getAbsolutePath());
        } else {
            System.out.println("El directorio no es válido");
        }
    }
    
    
    public void CifrarAction(ActionEvent event) {
        
        if(fc == null){         
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("¡Error!");
            alert.setHeaderText("No ha seleccionado ningún archivo");
            alert.setContentText("Elija al menos un archivo para cifrar");
            alert.showAndWait();
        }else if(selectedDirectory == null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("¡Error!");
            alert.setHeaderText("No ha seleccionado la ruta de guardado.");
            alert.setContentText("Elija la ruta donde desea guardar los archivos cifrados.");
            alert.showAndWait();
        }else{
            //Crear otra alerta con =/= nombre CLARA LO SABE
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("¡Estamos trabajando!");
            alert.setContentText("Estamos trabajando en cifrar tus archivos, ¡Un momento por favor!");
            alert.show();
            clave = password.getText();
            Controlador.cifrar(selectedFiles);
        }
    }
    
    public void DescifrarAction(ActionEvent event) throws IOException {
        if(fc == null){         
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("¡Error!");
            alert.setHeaderText("No ha seleccionado ningún archivo.");
            alert.setContentText("Elija al menos un archivo para descifrar.");
            alert.showAndWait();
        }else if(selectedDirectory == null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("¡Error!");
            alert.setHeaderText("No ha seleccionado la ruta de guardado.");
            alert.setContentText("Elija la ruta donde desea guardar los archivos descifrados.");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("¡Estamos trabajando!");
            alert.setContentText("Estamos trabajando en descifrar tus archivos, ¡Un momento por favor!");
            alert.show();
            Controlador.descifrar(selectedFiles);
         }
    }
    
    public void refrescar_lista(){
        System.out.println("Refrescamos la lista");
        lista.getItems().clear();
        lista.refresh();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
