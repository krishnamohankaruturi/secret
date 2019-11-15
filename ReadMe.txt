References
http://www.ioncannon.net/programming/975/spring-3-file-upload-example/
http://blog.springsource.com/2011/02/15/spring-3-1-m1-unified-property-management/

for properties.

http://forum.springsource.org/showthread.php?57371-Initializing-a-spring-bean-with-contents-of-a-property-file

http://www.displaytag.org/1.2/install.html

    commons-logging - yes.
    commons-lang - yes.
    commons-collections
    commons-beanutils
    log4j-yes
    itext (optional, for pdf/rtf export)
    
http://www.coderanch.com/t/532670/Struts/Getting-display-tag-struts
    
commons-collections-2.1.1.jar
commons-lang-2.1.jar
displaytag-1
displaytag-export-poi
displaytag-portlet 

------------------------
http://static.springsource.org/spring/docs/3.0.x/reference/html/beans.html#context-functionality-messagesource

# in exceptions.properties
argument.required=The '{0}' argument is required.

<beans>

  <!-- this MessageSource is being used in a web application -->
  <bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
      <property name="basename" value="test-messages"/>
  </bean>

  <!-- lets inject the above MessageSource into this POJO -->
  <bean id="example" class="com.foo.Example">
      <property name="messages" ref="messageSource"/>
  </bean>

</beans>

public class Example {

  private MessageSource messages;

  public void setMessages(MessageSource messages) {
      this.messages = messages;
  }

  public void execute() {
      String message = this.messages.getMessage("argument.required",
          new Object [] {"userDao"}, "Required", null);
      System.out.println(message);
  }

}

for generating schema from xml.

http://www.xmlforasp.net/codebank/system_xml_schema/buildschema/buildxmlschema.aspx


------------------------

1) Setting up ODBC

http://www.postgresql.org/ftp/odbc/versions/msi/

download 9.1 x86 for 64 bit

it does not seem to matter...

now using only 32 bit driver..


When you see things like "Illegal Exception" in the jsp there are more errors objects.So go to debug and look at the validator's errors objects.

TODO

1) Remove Enrollment Impl.Not needed.

EA ddl generation does not work for transformed models..Check with EA later.

2) Should i copy the validations on setter methods in UploadedRecord object to the EnrollmentImpl object ...?
 I think yes...so that the same validations get applied on the direct insert also.
 
3) add a column called validEnrollment.Done

4) Tie records to the right group...
 
 Replace with logger.
 
5) test subject should it go on assessment...? or should it be part of enrollment..?
 
 steps of uploading...
 
 remove mandatory validations on string..DONE
 
6) Enrollment is part of an uploaded record and NOT is a uploaded record.

 UploadedRecord * ---> 0,1  Enrollment ---> 1 Student. 

7) setDateStr and similar methods should be only in uploadedRecord. Partially Done.Rest do as you go.

8) unique condition on enrollment to be changed.

state student id, ayp school id,attendance id,current school year Done.

9) Up on rejecting the file state something like what records are valid so far..
 
 Common Mistakes..
 
 1)     Student getByStudentStateIdentifier(@Param("stateStudentIdentifier") long studentStateIdentifier);
 
 Selective binding.
 2) http://puretech.paawak.com/tag/spring-mvc/
 
3) look at the hash to see if it is null or an object.

