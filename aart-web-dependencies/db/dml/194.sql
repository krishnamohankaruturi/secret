--dml to update statecoursecode to actual code received from STCO
update roster r set statecoursecode=(select ca.name from contentarea ca where ca.id=r.statecourseid);