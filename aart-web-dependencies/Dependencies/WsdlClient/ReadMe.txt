1. Always get the wsdl from 

https://svvayp15511.ksde.org/kids_data/kids_webservice.asmx?WSDL

2. Compare it with the checked in version.

3. Note that authentication header has to be sent with the envelope.

4. If the wsdl has changed run the cxfWSDLToJava ant task and that will regenerate
the stubs and move it to the web-inf/lib

5. If the wsdl has changed then likely the response string has also changed.

6. Download the cxf dependencies from http://code.cete.us/svn/dlm/aart/AARTDependencies

7. update the corresponding enry in build.properties.