4) if using an object and a string use the parameter type as map and do category.id etc..
 -----
 
 1) Read the header.Done
 2) Identify the first record. -N.A-
 3) if record type is not selected they should not be able to upload a file.
 4) Consider creating an uploadable interface.Done. Validateable interface.
 5) Reconsider the 2 private methods on controller.DONE
 6) Add formatRegex to field specification. Done
 7) set the flags doReject and inValid.Done
 8) Remove the processAArtLin unwanted code.DONE
 9) dump of field specification values.DONE
 10) Residence district identifier.done.
 11) should an enrollment be searched on ayp school identifier given that it ayp school id always does not have to be present.I think yes.Done
 12) What to do if an ayp school is not found put null and create the record or store the ayp school identifier in some sort ?
  i think we would prefer to store the aypschool identifier.DONE
 13) if the attendance school program identifier is not found, then the enrollment record will not be created. DONE verify.
 14) for the next record type.Ask the following questions, is the test record type related to anyother record types created so far ?
 15) roster many to many change with Neil.DONE
 16) get field spec only from inside the controller.DONE...
 17) Field spec invalid boolean field...more trouble than it is worth. It should be wholly independent of field value.
 18) null safe on scrs record getters.DONE
 19) Remove underscores from enrollmentsrosters table.done.
 20) TODO is course section name unique to a school ?..not really unique updated design doc.DONE.
 21) remove invalidFlag from FieldSpecification object.DONE
 22) transaction is atomic at the DAO level.Needs to be changed.
 23) indicate not found type of errors for scrs.DONE
 24) Correct null is null invalid message.done.
 25) Is there a way to not hard code the field names ? without having to iterate over the object ? the display is still driven from property file.
 Checked with cikai and DONE.
 26) Number of records created/updated ? DONE
 
 --
 1) All field names also from property file.
 2) Inject properties without application context.
 3) DDL changes on enrollment.DONE
 4) Remove unwanted fields from column spec.DONE
 5) Verify if each of the columns are getting updated. DONE
 6) Number of records created/updated. DONE
 7) add first language to student.DONE
 8) identifiers displayed with no errors next to it.
 9) drop constraints on student names in qa.DONE
 10) update field specification field length.DONE
 11) programmatic transaction management when dealing with records extending ValidateableRecord ? Join transaction.DONE
 12) if a file is not selected do not allow the users to upload the file.
 13) If a record type is not selected do not allow them to upload.DOING...
 14) Should upload controller be split for different record types ?
 15) setRecordTypes optimize sql calls.
 16) change that hashmap.<String,Category>
 17) upload specification change it to annotation.
 18) configure to look for properties in multiple places.
 19) remove set upload specification from controller.
 20) 		//TODO find assessment by joining with category.
 21) Remove underscores from Assessment.
 22) state subject area is same as test subject.
 23) remove other hard coded comparisons in upload controller.
 24) insert test subject and test type if needed before inserting assessment.do it in the xml itself.
 24) meta data for test type (and test subject that is same as state subject area)
 25) Send Jerod and Gretchen the excel sheet of error messages.
 26) Exception handling logic in web.xml. Get from cikai and create a page to route it.
 27) TODO Need to change from distinct. add limit.
 28) write the uploaded file to a temporary directory in server.
 -------------------------
 29) FOLLOW UP: How are the students for each roster displayed...? select the roster then make the server call and display all students with in that roster.
 
 
 ----------------
 
 XML beans
 
 1) Add xbean.jar,xmlbeans-2.5.0.jar under lib.
 
 2) Generate the schema file,
 
 http://www.xmlforasp.net/codebank/system_xml_schema/buildschema/buildxmlschema.aspx
 
 from the println of the xml.
 
 3) Add the build file. 

   <project default="generateXmlObjects" basedir=".">
     <target name="generateXmlObjects" description="Generate the files">
     	<taskdef name="xmlbean" classname="org.apache.xmlbeans.impl.tool.XMLBean" classpath="lib/xbean.jar" />
     	
     	<!--<xmlbean schema="XmlSchemas/KIDSSchema.xsd" download="true"/>-->

     	<path id="class.path">
     		<fileset dir="lib">
     			<include name="**/*.jar"/>
     		</fileset>
     	</path>     	
     	
     	<xmlbean classgendir="XmlSchemas/build" classpathref="class.path"
     	      failonerror="true">
     	  <fileset dir="XmlSchemas/src" excludes="**/*.xsd"/>
     	  <fileset dir="XmlSchemas/schemas" includes="**/*.*"/>
     	</xmlbean>
     	
     </target>
   </project>

4) generate it. Boom...ton of classes....not good.

------------------

