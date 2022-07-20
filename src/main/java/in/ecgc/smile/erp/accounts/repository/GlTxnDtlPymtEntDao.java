package in.ecgc.smile.erp.accounts.repository;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.GlTxnDtlPymtEnt;

public interface GlTxnDtlPymtEntDao{

	public Integer addGlTxnDtlPymtEntData(GlTxnDtlPymtEnt  glTxnDtlPymtEnt);

	public List<GlTxnDtlPymtEnt> getGlTxnDtlPymtEntByPaymentDtl(Integer paymentNo, String logicalLoc, String sectionCd);


	//	public List<GlTxnDtlPymtEnt> getGlTxnDtlPymtEntList()throws Exception;

	//	public Integer updateGlTxnDtlPymtEntData(GlTxnDtlPymtEnt  glTxnDtlPymtEnt);
}
