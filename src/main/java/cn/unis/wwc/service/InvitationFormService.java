package cn.unis.wwc.service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.util.StringUtil;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.utils.JxlUtil;
import cn.unis.wwc.dao.InvitationFormDao;
import cn.unis.wwc.entity.InvitationForm;
import cn.unis.wwc.entity.InvitationFormApplicant;

/**
 * 邀请 详细信息表单 .service层
 *
 * @author eric
 */
@Component
@Transactional(readOnly = false)
public class InvitationFormService extends BaseManagerService<InvitationForm>{
	@Autowired
	private McodeService mcodeService;
	@Autowired
	private InvitationFormDao invitationFormDao;
	@Override
	protected BaseManagerDao<InvitationForm> getDomainDao() {
		return invitationFormDao;
	}

	public InvitationForm getByCode(String code){
		return invitationFormDao.findByinvitationCode(code);
	}

	/**
	 * 分页查询
	 * @param pageable
	 * @param _code
	 * @param _companyName
	 * @param _formType
	 * @param _isSure
	 * @return
	 */
	public Page<InvitationForm> pageParamQuery(PageRequest pageable, final String _code, final String _companyName, final Integer _formType, final Integer _isSure,
			final String _districtCode, final Integer _hotel, final Integer _specialDiet, final Integer _nightTourOfQiRiver, final Integer _visitFormerResidenceOfSunYatSen, final Integer _shopping, final Integer _entertainment
			){

		Page<InvitationForm> paginate =
				invitationFormDao.findAll(new Specification<InvitationForm>(){
					@Override
					public Predicate toPredicate(Root<InvitationForm> root, CriteriaQuery<?> query, CriteriaBuilder cb){
						List<Predicate> predicateList = new ArrayList<Predicate>();

						/**
						 * 邀请码
						 */
						if(!StringUtil.isNullAndBlank(_code)){
							Path<String> ex_like_str = root.get("invitationCode").get("code");
							predicateList.add(cb.equal(ex_like_str, _code));
						}

						/**
						 * 公司名
						 */
						if(!StringUtil.isNullAndBlank(_companyName)){
							Path<String> ex_like_str = root.get("companyName");
							predicateList.add(cb.like(cb.lower(ex_like_str), "%" + _companyName.toLowerCase() + "%"));
						}

						/**
						 * 表单版本 11=英文 21=中文
						 */
						if(_formType!=null){
							Path<Integer> ex_eq_int = root.get("formType");
							predicateList.add(cb.equal(ex_eq_int, _formType));
						}

						/**
						 * 是否已确认，后台操作，即确认后用户不能修改 0=未确认 1=已确认
						 */
						if(_isSure!=null){
							Path<Integer> ex_eq_int = root.get("isSure");
							predicateList.add(cb.equal(ex_eq_int, _isSure));
						}
						
						/**
						 * 国家或地区
						 */
						if(_districtCode!=null){
							Path<String> ex_eq_str = root.get("districtCode");
							predicateList.add(cb.equal(ex_eq_str, _districtCode));
						}
						/**
						 * 住宿
						 */
						if(_hotel!=null){
							if(_hotel==-1){
								Path<Integer> ex_eq_int = root.get("hotel");
								predicateList.add(cb.isNull(ex_eq_int));	
							}else{
								Path<Integer> ex_eq_int = root.get("hotel");
								predicateList.add(cb.equal(ex_eq_int, _hotel));	
							}
						}
						/**
						 * 特殊餐饮
						 */
						if(_specialDiet!=null){
							if(_specialDiet==1){
								Path<String> ex_eq_str = root.get("specialDiet");								
								predicateList.add(cb.gt(cb.length(ex_eq_str),0));	
							}else{
								Path<String> ex_eq_str = root.get("specialDiet");
								predicateList.add(cb.equal(ex_eq_str,""));
							}
						}
						/**
						 * 岐江夜游
						 */
						if(_nightTourOfQiRiver!=null){
							if(_nightTourOfQiRiver==1){
								Path<Integer> ex_eq_int = root.get("nightTourOfQiRiver");								
								predicateList.add(cb.equal(ex_eq_int,1));	
							}else{
								Path<Integer> ex_eq_int = root.get("nightTourOfQiRiver");	
								predicateList.add(cb.notEqual(ex_eq_int,1));	
							}
						}
						
						/**
						 * 故居参观
						 */
						if(_visitFormerResidenceOfSunYatSen!=null){
							if(_visitFormerResidenceOfSunYatSen==1){
								Path<Integer> ex_eq_int = root.get("visitFormerResidenceOfSunYatSen");								
								predicateList.add(cb.equal(ex_eq_int,1));	
							}else{
								Path<Integer> ex_eq_int = root.get("visitFormerResidenceOfSunYatSen");	
								predicateList.add(cb.notEqual(ex_eq_int,1));	
							}
						}
						/**
						 * 旅游购物
						 */
						if(_shopping!=null){
							if(_shopping==1){
								Path<Integer> ex_eq_int = root.get("shopping");								
								predicateList.add(cb.equal(ex_eq_int,1));	
							}else{
								Path<Integer> ex_eq_int = root.get("shopping");	
								predicateList.add(cb.notEqual(ex_eq_int,1));	
							}
						}
						/**
						 * 娱乐活动
						 */
						if(_entertainment!=null){
							if(_entertainment==1){
								Path<Integer> ex_eq_int = root.get("entertainment");								
								predicateList.add(cb.equal(ex_eq_int,1));	
							}else{
								Path<Integer> ex_eq_int = root.get("entertainment");	
								predicateList.add(cb.notEqual(ex_eq_int,1));	
							}
						}
						
							
							
						Predicate[] predicates = new Predicate[predicateList.size()];
						predicateList.toArray(predicates);
						if(predicates.length > 0){
							return cb.and(predicates);
						}else{
							return cb.conjunction();
						}
					}
				}, pageable);
		return paginate;
	}