1) url from table.<--done.
2) Web service permissions.
3) Web service provider limitations.
4) record common id and create date fields.<-- starting..
5) estimate and read this iteration's user stories.<-- later.
6) data check in <-- Done..
create kansas assessments..

7) from clean slate. <-- not started.
8) the build script for provider.<--done.
9) Student to assessment <--Done.


------------

http://cxf.apache.org/docs/wsdl-to-java.html

---

1. Move Cxf To AARTDependencies Project.Done
2. Move Open CSV to AARTDependecies project.
3. Pull other types of records...? add to enhancement
4. Remove the hard coded 33.
5. put the school year.
6. TODO as of now all input dates are of the same format.
change it to accept date formats if input dates can be of different format.
7. Test all dates.
8. Add a method in categoryDao to update categories and remove references to category DAO for updating.
9. Inform Jerod about pulling organizations through KIDS.DONE
10. Inform Jerod aabout upload changes due to organizational upload. DONE
11.         //TODO add the permission to the user object so that we need not do this every time. DONE
12. Web service really end of road ? DONE
13. Web service FERPA compliant steps. DONE follow up on invite.
14. Make the below explicit,
Users having access to two or more organizations can only access one organization at a time.
15. Check in the sqls.DONE
16. selected org id and user org id.DONE
17. Test case document for common security.DONE..
18. Remove _ from the new tables.DONE
19. Why is it returning a lot of rows..? the resource restriction.DONE
---
1. Contract org display id<-- really ?

2. So AART authorities does not extend Spring authority..bummer.
 
------
1. Make testing program id DONE.
2. Make test type disappear. DONE
3. Use assessment code instead of test type.
using test type as assessment code.DONE
4. Make test subject disappear.DONE
5. Add it to students assessments relation. 
first point it to category then...point it to content area
This can be done post FRI.DONE
6. Add assessment code .DONE.
7. Fix the case of inserting junk assessments.DONE
8. Create assessments pointing to a default unknown testing program.DONE
9. Remove test type from the properties.
This can be done post fri.DONE
10. Should test subject (not subject) be also removed from properties ?
This can be done post fri.DONE
11. Add the unique constraints by doing the a-pgdiff comparison.
DONE mostly.
12. Test a case for assessment insert.
DONE.
13. Test a case for Web service upload.DONE
13. In WS upload the assessment parsing mechanism.
..DOING
15. Do the testing session page related changes.
Partly Neil.Partly me.DONE
16. Check the search tests feature.DONE
17. Execute the ddl.DONE
18. insert the unknown testing program and unknown assessment program.DONE
19. Make change on category for origination code.DONE
20. Insert the CPASS testing programs in localhost.DONE
21. Test all uploads.DONE
22. contentAreaService.findByNameAndOriginationCode(name, null); <---should only AART specific content areas be used ..?
why not set orignation code as AART.?
23. View/EditRosters any other search pages.
24. State course same as content area ?
25. Make the assessment data changes sql and check it in.DONE

26. Add similar messaging changes for test and enrollment upload.

27. Remove the grades.
28. remove the test types
29. Remove the test subjects.
30 drop the subject in assessment.
31. change students assessments.
32. Current grade level it says exited.TODO
33. Compare schemas.
34. If multiple assessments found reject the record.
35. fix the synced issue
36. Grade course and content area unique constraints ?
37. The user who can upload..web service.
----

Post FRI,

Get back to Jerod for Restriction and testing.

stupid spaces in aliases wassted 1 hr.

-----------
If extending it takes a lot of time to serailize in to objects.
1. User Organization tree rename to contracting organizationtree.

------------
--For mavenizing AART.

1. Remove timer out of the application.
2. remove springbyexample jar. remove this dependency.
3. Remove property configuration from mvc.xml.

tomcat run time if the classpath 

1. Why does my batis put a crappy equals method ..? should it not use only the id of the table ?
2. copy the read me's from the other envs.
3. hasStudentRecord and hasUserRecord..study some behavioral patterns.

4. do tomcat clean of work directory. if facing exceptions like unable to find spring jars.
Not able to load Aspect etc. .. are the exceptions
5. over loaded setters are not recognized by bean resolvers in jsp and
restful pojos

