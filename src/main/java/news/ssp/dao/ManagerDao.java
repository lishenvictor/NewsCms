package news.ssp.dao;

import news.ssp.entity.Manager;

/**
 * ����ԱDao�ӿ�
 * @author user
 *
 */
public interface ManagerDao {

	/**
	 * ͨ���û��������û�ʵ��
	 * @param userName
	 * @return
	 */
	public Manager getByUserName(String userName);
	
	/**
	 * ���¹���Ա��Ϣ
	 * @param manager
	 * @return
	 */
	public Integer update(Manager manager);
}