	/**
	 * 导出excel
	 * @param _invitationForm
	 * @param os
	 * @throws Exception
	 */
	public void exportXls(List<InvitationForm> _invitationForm, OutputStream os) throws Exception{
		//生成excel文件
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		// sheet名称
		WritableSheet wsheet = wwb.createSheet("合作伙伴参会表单信息", 0);
		//设置列宽
		int a=0;

		wsheet.setColumnView(a++, 10);//邀请码
		wsheet.setColumnView(a++, 15);//国家或地区
		wsheet.setColumnView(a++, 15);//邀请码类型
		wsheet.setColumnView(a++, 15);//表单确认状态
		wsheet.setColumnView(a++, 10);//语言版本
		wsheet.setColumnView(a++, 30);//公司名称
		wsheet.setColumnView(a++, 15);//预计到达时间
		wsheet.setColumnView(a++, 15);//是否需要接机
		wsheet.setColumnView(a++, 15);//接机航班号
		wsheet.setColumnView(a++, 15);//参会人1名字
		wsheet.setColumnView(a++, 15);//参会人1性别
		wsheet.setColumnView(a++, 15);//参会人1职位
		wsheet.setColumnView(a++, 15);//参会人1手机号
		wsheet.setColumnView(a++, 25);//参会人1Email
		wsheet.setColumnView(a++, 15);//参会人2名字
		wsheet.setColumnView(a++, 15);//参会人2性别
		wsheet.setColumnView(a++, 15);//参会人2职位
		wsheet.setColumnView(a++, 15);//参会人2手机号
		wsheet.setColumnView(a++, 25);//参会人2Email
		wsheet.setColumnView(a++, 15);//参会人3名字
		wsheet.setColumnView(a++, 15);//参会人3性别
		wsheet.setColumnView(a++, 15);//参会人3职位
		wsheet.setColumnView(a++, 15);//参会人3手机号
		wsheet.setColumnView(a++, 25);//参会人3Email
		wsheet.setColumnView(a++, 15);//住宿
		wsheet.setColumnView(a++, 15);//餐饮
		wsheet.setColumnView(a++, 20);//餐饮特别要求
		wsheet.setColumnView(a++, 25);//岐江夜游(10月20晚)
		wsheet.setColumnView(a++, 25);//孙中山故居(10月20~22下午)
		wsheet.setColumnView(a++, 25);//旅游购物
		wsheet.setColumnView(a++, 25);//娱乐活动(10月20、21日晚)
		wsheet.setColumnView(a++, 20);//建议
		//设置标题
		WritableCellFormat totalx2Format = JxlUtil.getWritableCellFormatTitleRed();//红字蓝底

		String[] titles = {
				"邀请码","国家或地区","邀请码类型",
				"表单确认状态","语言版本","公司名称","预计到达时间","是否需要接机","接机航班号",
				"参会人1名字","参会人1性别","参会人1职位","参会人1手机号","参会人1Email",
				"参会人2名字","参会人2性别","参会人2职位","参会人2手机号","参会人2Email",
				"参会人3名字","参会人3性别","参会人3职位","参会人3手机号","参会人3Email",
				"住宿","餐饮","餐饮特别要求",
				"岐江夜游(10月20晚)","孙中山故居(10月20~22下午)","旅游购物","娱乐活动(10月20、21日晚)",
				"建议"
				};
		for(int i=0;i<titles.length;i++){
			wsheet.addCell(new Label(i,0,titles[i],totalx2Format));
        }
		int x = 1,y = 0;
		for(InvitationForm invitationForm : _invitationForm){
			y=0;
			//邀请码
			wsheet.addCell(new Label(y++, x, invitationForm.getInvitationCode().getCode(), JxlUtil.getWritableCellFormatTitleWrite2()));
			//国家或地区
			wsheet.addCell(new Label(y++, x, mcodeService.findByMtypeAndMvalue("COUNTRY_CHINESE", invitationForm.getDistrictCode()).getMkey(), JxlUtil.getWritableCellFormatTitleWrite2()));
			//邀请码类型
			wsheet.addCell(new Label(y++, x, invitationForm.getInvitationCode().getTypeChinese(), JxlUtil.getWritableCellFormatTitleWrite2()));
			//表单确认状态
			wsheet.addCell(new Label(y++, x, invitationForm.getIsSureChinese(), JxlUtil.getWritableCellFormatTitleWrite2()));
			//语言版本
			wsheet.addCell(new Label(y++, x, invitationForm.getFormTypeChinese(), JxlUtil.getWritableCellFormatTitleWrite2()));
			//公司名称
			wsheet.addCell(new Label(y++, x, invitationForm.getCompanyName(), JxlUtil.getWritableCellFormatTitleWrite2()));
			//预计到达时间
			wsheet.addCell(new Label(y++, x, invitationForm.getArriveDateStr(), JxlUtil.getWritableCellFormatTitleWrite2()));
			//是否需要接机
			wsheet.addCell(new Label(y++, x, invitationForm.getPickUpOrNot()==1?"是":"否", JxlUtil.getWritableCellFormatTitleWrite2()));
			//接机航班号
			wsheet.addCell(new Label(y++, x, invitationForm.getFlightNo(), JxlUtil.getWritableCellFormatTitleWrite2()));

			int y2 = y;
			y+=15;
			for(InvitationFormApplicant  invitationFormApplicant : invitationForm.getInvitationFormApplicantList()){
				String applicantName = invitationForm.getFormType()==21?invitationFormApplicant.getName():(invitationFormApplicant.getFirstName()+" "+invitationFormApplicant.getLastName());
				//参会人名字
				wsheet.addCell(new Label(y2++, x, applicantName, JxlUtil.getWritableCellFormatTitleWrite2()));
				//参会人性别
				wsheet.addCell(new Label(y2++, x, invitationFormApplicant.getGenderChinese(), JxlUtil.getWritableCellFormatTitleWrite2()));
				//参会人职位
				wsheet.addCell(new Label(y2++, x, invitationFormApplicant.getPosition(), JxlUtil.getWritableCellFormatTitleWrite2()));
				//参会人手机号
				wsheet.addCell(new Label(y2++, x, invitationFormApplicant.getMobileNumber(), JxlUtil.getWritableCellFormatTitleWrite2()));
				//参会人Email
				wsheet.addCell(new Label(y2++, x, invitationFormApplicant.getEmail(), JxlUtil.getWritableCellFormatTitleWrite2()));
			}

			//住宿
			wsheet.addCell(new Label(y++, x, invitationForm.getHotelChinese(), JxlUtil.getWritableCellFormatTitleWrite2()));
			//餐饮
			wsheet.addCell(new Label(y++, x, invitationForm.getDinnerTypeChinese(), JxlUtil.getWritableCellFormatTitleWrite2()));
			//餐饮特别要求
			wsheet.addCell(new Label(y++, x, invitationForm.getSpecialDiet(), JxlUtil.getWritableCellFormatTitleWrite2()));
			//岐江夜游(10月20晚)
			wsheet.addCell(new Label(y++, x, invitationForm.getNightTourOfQiRiver()==1?"参加":"", JxlUtil.getWritableCellFormatTitleWrite2()));
			//孙中山故居(10月20~22下午)
			wsheet.addCell(new Label(y++, x, invitationForm.getVisitFormerResidenceOfSunYatSen()==1?"参加":"", JxlUtil.getWritableCellFormatTitleWrite2()));
			//旅游购物
			wsheet.addCell(new Label(y++, x, invitationForm.getShopping()==1?"参加":"", JxlUtil.getWritableCellFormatTitleWrite2()));
			//娱乐活动(10月20、21日晚)
			wsheet.addCell(new Label(y++, x, invitationForm.getEntertainment()==1?"参加":"", JxlUtil.getWritableCellFormatTitleWrite2()));
			//建议
			wsheet.addCell(new Label(y++, x, invitationForm.getMessage(), JxlUtil.getWritableCellFormatTitleWrite2()));
			x++;
		}
		// 主体内容生成结束
		wwb.write();
		wwb.close();
		os.close();
	}

}
