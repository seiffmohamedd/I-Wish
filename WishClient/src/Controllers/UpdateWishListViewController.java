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
    private TableColumn<WishList, String> PriceCol;
    @FXML
    private TableColumn<WishList, String> DescriptionCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dg = new Dialog();
        updateWishListTable(User.getWishList());
        // Set up the "Remove" button column for ViewMyItemsTable
        setupRemoveButtonColumn();
        // Set up the "Add" button column for AddtoMyItemsTable
//        setupAddButtonColumn();

    }

    Dialog dg;
    SetSocket socket;
    
    
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
                wishListData.add(new WishList(ItemID, itemName));
            } catch (JSONException ex) {
                dg.showDialog("ERROR", "ERROR in data retrieval", "ERROR");
            }
        }

//        // Populate the AddtoMyItemsTable with the same data for demonstration
//        AddtoMyItemsTable.setItems(wishListData);
//        ItemstoAddCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
    }
    @FXML
    private void searchbtnAction(ActionEvent event) {
        setupAddButtonColumn();
        try {
            socket = new SetSocket();
            JSONObject request = new JSONObject();
            request.put("Command", "getItemsLike");
            request.put("userName", User.getUserName());
            request.put("item", ItemNameSearchTxt.getText());

            socket.getDOS().println(request);
            socket.getDOS().flush(); // Ensure data is sent
            
            String response = socket.getDIS().readLine();

            if (response == null || response.isEmpty() || response.equals("Fail")) {
                dg.showDialog("Data Error", "No data found", "ERROR");
                return;
            }

            JSONArray data = new JSONArray(response);
            System.out.println("Search results: " + data);

            updateAddToItemsTable(data);
            socket.closeStreams();
        } catch (JSONException | IOException ex) {
            Logger.getLogger(UpdateWishListViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateAddToItemsTable(JSONArray jsonArray) {
        ObservableList<WishList> addItemsList = FXCollections.observableArrayList();

        // Iterate through the JSON array and extract objects
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int itemID = jsonObject.getInt("itemID");
                String itemName = jsonObject.getString("itemName");
                double itemPrice = jsonObject.getDouble("itemPrice");

                String itemDescription = jsonObject.getString("itemDescription");

                // Add extracted data to the ObservableList
                addItemsList.add(new WishList(itemID, itemName, itemPrice, itemDescription));
            } catch (JSONException ex) {
                dg.showDialog("ERROR", "Error in data retrieval", "ERROR");
            }
        }

        // Set cell values for table columns
        ItemstoAddCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));

        // Populate the table
        AddtoMyItemsTable.setItems(addItemsList);
    }

    @FXML
    private void backbtnAction(ActionEvent event) {
        new LoadView(event, "ProfileView");
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
                                socket = new SetSocket();
                                WishList data = getTableView().getItems().get(getIndex());
                                System.out.println("Removed Item: " + data.getItemName());
                                System.out.println("Removed Item id : " + data.getItemid());
                                WishitemRemove wishRemove = new WishitemRemove(data.getItemid(), User.getUserName());
                                JSONObject removeItemReq = new JSONObject(wishRemove);
                                removeItemReq.put("Command", "RemoveWishListItem");
                                System.out.println("the requests sends is : " + removeItemReq);
                              
                                socket.getDOS().println(removeItemReq);
                                socket.getDOS().flush(); // Ensure data is sent
                                
                                if ("Success".equals(socket.getDIS().readLine())) {
                                   dg.showDialog("Item Remove", "Item remove Successfully", "CONFIRMATION");
                                   wishListData.remove(data); // Remove from ViewMyItemsTable
                                   AddtoMyItemsTable.getItems().add(data); // Add to AddtoMyItemsTable
                                } else {
                                    dg.showDialog("Item Remove", "Failed to Remove Item ", "ERROR");
                                }
                                socket.closeStreams();
                                
                            } catch (JSONException ex) {
                                Logger.getLogger(UpdateWishListViewController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
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
                            System.out.println("Added Item: " + data.getItemName() + " with id " + data.getItemid());
                            JSONObject addItem = new JSONObject();
                            try {
                                socket = new SetSocket();
                                addItem.put("Command", "addItemToWish");
                                addItem.put("ItemID", data.getItemid());
                                addItem.put("userName", User.getUserName());
                                addItem.put("Remaining", data.getPrice());
                                socket.getDOS().println(addItem);
                                socket.getDOS().flush(); // Ensure data is sent
                                
                                if ("Success".equals(socket.getDIS().readLine())) {
                                    dg.showDialog("Item Add", "Item Added Successfully", "CONFIRMATION");
                                       
                                    getTableView().getItems().remove(data);
                                    wishListData.add(data);
                                } else {
                                    dg.showDialog("Item Add", "Failed to Add Item to User", "ERROR");
                                }
                                socket.closeStreams();
                            } catch (JSONException ex) {
                                Logger.getLogger(UpdateWishListViewController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
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
}
