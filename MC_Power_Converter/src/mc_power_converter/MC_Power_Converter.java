// Translated from MC_Power_Convert.py @Author_Tyler Bauer
package mc_power_converter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author cmoon
 */
public class MC_Power_Converter extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MC_Power_Converter.fxml"));
        Parent root = loader.load();
        final MC_Power_ConverterController controller = loader.getController();
        
        Scene scene = new Scene(root);
        
        stage.setOnCloseRequest(e -> controller.shutdown());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
