package in.ecgc.smile.erp.accounts.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import in.ecgc.smile.erp.accounts.exception.RecordNotFoundException;
import in.ecgc.smile.erp.accounts.model.CalendarRequestModel;
import in.ecgc.smile.erp.accounts.model.EntityGL;
import in.ecgc.smile.erp.accounts.model.TrialBalance;
import in.ecgc.smile.erp.accounts.util.CalendarRequestQueries;
import in.ecgc.smile.erp.accounts.util.TrialBalanceSqlQueries;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public class TrialBalanceDaoImpl implements TrialBalanceDao {
	
	@Autowired
	EntityGLMasterDao entityGLMasterDao;

	private NamedParameterJdbcOperations namedParameterJdbcOperations;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public TrialBalanceDaoImpl(DataSource dataSource) {
		namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public TrialBalance getByLogicalLoc(@NotBlank String logicalloc, @NotBlank String balancedate, @NotBlank String mainglcd, @NotBlank String subglcd,@NotBlank String glname,@NotBlank String gltype) {
		log.info("inside TrialBalanceDaoImpl  -  getByLogicalLoc ");
		TrialBalance trBalance = new TrialBalance();
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("maingl_cd", mainglcd);
		paraMap.put("subgl_cd", subglcd);
		paraMap.put("logicalloc", logicalloc);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate lcdt = LocalDate.parse(balancedate, formatter);
		paraMap.put("balancedt", lcdt);

		try {
			trBalance = namedParameterJdbcOperations.queryForObject(TrialBalanceSqlQueries.GET_TRIAL_BAL_BY_LOGLOC, paraMap,
					new RowMapper<TrialBalance>() {

						@Override
						public TrialBalance mapRow(ResultSet rs, int rowNum) throws SQLException {
							TrialBalance balance = new TrialBalance();
							if(rs.getBigDecimal("dr_amount")!=null)
							{
								balance.setDr_amt(rs.getBigDecimal("dr_amount").setScale(2, BigDecimal.ROUND_HALF_UP));
							}
							else
							{
								
								balance.setDr_amt(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
							if(rs.getBigDecimal("cr_amount")!=null)
							{
								balance.setCr_amt(rs.getBigDecimal("cr_amount").setScale(2, BigDecimal.ROUND_HALF_UP));
							}
							else
							{
								balance.setCr_amt(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
							balance.setMainGlCd(mainglcd);
							balance.setSubGlCd(subglcd);
							balance.setGlName(glname);
							balance.setGltype(gltype);
							return balance;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			trBalance.setDr_amt(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
			trBalance.setCr_amt(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
			trBalance.setMainGlCd(mainglcd);
			trBalance.setSubGlCd(subglcd);
			trBalance.setGlName(glname);
			trBalance.setGltype(gltype);
			
		}
		return trBalance;
	}
	
	@Override
	public TrialBalance getForAllLocation( @NotBlank String balancedate, @NotBlank String mainglcd, @NotBlank String subglcd,@NotBlank String glname,@NotBlank String gltype) {
		log.info("inside TrialBalanceDaoImpl  -  getForAllLocation ");
		TrialBalance trBalance = new TrialBalance();
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("maingl_cd", mainglcd);
		paraMap.put("subgl_cd", subglcd);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate lcdt = LocalDate.parse(balancedate, formatter);
		paraMap.put("balancedt", lcdt);

		try {
			trBalance = namedParameterJdbcOperations.queryForObject(TrialBalanceSqlQueries.GET_TRIAL_BAL_FOR_ALL_LOGLOC, paraMap,
					new RowMapper<TrialBalance>() {

						@Override
						public TrialBalance mapRow(ResultSet rs, int rowNum) throws SQLException {
							TrialBalance balance = new TrialBalance();
							if(rs.getBigDecimal("dr_amount")!=null)
							{
								balance.setDr_amt(rs.getBigDecimal("dr_amount").setScale(2, BigDecimal.ROUND_HALF_UP));
							}
							else
							{
								balance.setDr_amt(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
							if(rs.getBigDecimal("cr_amount")!=null)
							{
								balance.setCr_amt(rs.getBigDecimal("cr_amount").setScale(2, BigDecimal.ROUND_HALF_UP));
							}
							else
							{
								balance.setCr_amt(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
							balance.setMainGlCd(mainglcd);
							balance.setSubGlCd(subglcd);
							balance.setGlName(glname);
							balance.setGltype(gltype);
							return balance;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			trBalance.setDr_amt(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
			trBalance.setCr_amt(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
			trBalance.setMainGlCd(mainglcd);
			trBalance.setSubGlCd(subglcd);
			trBalance.setGlName(glname);
			trBalance.setGltype(gltype);
			
		}
		return trBalance;
	}
	
	
}
