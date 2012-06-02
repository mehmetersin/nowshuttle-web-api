package com.mesoft.nowshuttle.webapi.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mesoft.nowshuttle.webapi.om.Shuttle;
import com.mesoft.nowshuttle.webapi.om.TransferStatus;
import com.mesoft.nowshuttle.webapi.om.TransferStatusRequest;
import com.mesoft.nowshuttle.webapi.om.User;

public class DaoImpl implements DaoIntf {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insertUser(User user) {

		String sql = "INSERT INTO USER "
				+ "(email, password, status,displayName) VALUES (?, ?, ?,?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getStatus());
			ps.setString(4, user.getDisplayName());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	@Override
	public void updateUser(int userId,String displayName) {
		String sql = "update USER set displayName=? where id=?";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, displayName);
			ps.setInt(2, userId);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	@Override
	public User checkUser(String username, String password) {

		String sql = "SELECT * FROM USER WHERE email = ? and password=?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			User u = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				u = new User();
				u.setDisplayName(rs.getString("displayName"));
				u.setEmail(rs.getString("email"));
				u.setStatus(rs.getString("status"));
				u.setUserId(rs.getInt("id"));
			}
			rs.close();
			ps.close();
			return u;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public User findUserById(String id) {

		String sql = "SELECT * FROM USER WHERE id = ?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.valueOf(id));
			User u = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				u = new User();
				u.setDisplayName(rs.getString("displayName"));
				u.setEmail(rs.getString("email"));
				u.setStatus(rs.getString("status"));
				u.setUserId(rs.getInt("id"));
			}
			rs.close();
			ps.close();
			return u;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public User findUserByUsername(String username) {

		String sql = "SELECT * FROM USER WHERE email = ?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			User u = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				u = new User();
				u.setDisplayName(rs.getString("displayName"));
				u.setEmail(rs.getString("email"));
				u.setStatus(rs.getString("status"));
				u.setUserId(rs.getInt("id"));
			}
			rs.close();
			ps.close();
			return u;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public List<Shuttle> getShuttles() {
		String sql = "SELECT * FROM SHUTTLE ";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			List<Shuttle> l = new ArrayList<Shuttle>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Shuttle s = new Shuttle();
				s.setId(rs.getString("id"));
				s.setName(rs.getString("name"));
				l.add(s);
			}
			rs.close();
			ps.close();
			return l;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	@Override
	public TransferStatus getTrasferStatus(String shuttleId, String date,
			String userId) {

		String sql = "SELECT * FROM TRANSFER_LIST where shuttleId=? and currentDate=? and userId=?";
		Connection conn = null;
		TransferStatus l = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, shuttleId);
			Date a = new Date(Long.valueOf(date));
			ps.setDate(2, a);
			ps.setString(3, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				l = new TransferStatus();
				l.setState(rs.getString("state"));
				l.setUser(findUserById(rs.getString("userId")));
				l.setId(rs.getInt("id"));
			}
			rs.close();
			ps.close();
			return l;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public List<TransferStatus> getTrasferList(String shuttleId, String date) {

		String sql = "SELECT * FROM TRANSFER_LIST where shuttleId=? and currentDate=? ";
		Connection conn = null;
		List<TransferStatus> l = new ArrayList<TransferStatus>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, shuttleId);
			Date a = new Date(Long.valueOf(date));
			ps.setDate(2, a);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TransferStatus s = new TransferStatus();
				s.setState(rs.getString("state"));
				s.setUser(findUserById(rs.getString("userId")));
				l.add(s);
			}
			rs.close();
			ps.close();
			return l;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public void informTransferStatus(TransferStatusRequest req) {

		String sql = "INSERT INTO TRANSFER_LIST "
				+ "(shuttleId,userId,currentDate,state) VALUES (?, ?, ?,?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, req.getShuttleId());
			ps.setString(2, req.getUserId());
			ps.setDate(3, new Date(Long.valueOf(req.getDate())));
			ps.setString(4, req.getState());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}
	
	
	@Override
	public void updateTransferStatus(String state,int id) {

		String sql = "update TRANSFER_LIST "
				+ "set state=? where id=?";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, state);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}

}