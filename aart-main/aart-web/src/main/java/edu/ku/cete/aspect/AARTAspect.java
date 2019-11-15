package edu.ku.cete.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ku.cete.domain.audit.AuditableDomain;

@Aspect
public class AARTAspect {

	private Logger logger = LoggerFactory.getLogger(AARTAspect.class);
	
	@Before("execution(* edu.ku.cete.service.impl.*.add*(..)) || execution(* edu.ku.cete.service.impl.*.insert*(..)) || " +
			"execution(* edu.ku.cete.service.impl.*.cascadeAdd*(..)) || execution(* edu.ku.cete.service.impl.*.save*(..))")
	public void setAuditColumnsBeforeCreateTransaction(JoinPoint joinPoint){
		logger.debug("setAuditColumnsBeforeCreateTransaction Enter with Method Name :" + joinPoint.getSignature().getName());  
				 
		Object[] objs = joinPoint.getArgs();
		for(Object obj : objs){
			if (obj instanceof AuditableDomain){
				((AuditableDomain) obj).setAuditColumnProperties();
				logger.debug("setAuditColumnsBeforeCreateTransaction audit properties set.");  
			}
		} 
		
		logger.debug("setAuditColumnsBeforeCreateTransaction Exit");
	}
	
	@Before("execution(* edu.ku.cete.service.impl.*.update*(..))")
	public void setModifyAuditColumnsBeforeModifyTransaction(JoinPoint joinPoint){
		logger.debug("setModifyAuditColumnsBeforeModifyTransaction Enter with Method Name :" + joinPoint.getSignature().getName());  
				 
		Object[] objs = joinPoint.getArgs();
		for(Object obj : objs){
			if (obj instanceof AuditableDomain){
				((AuditableDomain) obj).setAuditColumnPropertiesForUpdate();
				logger.debug("setModifyAuditColumnsBeforeModifyTransaction audit properties set.");  
			}
		} 
		
		logger.debug("setModifyAuditColumnsBeforeModifyTransaction Exit");
	}
	
	/*@Before("execution(* edu.ku.cete.service.impl.*.delete*(..))")
	public void setModifyAuditColumnsBeforeSoftDeleteTransaction(JoinPoint joinPoint){
		logger.debug("setModifyAuditColumnsBeforeSoftDeleteTransaction Enter with Method Name :" + joinPoint.getSignature().getName());  
				 
		Object[] objs = joinPoint.getArgs();
		for(Object obj : objs){
			if (obj instanceof AuditableDomain){
				((AuditableDomain) obj).setAuditColumnPropertiesForDelete();
				logger.debug("setModifyAuditColumnsBeforeSoftDeleteTransaction audit properties set.");  
			}
		} 
		
		logger.debug("setModifyAuditColumnsBeforeSoftDeleteTransaction Exit");
	}*/
	
}
