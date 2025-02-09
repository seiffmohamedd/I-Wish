package Controllers;

import BDO.Items;
import BDO.User;
import BDO.WishList;
import BDO.WishitemRemove;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wishclient.SetSocket;

/**
 * FXML Controller class
 *
 * @author Windo
 */
public class UpdateWishListViewController implements Initializable {

    @FXML
    private TableView<WishList> ViewMyItemsTable;
    @FXML
    private TableColumn<WishList, String> ItemsInMyWishCol;
    @FXML
    private TableColumn<WishList, Void> RemoveCol; // Updated to use Void for button column
    private ObservableList<WishList> wishListData = FXCollections.observableArrayList();
    @FXML
    private TextField ItemNameSearchTxt;
    @FXML
    private TableView<WishList> AddtoMyItemsTable;
    @FXML
    private TableColumn<WishList, String> ItemstoAddCol;
    @FXML
    private TableColumn<WishList, Void> AddCol; // Updated to use Void for button column
    @FXML
    private TableColumn<?, ?> PriceCol;
    @FXML
    private TableColumn<?, ?> DescriptionCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dg = new Dialog();
            updateWishListTable(User.getWishList());
            
            // Set up the "Remove" button column for ViewMyItemsTable
            setupRemoveButtonColumn();
            
            // Set up the "Add" button column for AddtoMyItemsTable
            setupAddButtonColumn();
            
            socket = new SetSocket();
        } catch (IOException ex) {
            dg.showDialog("Socket error", "Can not open socet", "ERROR");
        }
        
        
    }   
    
    Dialog dg;
    SetSocket socket;
    @FXML
    private void searchbtnAction(ActionEvent event) {
     
        try {
            SetSocket s = new SetSocket();
            JSONObject request = new JSONObject();
            request.put("Command", "getItemsLike");
            request.put("userName",User.getUserName());
            request.put("item",ItemNameSearchTxt.getText());
            s.getDOS().println(request);
            String data = s.getDIS().readLine();
            if("Fail".equals(data)){
                dg.showDialog("DataError", "No data Found", "ERROR");
            }else{
                System.out.println("the output for search is "+data);
                }
        } catch (JSONException ex) {
            Logger.getLogger(UpdateWishListViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpdateWishListViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }

    @FXML
    private void backbtnAction(ActionEvent event) {
        new LoadView(event, "ProfileView");
    }
    
    public void updateWishListTable(JSONArray jsonArray) {
        ItemsInMyWishCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        ViewMyItemsTable.setItems(wishListData);
        wishListData.clear();  // Clear existing data

        // Iterate through JSONArray and extract objects
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                
                String itemName = jsonObject.getString("itemName");
                int ItemID = jsonObject.getInt("ItemID");
                wishListData.add(new WishList(ItemID,itemName));
            } catch (JSONException ex) {
                dg.showDialog("ERROR", "ERROR in data retrieval", "ERROR");
            }
        }

//        // Populate the AddtoMyItemsTable with the same data for demonstration
//        AddtoMyItemsTable.setItems(wishListData);
//        ItemstoAddCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
    }

    private void setupRemoveButtonColumn() {
        RemoveCol.setCellFactory(new Callback<TableColumn<WishList, Void>, TableCell<WishList, Void>>() {
            @Override
            public TableCell<WishList, Void> call(final TableColumn<WishList, Void> param) {
                return new TableCell<WishList, Void>() {
                    private final Button btn = new Button("Remove");

                    {
                        btn.setOnAction(event -> {
                            try {
                                WishList data = getTableView().getItems().get(getIndex());
                                System.out.println("Removed Item: " + data.getItemName());
                                System.out.println("Removed Item id : " + data.getItemid());
                                WishitemRemove wishRemove = new WishitemRemove(data.getItemid(),User.getUserName());
                                JSONObject removeItemReq = new JSONObject(wishRemove);
                                removeItemReq.put("Command", "RemoveWishListItem");
                                System.out.println("the requests sends is : "+ removeItemReq);
                                wishListData.remove(data);  // Remove the item from the table
                                socket.getDOS().println(removeItemReq);
                                try {
                                    if("Success".equals(socket.getDIS().readLine()))
                                        dg.showDialog("Item Remove", "Item remove Successfully", "CONFIRMATION");
                                    else
                                        dg.showDialog("Item Remove", "Failed to Remove Item ", "ERROR");
                                } catch (IOException ex) {
                                    Logger.getLogger(UpdateWishListViewController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (JSONException ex) {
                                Logger.getLogger(UpdateWishListViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
    }

    private void setupAddButtonColumn() {
        AddCol.setCellFactory(new Callback<TableColumn<WishList, Void>, TableCell<WishList, Void>>() {
            @Override
            public TableCell<WishList, Void> call(final TableColumn<WishList, Void> param) {
                return new TableCell<WishList, Void>() {
                    private final Button btn = new Button("Add");

                    {
                        btn.setOnAction(event -> {
                            WishList data = getTableView().getItems().get(getIndex());
                            System.out.println("Added Item: " + data.getItemName());
                            // Perform your action here, e.g., add the item to a cart
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
    }
}