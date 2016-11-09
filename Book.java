package model;

import java.io.Serializable;

public class Book implements Serializable {
		private String bookId;
		private String title;
		private String author;
		private String translator;
		private String publisher;
		private String publishingDate;
		private String code;
		private String status;
		private String keyword;
		private String memo;
		private String dataCreator;
		private String dataCreatedDate;

		public String getBookId(){return this.bookId;}
		public void setBookId(String value){this.bookId = value;}
		public String getTitle(){return this.title;}
		public void setTitle(String value){this.title = value;}
		public String getAuthor(){return this.author;}
		public void setAuthor(String value){this.author = value;}
		public String getTranslator(){return this.translator;}
		public void setTranslator(String value){this.translator = value;}
		public String getPublisher(){return this.publisher;}
		public void setPublisher(String value){this.publisher = value;}
		public String getPublishingDate(){return this.publishingDate;}
		public void setPublishingDate(String value){this.publishingDate = value;}
		public String getCode(){return this.code;}
		public void setCode(String value){this.code = value;}
		public String getMemo(){return this.memo;}
		public void setMemo(String value){this.memo = value;}
		public String getStatus(){return this.status;}
		public void setStatus(String value){this.status = value;}
		public String getDataCreatedDate(){return this.dataCreatedDate;}
		public void setDataCreatedDate(String value){this.dataCreatedDate = value;}
		public String getDataCreator(){return this.dataCreator;}
		public void setDataCreator(String value){this.dataCreator = value;}
		public String getKeyword(){return this.keyword;}
		public void setKeyword(String value){this.keyword = value;}

	}