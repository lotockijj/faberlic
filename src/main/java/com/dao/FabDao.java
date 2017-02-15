package com.dao;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.faberlic.Goods;

public class FabDao {
	
	private Connection myConn;
	
	public FabDao() throws Exception{
		// get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("demo.properties"));
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		//connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
		System.out.println("DB connection successful to: " + dburl);
	}

	public List<Goods> getAllGoods(){
		List<Goods> list = new ArrayList<>();
		try(Statement myStmt = myConn.createStatement();
			ResultSet 	myRs = myStmt.executeQuery("select *from fab;")){
			
			while(myRs.next()){
				Goods tempGoods = convertRowToGoods(myRs);
				list.add(tempGoods);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public List<Goods> searchGoods(String article) throws SQLException{
		List<Goods> list = new ArrayList<>();
		
		ResultSet myRs = null;
		
		article += "%";
		try(PreparedStatement myStmt = myConn.prepareStatement("select *from fab "
				+ "where article like ?;")){
			
			myStmt.setString(1, article);
			
			myRs = myStmt.executeQuery();
			
			while(myRs.next()){
				Goods tempGoods = convertRowToGoods(myRs);
				list.add(tempGoods);
			}
		} finally {
			myRs.close();
		}
		return list;
	}
	
	private Goods convertRowToGoods(ResultSet myRs) throws SQLException{
		String discount = myRs.getString("discount");
		String page = myRs.getString("page");
		String article = myRs.getString("article");
		String name = myRs.getString("name");
		
		BigDecimal priceCatalog = myRs.getBigDecimal("priceCatalog");
		BigDecimal theSame = myRs.getBigDecimal("theSame");
		String allowance = myRs.getString("allowance");
		BigDecimal priceStore = myRs.getBigDecimal("priceStore");
		float ballConsultant = myRs.getFloat("ballConsultant");
		BigDecimal priceBuyer = myRs.getBigDecimal("priceBuyer");
		float ballBuyer = myRs.getFloat("ballBuyer");
		
		Goods tempGoods = new Goods(discount, page, article, name, priceCatalog, 
				theSame, allowance, priceStore, ballConsultant, priceBuyer, ballBuyer);
		
		return tempGoods;
	}
}
