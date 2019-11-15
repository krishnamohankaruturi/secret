CREATE OR REPLACE VIEW educators_view AS 
 SELECT g.id AS groupid, g.organizationid, g.groupname, 
    g.activeflag AS groupactive, a.id, a.username, a.firstname, a.middlename, 
    a.surname, a.password, a.email, a.uniquecommonidentifier, 
    a.defaultusergroupsid, a.ukey, a.createddate, a.createduser, a.activeflag, 
    a.modifieddate, a.modifieduser, a.displayname, 
    a.lastexpiredpasswordresetdate, a.ksinactivateduser, a.systemindicator, 
    a.sourcetype, o.organizationname, ot.typename, ua.assessmentprogramid
   FROM aartuser a
   LEFT JOIN usersorganizations uo ON a.id = uo.aartuserid
   LEFT JOIN userorganizationsgroups ug ON uo.id = ug.userorganizationid
   LEFT JOIN organization o ON o.id = uo.organizationid
   LEFT JOIN organizationtype ot ON ot.id = o.organizationtypeid
   LEFT JOIN groups g ON g.id = ug.groupid
   left join userassessmentprogram ua on ua.userorganizationsgroupsid = ug.id;

CREATE OR REPLACE VIEW duplicateidentifiers_view AS 
 SELECT aartuser.id, aartuser.username, aartuser.firstname, aartuser.middlename, 
    aartuser.surname, aartuser.password, aartuser.email, 
    aartuser.uniquecommonidentifier, aartuser.defaultusergroupsid, 
    aartuser.ukey, aartuser.createddate, aartuser.createduser, 
    aartuser.activeflag, aartuser.modifieddate, aartuser.modifieduser, 
    aartuser.displayname, aartuser.lastexpiredpasswordresetdate, 
    aartuser.ksinactivateduser, aartuser.systemindicator, aartuser.sourcetype
   FROM aartuser
  WHERE (aartuser.uniquecommonidentifier::text IN ( SELECT aartuser.uniquecommonidentifier
           FROM aartuser
          GROUP BY aartuser.uniquecommonidentifier
         HAVING count(*) > 1)) AND aartuser.uniquecommonidentifier IS NOT NULL AND aartuser.uniquecommonidentifier::text <> ''::text
  ORDER BY aartuser.uniquecommonidentifier;
