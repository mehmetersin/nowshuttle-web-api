package com.mesoft.nowshuttle.webapi.db;

import java.util.List;

import com.mesoft.nowshuttle.webapi.om.Shuttle;
import com.mesoft.nowshuttle.webapi.om.TransferStatus;
import com.mesoft.nowshuttle.webapi.om.TransferStatusRequest;
import com.mesoft.nowshuttle.webapi.om.User;

public interface DaoIntf {

	public abstract void insertUser(User user);


	public abstract User checkUser(String username, String password);

	public abstract User findUserById(String id);

	public abstract List<Shuttle> getShuttles();


	public abstract User findUserByUsername(String username);
	
	public abstract void informTransferStatus(TransferStatusRequest request);

	public abstract List<TransferStatus> getTrasferList(String shuttleId, String date);

	public abstract TransferStatus getTrasferStatus(String shuttleId, String date, String userId);

	public abstract void updateTransferStatus(String state, int id);

	public abstract void updateUser(int userId, String displayName);


}