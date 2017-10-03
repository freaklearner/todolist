package model;

import java.util.*;
import java.sql.*;
import helper.*;

public class eval{
	public ArrayList<topic> inital(String driver, String path,int usr) throws SQLException,ClassNotFoundException{
		Connection con;
		PreparedStatement st;
		Statement query,subQuery;
		st = null;
		query = null;
		subQuery = null;
		con = null;
		//rs = rs1 = rs2 =null;
		ArrayList<topic> list = new ArrayList<topic>();
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(path);
			st = con.prepareStatement("select * from main where userId=?");
			query = con.createStatement();
			subQuery = con.createStatement();
			String q;
			st.setInt(1,usr);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				topic t = new topic();
				int id = rs.getInt("topicId");
				t.setId(id);
				String txt = rs.getString("txt");
				t.setData(txt);
				q = "select * from s_topic where topicId = "+id;
				ResultSet rs1 = query.executeQuery(q);
				while(rs1.next()){
					topic sub1 = new topic();
					int sid = rs1.getInt("sTopicId");
					sub1.setId(sid);
					String stxt = rs1.getString("stxt");
					sub1.setData(stxt);
					q = "select * from ss_topic	where sTopicId = "+sid;
					ResultSet rs2 = subQuery.executeQuery(q);
					while(rs2.next()){
						topic sub2 = new topic();
						int ssid = rs2.getInt("sstopicId");
						sub2.setId(ssid);
						String sstxt = rs2.getString("sstxt");
						sub2.setData(sstxt);
						sub1.addTopic(sub2);
					}
					rs2.close();
					t.addTopic(sub1);
				}
				rs1.close();
				list.add(t);
			}
			rs.close();
			query.close();
			subQuery.close();
			st.close();
			con.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		catch(ClassNotFoundException ex){
			throw ex;
		}
		finally{
			
			if(st!=null){
				st.close();
			}
			
			if(query!=null){
				query.close();
			}
			if(subQuery!=null){
				query.close();
			}
			
			/*if(rs!=null){
				rs.close();
			}
			if(rs1!=null){
				rs1.close();
			}
			if(rs2!=null){
				rs2.close();
			}*/
			if(con!=null){
				con.close();
			}
		}
		return list;
	}
	
}