package news.ssp.service.impl;

import javax.annotation.Resource;

import news.ssp.dao.ManagerDao;
import org.springframework.stereotype.Service;

import news.ssp.entity.Manager;
import news.ssp.service.ManagerService;

/**
 * 管理员Service实现类
 * @author user
 *
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService{

	@Resource
	private ManagerDao managerDao;

	public Manager getByUserName(String userName) {
		return managerDao.getByUserName(userName);
	}

	public Integer update(Manager manager) {
		return managerDao.update(manager);
	}
}
