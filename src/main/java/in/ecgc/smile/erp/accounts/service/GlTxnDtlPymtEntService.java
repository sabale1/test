package in.ecgc.smile.erp.accounts.service;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.GlTxnDtlPymtEnt;
import in.ecgc.smile.erp.accounts.model.Payments;

public interface GlTxnDtlPymtEntService{

	public Boolean addGlTxnDtlPymtEntData(Payments payments);

	public List<GlTxnDtlPymtEnt> getGlTxnDtlPymtEntByPaymentDtl(Integer paymentNo, String logicalLoc, String sectionCd);


//	public List<GlTxnDtlPymtEnt> getGlTxnDtlPymtEntList();
//
//	public Integer updateGlTxnDtlPymtEntData(GlTxnDtlPymtEnt  glTxnDtlPymtEnt);
//

}
