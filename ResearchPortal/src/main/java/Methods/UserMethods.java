package Methods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.pojo.RecommendationPojo;
import com.pojo.UserPojo;

public class UserMethods {
	
	
	 public boolean emailPhoneVal(String email,String pNumber,Connection con) {
	        
	        String emailQuery="select email,pnumber from usertable where email=? or pnumber=?";
	        try {
	            PreparedStatement ps=con.prepareStatement(emailQuery);
	            ps.setString(1, email);
	            ps.setString(2, pNumber);
	            ResultSet rs=ps.executeQuery();
	            while(!(rs.next())) {
	                 String regex = "^[A-Za-z0-9+_.-]+@(.+)$"; 
	                 Pattern pattern = Pattern.compile(regex);  
	                 Matcher matcher = pattern.matcher(email);
	                if(matcher.matches() && numberVal(pNumber)&& pNumber.length()==10) {
	                    return true;                    
	                }
	                else {
	                    return false;
	                }
	            }
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }        
	        return false;
	        
	    }
	    
	    
	    public boolean numberVal(String pNumber) {
	        
	        
	        try {
	            double d = Double.parseDouble(pNumber);
	        } catch (NumberFormatException nfe) {
	            return false;
	        }
	        return true;
	        
	    }
	
	
	
	public boolean validUserName(String userName,Connection con) {
		String userVal="select username from usertable where username=?";
		try {
			PreparedStatement ps=con.prepareStatement(userVal);
			ps.setString(1, userName);
			ResultSet rs=ps.executeQuery();
			while(!(rs.next())) {
				if(userName.length()>=3 && userName.length()<=10 && StringUtils.isAlphanumeric(userName)) {
					return true;
				}
				else {
					return false;
				}
			}
			
		} catch (SQLException e) {
			System.out.println("Exception in validUser");		
			e.printStackTrace();
		}
		return false;
	}
	public boolean validPassword(String pass,Connection con) {
		if(pass.length()>=6 &&StringUtils.isAlphanumeric(pass)) {
			return true;
		}
		else {
			return false;
		}

	}

	
	public void insertDetails(UserPojo up,Connection con) {
		String insertUser="insert into usertable (username,password,email,pnumber) values(?,?,?,?)";
		try {
			PreparedStatement ps=con.prepareStatement(insertUser);
			ps.setString(1,up.getUsername());
			ps.setString(2,up.getPassword());
			ps.setString(3,up.getEmail());
			ps.setString(4,up.getPhonenumber());
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void insertToken(int userid,Connection con) {
		String tokenInsert="insert into tokentable (userid,sessionToken) values (?,?)";
		 String sessionToken = UUID.randomUUID().toString();

		try {
//			String sessionToken = UUID.randomUUID().toString();
			PreparedStatement ps2 = con.prepareStatement(tokenInsert);
			ps2.setInt(1,userid);
			ps2.setString(2, sessionToken);
			ps2.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String displayToken(int userid,Connection con) {
    	String token="select sessionToken from tokentable where userid=?";
    	 try {
	            PreparedStatement ps=con.prepareStatement(token);
	            ps.setInt(1, userid);
	            ResultSet rs=ps.executeQuery();
	            String t = null;
	            while(rs.next()) {
	            	t=rs.getString("sessionToken");
	            }
	            return t;
    	 }catch (Exception e) {
			// TODO: handle exception
		}
		return null;


	}
	
	 public String userloginval(String username,String password,Connection con) {
	        
	        String loginquery="select username,password from usertable where username=? and password=?";
	        try {
	            PreparedStatement ps=con.prepareStatement(loginquery);
	            ps.setString(1, username);
	            ps.setString(2, password);
	            ResultSet rs=ps.executeQuery();
	            while((rs.next())) {
	            	String userIdQuery="select userid from usertable where username=?";
	            	PreparedStatement ps1=con.prepareStatement(userIdQuery);
		            ps1.setString(1, username);
		            ResultSet rs1=ps1.executeQuery();
            		String t=null;

		            	while(rs1.next()) {
		            		delete(rs1.getInt("userid"), con);

		            		String tokenInsert="insert into tokentable (userid,sessionToken) values (?,?)";
			           		try {
//			           			String sessionToken = UUID.randomUUID().toString();
			            		t=sessiontoken();

			           			PreparedStatement ps2 = con.prepareStatement(tokenInsert);
			           			ps2.setInt(1,rs1.getInt("userid"));
			           			ps2.setString(2,t);
			           			ps2.execute();		            
			           		}catch (Exception e) {
								// TODO: handle exception
							}
		            }		
	            	return t;   
	            }
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }        
	        return null;
	        
	    }
	 public String sessiontoken()
	 {
		   String sessionToken = UUID.randomUUID().toString();

		return sessionToken;
	 }
	 
	public boolean insertDetails(RecommendationPojo rp,Connection con) {
		String tokenVal="select userid from tokentable where sessionToken=?";
		try {
			PreparedStatement ps=con.prepareStatement(tokenVal);
			ps.setString(1, rp.getSessionToken());
			ResultSet  rs=ps.executeQuery();
			while(rs.next()) {
				String insertDetails="INSERT INTO recommendation (userid, stocksymbol, rtype,rdate, rdetails) VALUES (?,?,?,?,?)";
				PreparedStatement ps1=con.prepareStatement(insertDetails);
				ps1.setInt(1,rs.getInt("userid"));
				ps1.setString(2, rp.getStocksymbol());
				ps1.setString(3, rp.getRtype());
				ps1.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
				ps1.setString(5, rp.getRdetails());

				ps1.execute();
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	 
		public int viewDetails(RecommendationPojo rp,Connection con) {
			String tokenVal="select userid from tokentable where sessionToken=?";
			try {
				PreparedStatement ps=con.prepareStatement(tokenVal);
				ps.setString(1, rp.getSessionToken());
				ResultSet  rs=ps.executeQuery();
				while(rs.next()) {
					
					return rs.getInt("userid");
					
				}				
			}catch (Exception e) {
				// TODO: handle exception
			}
			return -1;
			
		}
	public List<RecommendationPojo> list(int userid,Connection con){
		ArrayList<RecommendationPojo> list=new ArrayList<>();
		String viewQuery="select rid,stocksymbol, rtype, rdate, rdetails from recommendation where userid=?";
		PreparedStatement ps1;
		try {
			ps1 = con.prepareStatement(viewQuery);
			ps1.setInt(1,userid);
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()) {
				list.add(new RecommendationPojo(rs1.getInt("rid"),rs1.getString("stocksymbol"),rs1.getString("rdate"), rs1.getString("rtype"),rs1.getString("rdetails")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	

	public List<RecommendationPojo> filter(RecommendationPojo rp,Connection con) {
		String filterQuery="SELECT * FROM recommendation where rid=? or rtype=? or stocksymbol=? or rdate=?";
		ArrayList<RecommendationPojo> list=new ArrayList<>();
		try {
			PreparedStatement ps1=con.prepareStatement(filterQuery);
			ps1.setInt(1,rp.getRid());
			ps1.setString(2, rp.getRtype());
			ps1.setString(3, rp.getStocksymbol());
			ps1.setString(4, rp.getRdate());
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()) {
				list.add(new RecommendationPojo(rs1.getInt("rid"),rs1.getString("stocksymbol"),rs1.getString("rdate"), rs1.getString("rtype"),rs1.getString("rdetails")));
			}
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String logoutFromAll(String username,String password,Connection con) {
        
        String loginquery="select username,password from usertable where username=? and password=?";
        try {
            PreparedStatement ps=con.prepareStatement(loginquery);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs=ps.executeQuery();
            while((rs.next())) {
            	String userIdQuery="select userid from usertable where username=?";
            	PreparedStatement ps1=con.prepareStatement(userIdQuery);
	            ps1.setString(1, username);
	            ResultSet rs1=ps1.executeQuery();{
	            	while(rs1.next()) {
	            		delete(rs1.getInt("userid"), con);
	            		
	            	String tokenInsert="insert into tokentable (userid,sessionToken) values (?,?)";
	            	String t=sessiontoken();
	           		try {
//	           			String sessionToken = UUID.randomUUID().toString();
	           			PreparedStatement ps2 = con.prepareStatement(tokenInsert);
	           			ps2.setInt(1,rs1.getInt("userid"));
	           			ps2.setString(2,t);
	           			ps2.execute();
	           		} catch (SQLException e) {
	           			// TODO Auto-generated catch block
	           			e.printStackTrace();
	           		}
	            		
	            	            		
	                	return t;   

	            	}
	            }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        return null;
        
    }
	public void delete(int userid,Connection con) {
		String delete="DELETE from tokentable where userid=?";
		try {
			PreparedStatement ps=con.prepareStatement(delete);
			ps.setInt(1, userid);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}