for java mail client the application was using a different version of geronimo that was ignoring the
secure connection parameters.

After changing the maven dependency that started working.

Exceptions are not getting printed in the logs..especially duplicate key and constraint violation exceptions.
After adding a dependency jar, it started working as well.


http://stackoverflow.com/questions/10292103/some-error-while-running-spring-mvc-project
http://maven.40175.n5.nabble.com/Unable-to-locate-local-repository-in-Mac-OSX-td65490.html


2013-01-03 11:20:45,525 ERROR main org.springframework.web.context.ContextLoader - Context initialization failed
org.springframework.beans.factory.BeanDefinitionStoreException: Unexpected exception parsing XML document from ServletContext resource [/WEB-INF/applicationContext.xml]; nested exception is java.lang.IllegalStateException: Unable to load schema mappings from location [META-INF/spring.schemas]
	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.doLoadBeanDefinitions(XmlBeanDefinitionReader.java:412)
	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:334)
	at org.springframework.beans.factory.xml.XmlBeanDefinitionReader.loadBeanDefinitions(XmlBeanDefinitionReader.java:302)
	at org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(AbstractBeanDefinitionReader.java:143)
	at org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(AbstractBeanDefinitionReader.java:178)
	at org.springframework.beans.factory.support.AbstractBeanDefinitionReader.loadBeanDefinitions(AbstractBeanDefinitionReader.java:149)
	at org.springframework.web.context.support.XmlWebApplicationContext.loadBeanDefinitions(XmlWebApplicationContext.java:124)
	at org.springframework.web.context.support.XmlWebApplicationContext.loadBeanDefinitions(XmlWebApplicationContext.java:93)
	at org.springframework.context.support.AbstractRefreshableApplicationContext.refreshBeanFactory(AbstractRefreshableApplicationContext.java:130)
	at org.springframework.context.support.AbstractApplicationContext.obtainFreshBeanFactory(AbstractApplicationContext.java:467)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:397)
	at org.springframework.web.context.ContextLoader.createWebApplicationContext(ContextLoader.java:276)
	at org.springframework.web.context.ContextLoader.initWebApplicationContext(ContextLoader.java:197)
	at org.springframework.web.context.ContextLoaderListener.contextInitialized(ContextLoaderListener.java:47)
	at org.apache.catalina.core.StandardContext.listenerStart(StandardContext.java:4206)
	at org.apache.catalina.core.StandardContext.start(StandardContext.java:4705)
	at org.apache.catalina.core.ContainerBase.start(ContainerBase.java:1057)
	at org.apache.catalina.core.StandardHost.start(StandardHost.java:840)
	at org.apache.catalina.core.ContainerBase.start(ContainerBase.java:1057)
	at org.apache.catalina.core.StandardEngine.start(StandardEngine.java:463)
	at org.apache.catalina.core.StandardService.start(StandardService.java:525)
	at org.apache.catalina.core.StandardServer.start(StandardServer.java:754)
	at org.apache.catalina.startup.Catalina.start(Catalina.java:595)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at org.apache.catalina.startup.Bootstrap.start(Bootstrap.java:289)
	at org.apache.catalina.startup.Bootstrap.main(Bootstrap.java:414)
Caused by: java.lang.IllegalStateException: Unable to load schema mappings from location [META-INF/spring.schemas]
	at org.springframework.beans.factory.xml.PluggableSchemaResolver.getSchemaMappings(PluggableSchemaResolver.java:153)
	at org.springframework.beans.factory.xml.PluggableSchemaResolver.resolveEntity(PluggableSchemaResolver.java:110)
	at org.springframework.beans.factory.xml.DelegatingEntityResolver.resolveEntity(DelegatingEntityResolver.java:85)


Jars corrupt. Deleted the jars did a maven clean and install fixed the issue.

Integration.

http://integration1.qa.cete.us/AartService-1.0/test/valid/123..

---
Jmeter
make sure it is encoding..
kuload+2 is pased as kuload
