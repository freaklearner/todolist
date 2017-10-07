package model;

import java.util.*;
import java.sql.*;
import helper.*;

public class eval{
	
	public ArrayList<topic> inital(String driver, String path,int usr) throws SQLException,ClassNotFoundException{
		Connection con;
		PreparedStatement st;
		Class.forName(driver);
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
	public int insert(String driver,String path,int id,String txt,int v) throws SQLException,ClassNotFoundException{
		Connection con = null;
		PreparedStatement st = null;
		int result;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(path);
			if(v==1){
				st = con.prepareStatement("insert into main(userId,txt) values(?,?)");
			}
			else if(v==2){
				st = con.prepareStatement("insert into s_topic(topicId,stxt) values(?,?)");
			}
			else{
				st = con.prepareStatement("insert into ss_topic(stopicId,sstxt) values(?,?)");
			}
			st.setInt(1,id);
			st.setString(2,txt);
			st.executeUpdate();
			result = updatedValue(con,v);
			st.close();
			con.close();
		}catch(SQLException ex){
			throw ex;
		}
		catch(ClassNotFoundException ex){
			throw ex;
		}
		finally{
			if(st!=null){
				st.close();
			}
			if(con!=null){
				con.close();
			}
		}
		return(result);
	}
	int updatedValue(Connection con,int v) throws SQLException{
		//Connection con;
		PreparedStatement st = null;
		int value=0;
		try{
			//Class.forName(driver);
			//con = DriverManager.getConnection(path);
			if(v==1){
				st = con.prepareStatement("select topicId from main order by topicId desc limit 1");
			}
			else if(v==2){
				st = con.prepareStatement("select stopicId from s_topic order by stopicId desc limit 1");
			}
			else{
				st = con.prepareStatement("select sstopicId from ss_topic order by sstopicId desc limit 1");
			}
			ResultSet rs = st.executeQuery();
			if(v==1){
				if(rs.next()){
					value = rs.getInt("topicId");
				}
			}
			else if(v==2){
				if(rs.next()){
					value = rs.getInt("stopicId");
				}
			}
			else{
				if(rs.next()){
					value = rs.getInt("sstopicId");
				}
			}
			rs.close();
			st.close();
			//con.close();
			
		}catch(SQLException ex){
			throw ex;
		}
		finally{
			if(st!=null){
				st.close();
			}
			if(con!=null){
				//con.close();
			}
		}
		return(value);
	}
	
}