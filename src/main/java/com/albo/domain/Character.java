package com.albo.domain;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "character")
public class Character {

	    @Id
	    private long id;
	    public long getId() {
			return id;
		}


		public void setId(long id) {
			this.id = id;
		}


		public Date getLastSync() {
			return lastSync;
		}


		public void setLastSync(Date lastSync) {
			this.lastSync = lastSync;
		}


		public ArrayList<String> getEditors() {
			return editors;
		}


		public void setEditors(ArrayList<String> editors) {
			this.editors = editors;
		}


		public ArrayList<String> getWriters() {
			return writers;
		}


		public void setWriters(ArrayList<String> writers) {
			this.writers = writers;
		}


		public ArrayList<String> getColirist() {
			return colirist;
		}


		public void setColirist(ArrayList<String> colirist) {
			this.colirist = colirist;
		}


		private Date lastSync;
	    private ArrayList<String> editors;
	    private ArrayList<String> writers;
        private ArrayList<String> colirist;
	   
        
        public Character(long id, Date lastSync) {
    		this.id = id;
    		this.lastSync = lastSync;
    	}

	


}
