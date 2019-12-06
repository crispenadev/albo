package com.albo.domain;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "colaborator")
public class ColaboratorResponse {

	    @Id
	    private long id;
	    private String name;
	    private String lastSync;
		private Set<String> editors;
	    private Set<String> writers;
        private Set<String> colirist;
        
	    public Set<String> getEditors() {
			return editors;
		}


		public void setEditors(Set<String> editors) {
			this.editors = editors;
		}


		public Set<String> getWriters() {
			return writers;
		}


		public void setWriters(Set<String> writers) {
			this.writers = writers;
		}


		public Set<String> getColirist() {
			return colirist;
		}


		public void setColirist(Set<String> colirist) {
			this.colirist = colirist;
		}


	    
        
        public ColaboratorResponse(long id, String lastSync) {
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


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}

}
