package Controllers;

import Controllers.LoadView;
import DAL.DBConnection;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.event.ActionEvent;

public class ExcuteQueryController {

    @FXML
    private TextArea queryField;

    @FXML
    private TableView<ObservableList<String>> resultTable;


    private final DBConnection dbConnection = new DBConnection(); 
    private final Dialog dialog = new Dialog(); 

    @FXML
   
private void executeQuery() {
    String query = queryField.getText().trim();

    if (query.isEmpty()) {
        dialog.showDialog("Warning", "Query field is empty!", "WARNING");
        return;
    }

    try (Connection conn = dbConnection.getConection();
         Statement stmt = conn.createStatement()) {

        String lowerQuery = query.toLowerCase();

        
        if (lowerQuery.startsWith("select")) {  
            ResultSet rs = stmt.executeQuery(query);

            resultTable.getColumns().clear();
            resultTable.getItems().clear();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                final int colIndex = i - 1;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(metaData.getColumnName(i));
                column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(colIndex))
                );
                resultTable.getColumns().add(column);
            }

            ObservableList<ObservableList<String>> data = observableArrayList();
            while (rs.next()) {
                ObservableList<String> row = observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            resultTable.setItems(data);
            dialog.showDialog("Success", "Query executed successfully!", "INFORMATION");

   
        } else if (lowerQuery.startsWith("insert") || lowerQuery.startsWith("update") || lowerQuery.startsWith("delete")) {  
            int affectedRows = stmt.executeUpdate(query);
            dialog.showDialog("Success", "Query executed successfully! Rows affected: " + affectedRows, "INFORMATION");

        } else {
           
            dialog.showDialog("Error", "Only SELECT, INSERT, UPDATE, and DELETE queries are allowed!", "ERROR");
        }

    } catch (SQLException e) {
        dialog.showDialog("Database Error", e.getMessage(), "ERROR");
    }
}

    @FXML
    private void backbutnAction(ActionEvent event) {
        try {
            new LoadView(event, "AdminView");
        } catch (IOException ex) {
            Logger.getLogger(ExcuteQueryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
