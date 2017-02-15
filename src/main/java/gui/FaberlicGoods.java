package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;

import com.dao.FabDao;
import com.faberlic.Goods;

public class FaberlicGoods extends Application{

	FabDao fabDao;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// This is for assigning value into table
		fabDao = new FabDao();
		
		
		Label label = new Label("Enter article");
		TextField textField = new TextField();

		Button btn = new Button();
		btn.setText("Search");


		HBox hBox = new HBox();
		hBox.setPadding(new Insets(10, 10, 10, 10));
		hBox.setSpacing(10);
		hBox.setAlignment(Pos.TOP_CENTER);
		hBox.getChildren().addAll(label, textField, btn);


		BorderPane root = new BorderPane();
		root.setTop(hBox);

		TableView<Goods> tableView = new TableView<Goods>(); //table.setItems(teamMembers);
		

		TableColumn<Goods, String> discountTC = new TableColumn<Goods, String>("discount");
		discountTC.setCellValueFactory(new PropertyValueFactory<>("discount"));
		discountTC.setMinWidth(180);
		
		TableColumn<Goods, String>  pageTC = new TableColumn<Goods, String> ("page");
		pageTC.setCellValueFactory(new PropertyValueFactory<>("page"));
		pageTC.setMinWidth(80);
		
		TableColumn<Goods, String>  articleTC = new TableColumn<Goods, String> ("article");
		articleTC.setCellValueFactory(new PropertyValueFactory<>("article"));
		articleTC.setMinWidth(180);
		
		TableColumn<Goods, String>  nameTC = new TableColumn<Goods, String> ("name");
		nameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameTC.setMinWidth(300);

		TableColumn<Goods, String> priceCatalogCombined = new TableColumn<Goods, String>("price catalog");
		

		TableColumn<Goods, BigDecimal> priceCatalogTC = new TableColumn<Goods, BigDecimal>("was");
		priceCatalogTC.setCellValueFactory(new PropertyValueFactory<>("priceCatalog"));
		
		TableColumn<Goods, BigDecimal>  theSameTC = new TableColumn<Goods, BigDecimal> ("is");
		theSameTC.setCellValueFactory(new PropertyValueFactory<>("theSame"));

		priceCatalogCombined.getColumns().addAll(priceCatalogTC, theSameTC);

		TableColumn<Goods, String> allowanceTC = new TableColumn<Goods, String>("allowance");
		allowanceTC.setCellValueFactory(new PropertyValueFactory<>("allowance"));
		TableColumn<Goods, BigDecimal>   priceStoreTC = new TableColumn<Goods, BigDecimal>  ("price store");
		priceStoreTC.setCellValueFactory(new PropertyValueFactory<>("priceStore"));
		TableColumn<Goods, Float> ballConsultantTC = new TableColumn<Goods, Float>("ball consultant");
		ballConsultantTC.setCellValueFactory(new PropertyValueFactory<>("ballConsultant"));
		TableColumn<Goods, BigDecimal>   priceBuyerTC = new TableColumn<Goods, BigDecimal>  ("price buyer");
		priceBuyerTC.setCellValueFactory(new PropertyValueFactory<>("priceBuyer"));
		TableColumn<Goods, Float> ballBuyerTC = new TableColumn<Goods, Float>();
		ballBuyerTC.setCellValueFactory(new PropertyValueFactory<>("ballBuyer"));

		// making text vertical in column. 
		Label label1 = new Label("ball buyer");
		label1.setRotate(-90);
		label1.setMinHeight(200);
		Group g = new Group(label1);
		ballBuyerTC.setGraphic(g);

		tableView.getColumns().addAll(discountTC, pageTC, articleTC, nameTC, priceCatalogCombined, 
				allowanceTC, priceStoreTC, ballConsultantTC, priceBuyerTC, ballBuyerTC);

		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		vBox.getChildren().add(tableView);

		root.setCenter(vBox);
		Scene scene = new Scene(root, 1300, 450);

		primaryStage.setTitle("Goods search application.");
		primaryStage.setScene(scene);
		primaryStage.show();

		btn.setOnAction(e -> {
			List<Goods> listGoods = null;
			
			String searchingName = textField.getText();
			
			if(searchingName != null && searchingName.trim().length() > 0){
				try {
					listGoods = fabDao.searchGoods(searchingName); //3494
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				listGoods = fabDao.getAllGoods();
			}
			
			ObservableList<Goods> goods = FXCollections.observableArrayList(listGoods);
			tableView.setItems(goods);
			
			
//			if(ballBuyerTC.isVisible()){
//				ballBuyerTC.setVisible(false);
//			} else {
//				ballBuyerTC.setVisible(true);
//			}
//			if(discountTC.isVisible()){
//				discountTC.setVisible(false);
//			} else {
//				discountTC.setVisible(true);
//			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}