package edu.ku.cete.tde.webservice.domain;

public class PrimaryKeyRequest {

    private Long id;
    
    public PrimaryKeyRequest(Long id){
        this.setId(id);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
}
