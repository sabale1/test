package in.ecgc.smile.erp.accounts.repository;

import java.util.List;

import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.SubBifurcations;


public interface SubBifurcationMasterDao {

	List<SubBifurcations> getSubBifurcations();
	List<String> getSubBifurcationLevels();
	SubBifurcations getSubBifurcationsByLevel(String subBifurcationLevel);
	Integer addSubBifurcation(SubBifurcations subBifurcations);
	SubBifurcations updateSubBifurcationLevel(SubBifurcations currentSubBifurcations);

}
