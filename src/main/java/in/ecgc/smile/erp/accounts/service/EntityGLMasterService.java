package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.FTR;
import in.ecgc.smile.erp.accounts.model.GlTxnDtl;
import in.ecgc.smile.erp.accounts.model.GlTxnHdr;
import in.ecgc.smile.erp.accounts.model.SubBifurcationValue;

/**
 * GL master create operation Service Interface
 * 
 * @version 1.0 22-April-2020
 * @author Sanjali Kesarkar
 */
public interface EntityGLMasterService {

	Integer addGLCode(EntityGL entityGL);
	
	List<EntityGL> listAllGLCodes();
	
	EntityGL findGLByGLCode(String mainGLCode, String subGLCode);

//	String findSubBifurcations(String mainGLCode, String subGLCode);

	EntityGL updateGLCode(EntityGL currentEntityGL, EntityGL updatedEntityGL);
	
	Integer disableGLCode(EntityGL entityGL);
	
	Integer activateGLCode(EntityGL entityGL);
	
	Integer getRegularGLTypesByOpeningDay();
	
	Integer getConfiguredGLTypesByOpeningDay();

	List<EntityGL> getsubGLCodebyMainGLCode(String mainGLCode);

	List<EntityGL> getAllGlByIsGlGroup();
	List<EntityGL> getGstGlCodes(String branchGstin , String customerGstin);
	public List<GlTxnDtl> getAllGlTxn(String mainGLCode, String subGLCode);

	
}
