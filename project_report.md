# PROJECT Report

Author: Donna J. Harris (994042890) & Valter J. De Araujo Junior (195826730)

Date: Dec 20, 2022 

Check [readme.txt](readme.txt) for course work statement and self-evaluation. 
  

## R1 Requirements (project)

### R1 Write a project proposal (2-3 pages).

 
Complete? Yes

1. what you have done, 
The proposed Inventory Management Analytics Dashboard (IMAD) application presents
a high-level, no-input view of inventory for the desired scope, store, region, or entire
organization. IMAD presents details that will detect trends, issues, and opportunities.

2. what are the new features. 
It handles transactional and analytical data and the dashboard uses modern UI which will consumes data using REST.

3. Take some screen to demonstrate the features if applicable. 


### R2 Design data format, collect data, create dataset for the application.

 
Complete? Yes

![Model: EJB and JPA objects](images/R2-1_model_objects.png){width=90%}

![Model: Transactional and Analytical tables](images/R2-2_T_and_A_tables.png){width=70%}

![Model: Load initial data at Transactional tables at starting up](images/R2-3_Load_At_Startup_T_Tables.png){width=90%}

If Yes, briefly describe: 

1. what you have done, 
2. what are the new features. 
3. Take some screen to demonstrate the features if applicable. 


### R3 Develop and implement data application algorithms for the proposed application problem.

 
Complete? Yes

![Model: EJB contains the business logic to transform transactional to analytical data](images/R3-1_EJB_data_application_algorithms.png){width=90%}

If Yes, briefly describe: 

1. what you have done, 
2. what are the new features. 
3. Take some screen to demonstrate the features if applicable. 


### R4 Do data computing to generate models, representing models in portable format and stored in file or database. More credit is given if distributed approach is used data mining of big dataset.

 
Complete? Yes

![Model: representing models in portable format](images/R4-1_representing_models_portable_format_from_T_to_A_table.png){width=90%}

If Yes, briefly describe: 

1. what you have done, 
2. what are the new features. 
3. Take some screen to demonstrate the features if applicable. 


### R5 Create deployable service components using application models using Java based enterprise computing technologies, to create client program to do remote call of the data mining services.

 
Complete? Yes

![JEE: All software layers](images/R5-1_All_software_layers.png){width=90%}

![JEE: Maven to package all deployable artifacts](images/R5-2_create_deployable_service_components.png){width=90%}

If Yes, briefly describe: 

1. what you have done, 
2. what are the new features. 
3. Take some screen to demonstrate the features if applicable. 


### R6 Deploy service components onto enterprise application servers.

 
Complete? Yes

![Maven: Package and deploy log](images/R6-1_deploy_service_components_at_jboss_wildfly.png){width=90%}

![JBoss Wildfly: Deploy log](images/R6-2_deployed_imad-ear_at_jboss_wildfly.png){width=90%}

![JBoss Wildfly: Console](images/R6-3_wildfly_console.png){width=90%}

If Yes, briefly describe: 

1. what you have done, 
2. what are the new features. 
3. Take some screen to demonstrate the features if applicable. 


### R7 Create web services (SOAP, RESTful) to use the data service components.

 
Complete? Yes

![RESTful: Service Implementation and testing](images/R7-1_create_RESTful_web_services.png){width=90%}

If Yes, briefly describe: 

1. what you have done, 
2. what are the new features. 
3. Take some screen to demonstrate the features if applicable. 


### R8 Create web user interface/mobile applications to use the application/web services.

 
Complete? Yes

![SAPUI5: Using npm to build the UI app](images/R8-1_Build_UI5_App.png){width=90%}

![IMAD Dashboard rendering in an extra large desktop monitor](images/R8-2_xl_desktop_web_ui.png){width=90%}

![IMAD Dashboard rendering in a large desktop monitor](images/R8-3_l_desktop_web_ui.png){width=90%}

![IMAD Dashboard rendering in an iPad portrait](images/R8-4_ipad_web_ui.png){width=90%}

![IMAD Dashboard rendering in an iPad landscape](images/R8-5_ipad_web_ui.png){width=90%}

![IMAD Dashboard rendering in an iPhone portrait](images/R8-6_iphone_web_ui.png){width=90%}

![IMAD Dashboard rendering in an iPhone landscape](images/R8-7_iphone_web_ui.png){width=90%}

If Yes, briefly describe: 

1. what you have done, 
2. what are the new features. 
3. Take some screen to demonstrate the features if applicable. 


### R9 Test your services, log your services, and document your term project.

 
Complete? Yes

![Unit tests using Postman](images/R9-1_unit_tests.png){width=90%} 

![Unit tests using Postman in detail](images/R9-2_unit_tests_detail.png){width=90%} 

![Example of method documentation](images/R9-3_method_documentation.png){width=90%} 

![Development plan used by the team](images/R9-4_development_plan.png){width=90%} 

If Yes, briefly describe: 

1. what you have done, 
2. what are the new features. 
3. Take some screen to demonstrate the features if applicable. 


### R10 Demonstrate your term project in final project presentation, slides, short video.

**DELETE LATER** Video script for Donna:
1-shows the IMAD dashboard showing the A tables data (maybe browser should be at 90%)
2-minimize the browser, it shows Github CP630 project home on right side. Shows the dev plan on the left side (or not...it is up to you. If you decide on it, shows git hub as full screen.)
3-shows VS code, move over the modules: ejb, rs, web, etc. (Jboss is already up) Run mvn clean package wildfly:deploy. shows the MVN success log. shows the Jboss success deploy and the singleton running and loadind data.
4-Shows this project report on browser and mover over on process cards link and the ui link.
5-Shows the Dashboard in desktop mode, another browser in ipad mode.

Summary:
1-imad dashboard
2-Github (and Dev Plan)
3-VS Code (packaging and deploy)
4-Dashboard in Desktop, Tablet and Phone. Landscape is already better than portrait for phones.

Should we show the MySQL tables?****

Please let me know any questions and we can discuss the details.
Also, please add or remove anything you want to.
 
Complete? (Yes/No) 

If Yes, briefly describe: 

1. what you have done, 
2. what are the new features. 
3. Take some screen to demonstrate the features if applicable. 





**References**

1. CP630OC project
2. https://news.sap.com/2022/10/sap-nhl-develop-nhl-venue-metrics/
3. Assignment 3
