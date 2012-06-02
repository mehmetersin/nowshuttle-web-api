package com.mesoft.nowshuttle.webapi.db;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mesoft.nowshuttle.webapi.om.Shuttle;
import com.mesoft.nowshuttle.webapi.om.TransferStatus;
import com.mesoft.nowshuttle.webapi.om.TransferStatusRequest;
import com.mesoft.nowshuttle.webapi.om.User;

public class DbEngine {

	public void insertUser(User user) {
		DaoIntf daoImpl = getDaoImpl();
		daoImpl.insertUser(user);

	}

	private DaoIntf getDaoImpl() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"Spring-Module.xml");

		DaoIntf daoImpl = (DaoIntf) context.getBean("daoImpl");
		return daoImpl;
	}

	public User findUserByUsername(String email) {
		DaoIntf daoImpl = getDaoImpl();
		return daoImpl.findUserByUsername(email);

	}

	public User checkUser(String email, String password) {
		DaoIntf daoImpl = getDaoImpl();
		return daoImpl.checkUser(email, password);

	}

	public void updateUser(int userId,String displayName) {
		DaoIntf daoImpl = getDaoImpl();
		daoImpl.updateUser(userId,displayName);

	}

	public List<Shuttle> getShuttles() {
		DaoIntf daoImpl = getDaoImpl();
		return daoImpl.getShuttles();
	}

	public List<TransferStatus> getTrasferList(String shuttleId, String date) {
		DaoIntf daoImpl = getDaoImpl();
		return daoImpl.getTrasferList(shuttleId, date);
	}

	public TransferStatus getTrasferStatus(String shuttleId, String date,
			String userId) {
		DaoIntf daoImpl = getDaoImpl();
		return daoImpl.getTrasferStatus(shuttleId, date, userId);
	}

	public void informTransferStatus(TransferStatusRequest req) {
		DaoIntf daoImpl = getDaoImpl();
		daoImpl.informTransferStatus(req);
	}
	
	public void updateTransferStatus(String state,int id) {
		DaoIntf daoImpl = getDaoImpl();
		daoImpl.updateTransferStatus(state, id);
	}
}
