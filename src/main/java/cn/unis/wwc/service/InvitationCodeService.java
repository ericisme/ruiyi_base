package cn.unis.wwc.service;

import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.util.RandomUtil;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.utils.JxlUtil;
import cn.unis.wwc.dao.InvitationCodeDao;
import cn.unis.wwc.entity.InvitationCode;




/**
 * 邀请码管理 .service
 *
 * @author eric
 */
@Component
@Transactional(readOnly = true)
public class InvitationCodeService extends BaseManagerService<InvitationCode>{


	@Autowired
	private InvitationCodeDao invitationCodeDao;
	@Override
	protected BaseManagerDao<InvitationCode> getDomainDao() {
		return invitationCodeDao;
	}

	public InvitationCode findByCode(String code){
		InvitationCode invitationCode = null;
		if(code != null){
			invitationCode = invitationCodeDao.getByCode(code);
		}
		return invitationCode;
	}

	/**
	 * 保存，不带自动生成邀请码
	 */
	public void saveWithoutCode(InvitationCode invitationCode){
		this.getDomainDao().saveAndFlush(invitationCode);
	}

	/**
	 *保存实体,带自动生成邀请码
	 *用于实体的增加、修改
	 *新增时生产随机的六位邀请码
	 * @param news
	 */
	@Transactional(readOnly=false)
	public void save(InvitationCode invitationCode){
		//类型，默认为1=管理员内部邀请
		if(invitationCode.getType()==null){
			invitationCode.setType(1);
		}
		if(invitationCode.getCode()==null){//新增
			invitationCode.setCode(genANewCode(4));
			this.getDomainDao().saveAndFlush(invitationCode);
		}else{//编辑
			this.getDomainDao().saveAndFlush(invitationCode);
		}
	}

	/**
	 * 生成一个邀请码,4位不区分大小写
	 */
	public String genANewCode(int length){
		boolean flag = true;
		String code = "";
		while(flag){
			String newCode = RandomUtil.getInstance().randNumberAndUpperAlpha(length);
			InvitationCode ic = invitationCodeDao.getByCode(newCode);
			if(ic == null){
				code =  newCode;
				flag = false;
			}
		}
		System.out.println("code:"+code);
		return code;
	}



	/**
	 * 导出excel
	 * @param _invitationCode
	 * @param os
	 * @throws Exception
	 */
	public void exportXls(List<InvitationCode> _invitationCode, OutputStream os) throws Exception{
		//生成excel文件
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		// sheet名称
		WritableSheet wsheet = wwb.createSheet("邀请码", 0);
		//设置列宽
		wsheet.setColumnView(0, 20);
		wsheet.setColumnView(0, 20);
		wsheet.setColumnView(1, 20);
		wsheet.setColumnView(2, 20);
		wsheet.setColumnView(3, 20);
		wsheet.setColumnView(4, 20);
		wsheet.setColumnView(5, 50);
		//设置标题
		WritableCellFormat totalx2Format = JxlUtil.getWritableCellFormatTitleRed();//红字蓝底
		String[] titles = {"类型","邀请码","被邀人名字","公司","电话","电邮","地区","备注"};
		for(int i=0;i<titles.length;i++){
			wsheet.addCell(new Label(i,0,titles[i],totalx2Format));
        }
		int x = 1,y = 0;
		for(InvitationCode invitationCode : _invitationCode){
			y=0;
			String code = "";
			if(invitationCode.getType()==1){
				code=invitationCode.getCode();
			}
			if(invitationCode.getType()==2){
				if(invitationCode.getReviewStatus()==null){
					code="申请中";
				}else{
					if(invitationCode.getReviewStatus().equals(1)){
						code=invitationCode.getCode();
					}
					if(invitationCode.getReviewStatus().equals(0)){
						code="审核不通过";
					}
				}
			}
			wsheet.addCell(new Label(y++, x, invitationCode.getType()==1?"内部邀请":"自助申请", JxlUtil.getWritableCellFormatTitleWrite2()));
			wsheet.addCell(new Label(y++, x, code, JxlUtil.getWritableCellFormatTitleWrite2()));
			wsheet.addCell(new Label(y++, x, invitationCode.getName(), JxlUtil.getWritableCellFormatTitleWrite2()));
			wsheet.addCell(new Label(y++, x, invitationCode.getCompany(), JxlUtil.getWritableCellFormatTitleWrite2()));
			wsheet.addCell(new Label(y++, x, invitationCode.getTel(), JxlUtil.getWritableCellFormatTitleWrite2()));
			wsheet.addCell(new Label(y++, x, invitationCode.getEmail(), JxlUtil.getWritableCellFormatTitleWrite2()));
			wsheet.addCell(new Label(y++, x, invitationCode.getDistrictCode(), JxlUtil.getWritableCellFormatTitleWrite2()));
			wsheet.addCell(new Label(y++, x, invitationCode.getRemark(), JxlUtil.getWritableCellFormatTitleWrite2()));
			x++;
		}
		// 主体内容生成结束
		wwb.write();
		wwb.close();
		os.close();
	}



}
