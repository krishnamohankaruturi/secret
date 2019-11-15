

--US12925: Professional Development: Enroll in a module

create table usermodule
(
                id bigserial NOT NULL,                    
                userid bigint NOT NULL,
                moduleid bigint NOT NULL,         
                enrollmentstatusid bigint NOT NULL,
                createddate timestamp with time zone NOT NULL DEFAULT now(),
                modifieddate timestamp with time zone NOT NULL DEFAULT now(),
                createduser bigint,
                modifieduser bigint,
                activeflag boolean DEFAULT true,
                CONSTRAINT pk_usermodule PRIMARY KEY (id),
                CONSTRAINT usermodule_user_userid_fk FOREIGN KEY (userid)
                                REFERENCES aartuser (id) MATCH SIMPLE
                                ON UPDATE NO ACTION ON DELETE NO ACTION,
                CONSTRAINT usermodule_module_moduleid_fk FOREIGN KEY (moduleid)
                                REFERENCES module (id) MATCH SIMPLE
                                ON UPDATE NO ACTION ON DELETE NO ACTION,
                CONSTRAINT usermodule_category_enrollmentstatusid_fk FOREIGN KEY (enrollmentstatusid)
                                REFERENCES category (id) MATCH SIMPLE
                                ON UPDATE NO ACTION ON DELETE NO ACTION,
                CONSTRAINT usermodule_userid_moduleid_uk UNIQUE (userid, moduleid),
                CONSTRAINT module_createduser_fk FOREIGN KEY (createduser)
                                REFERENCES aartuser (id) MATCH SIMPLE
                                ON UPDATE NO ACTION ON DELETE NO ACTION,
                CONSTRAINT module_modifieduser_fk FOREIGN KEY (modifieduser)
                                REFERENCES aartuser (id) MATCH SIMPLE
                                ON UPDATE NO ACTION ON DELETE NO ACTION               
);

