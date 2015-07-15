package cn.ruiyi.base.service.mcode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import cn.ruiyi.base.dao.mcode.McodeDao;
import cn.ruiyi.base.entity.Mcode;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;

/**
 * 码表业务逻辑实现
 * @author chen
 * @version 1.0, 2012-11-13
 */
@Component
@Transactional(readOnly = true)
public class McodeService extends BaseManagerService<Mcode>{
	@Autowired
	private McodeDao mcodeDao;
	@Override
	protected BaseManagerDao<Mcode> getDomainDao() {
		return mcodeDao;
	}
	
	
	/**
	 * 根据码类型查找
	 */
	public List<Mcode> findByMtype(String mtype){		
		
		return this.mcodeDao.findByMtype(mtype);
	}
	
	public List<String> findByMtypeReturnValues(String mtype){
		List<Mcode> mcodes = findByMtype(mtype);
		List<String> list = Lists.newArrayList();
		for(Mcode mcode : mcodes){
			list.add(mcode.getMkey());
		}
		return list;
	}
	
	/**
	 * 根据码类型和码名查找
	 */
	public List<Mcode> findByMtypeAndMkey(String mtype,String mkey){
		
		return this.mcodeDao.findByMtypeAndMkey(mtype, mkey);
	}
	
	/**
	 * 根据码类型和码值得到一条码记录
	 */
	public Mcode findByMtypeAndMvalue(String mtype,String mvalue){
		return this.mcodeDao.findByMtypeAndMvalue(mtype, mvalue);
	}


	
	/**
	 * 码表查询，返回html下拉菜单
	 * @param type
	 * @param selectedId
	 * @return
	 */
	public String getMbHtmlSelect(String lx, String selectedId){
		List<Mcode> list = this.findByMtype(lx);
		
		StringBuffer sb = new StringBuffer();
		for(Mcode mb : list){
			sb.append("<option value='");
			sb.append(mb.getMvalue());
            sb.append("'");
            if(selectedId != null && !"".equals(selectedId)){
            	if(selectedId.equals(mb.getMvalue())){
    				sb.append(" selected");
    			}
            }
            sb.append(">");
            sb.append(mb.getMkey());
            sb.append("</option>");
		}
		
		return sb.toString();
	}
}
