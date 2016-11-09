package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class DatabaseBookCatalog extends BookCatalog {
	Connection con;
	static final String DATABASE_URL = "jdbc:mysql://localhost/library";
	static final String DATABASE_USER = "root";
	static final String DATABASE_PASSWORD = "UTsyatem2016";

	public DatabaseBookCatalog() {
		connect();
	}

	protected boolean connect(){
		String msg = "";
		try{
			if(con != null){
				if(con.getWarnings() == null)
					return true;
				disconnect();
		}
			try{
				Class.forName("com.mysql.jdbc.Driver");
				msg = "ドライバーのロードに成功しました";
			}catch(ClassNotFoundException e){
				msg = "ドライバーのロードに失敗しました";
			}

			con = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
			if(con.getWarnings() == null)
				return true;
			disconnect();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	protected void disconnect(){
		if(con != null){
			try{
				con.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			con = null;
		}
	}

	public Book getBook(String bookId){
		Book book = null;
		try{
			connect();
			PreparedStatement statement = con.prepareStatement("SELECT * FROM BOOKCATALOG WHERE bookId = ?");
			statement.setString(1,  bookId);
			ResultSet result = statement.executeQuery();
			if(result.next())
				book = createBook(result);
			statement.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return book;
	}

	public Book[] getBooks(){
		Book[] bookArray = null;
		try{
			connect();
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery("");
			bookArray = createBooks(result);
			statement.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return bookArray;
	}

	public synchronized Book addBook(Book book){
		try{
			connect();
			String bookId = book.getBookId();
			if(bookId == null || bookId.length() == 0 || getBook(bookId) != null)
				bookId = createUniqueBookId();
			PreparedStatement statement = con.prepareStatement("INSERT INTO bookcatalog VALUE(?,?,?,?,?,?,?,?,?,?,?.?)");

			statement.setString(1, bookId);
			statement.setString(2, book.getTitle());
			statement.setString(3, book.getAuthor());
			statement.setString(4, book.getTranslator());
			statement.setString(5, book.getPublisher());
			statement.setString(6, book.getPublishingDate());
			statement.setString(7, book.getCode());
			statement.setString(8, book.getStatus());
			statement.setString(9, book.getKeyword());
			statement.setString(10, book.getMemo());
			statement.setString(11, book.getDataCreator());
			statement.setString(12, book.getDataCreatedDate());
			int result = statement.executeUpdate();
			statement.close();
			return getBook(bookId);
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return null;
	}

	public void deleteBook(String bookId){
		try{
			connect();
			PreparedStatement statement = con.prepareStatement("DELETE FROM bookcatalog WHERE bookid = ? ");
			statement.setString(1, bookId);
			int result = statement.executeUpdate();
			statement.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}

	public Book[] searchBooks(String word){
		Book[] bookArray = null;
		try{
			connect();
			PreparedStatement statement = con.prepareStatement("");//SELECT * FROM bookcatalog WHERE bookid = ?");
			String pattern = "%" + word.toUpperCase() + "%";
			for(int i = 1; i <= 10; i++)
				statement.setString(i, pattern);
			ResultSet result = statement.executeQuery();
			bookArray = createBooks(result);
			statement.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return bookArray;
	}

	protected Book createBook(ResultSet result)throws SQLException{
		Book book = new Book();
		book.setBookId(result.getString("bookId"));
		book.setTitle(result.getString("title"));
		book.setAuthor(result.getString("author"));
		book.setTranslator(result.getString("translator"));
		book.setPublisher(result.getString("publisher"));

		book.setPublishingDate(result.getTimestamp("publishingdate").toString().substring(0, 10));
		book.setCode(result.getString("codeid"));
		book.setStatus(result.getString("Statusid"));
		book.setKeyword(result.getString("keyword"));
		book.setMemo(result.getString("Memo"));
		book.setDataCreatedDate(result.getTimestamp("datacreateddate").toString().substring(0, 16));
		book.setDataCreator(result.getString("datacreator"));
		return book;
	}

	protected Book[] createBooks(ResultSet result)throws SQLException{
		ArrayList<Book>books = new ArrayList<Book>();
		while(result.next())
			books.add(createBook(result));
		Book [] bookArray = new Book[books.size()];
		books.toArray(bookArray);
		return bookArray;
	}

}
