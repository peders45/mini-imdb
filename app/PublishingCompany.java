package app;

import java.sql.*;

public class PublishingCompany extends ActiveDomainObject {
	
	private int companyID;
	private String url;
	private String address;
	private String country;
	
	private static final int NOID = -1;
	
	// constructor for object with new category
	public PublishingCompany(String url, String address, String country ) {
		this.companyID = NOID;
		this.url = url;
		this.address = address;
		this.country = country;
	}
	
	// constructor for object with existing category
	public PublishingCompany(int companyID) {
		this.companyID = companyID;
	}
	
	public int getCompanyID() {
		return this.companyID;
	}
	
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	
	@Override
	public void initialize(Connection conn) {
		try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT urlLink, Address, Country FROM PublishingCompany WHERE CompanyID=" + companyID);
            while (rs.next()) {
            	url =  rs.getString("urlLink");
            	address =  rs.getString("Address");
            	country =  rs.getString("Country");
            }

        } catch (Exception e) {
            System.out.println("db error during select of publishing company= "+e);
            return;
        }
	}
	
	@Override
	public void refresh(Connection conn) {
		initialize (conn);
	}
	
	@Override
	public void save(Connection conn) {
		try {    
            Statement stmt = conn.createStatement(); 
            stmt.executeUpdate("INSERT INTO PublishingCompany (urlLink, Address, Country) VALUES ('"+url+"','"+address+"','"+country+"')"
            		, stmt.RETURN_GENERATED_KEYS);
            
            // update object's id to autogenerated id in database
        	ResultSet generatedKeys = stmt.getGeneratedKeys();
        	if (generatedKeys.next()) {
        		this.setCompanyID((generatedKeys.getInt(1)));
        	} 
            
        } catch (Exception e) {
            System.out.println("db error during insert of publishing company="+e);
            return;
        }
		
	}

}
