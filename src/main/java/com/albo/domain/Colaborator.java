package com.albo.domain;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "colaborator")
public class Colaborator {

	    @Id
	    private long id;
	    private String name;
	    private String lastSync;
	    private ArrayList<String> editors;
	    private ArrayList<String> writers;
        private ArrayList<String> colirist;
	    
        
        public Colaborator(long id, String lastSync) {
    		this.id = id;
    		this.lastSync = lastSync;
    	}
        
        
	    public long getId() {
			return id;
		}


		public void setId(long id) {
			this.id = id;
		}


		public String getLastSync() {
			return lastSync;
		}


		public void setLastSync(String lastSync) {
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


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		
	   
   

	


}
