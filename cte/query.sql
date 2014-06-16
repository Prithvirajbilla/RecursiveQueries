select * from A1 connect by prior c_key = 
	(select c_id from C1 where b_key = (select b_id from B1 where a_key = a_id))
	start with a_id = 1000000; 

------------
select * from A connect by prior c_key = 
	(select c_id from C where b_key = (select b_id from B where a_key = a_id))
	start with a_id = 10; 



select a_id from A where c_key in 
	(select c_id from C where b_key in 
		(select b_id from B where a_key = 1));

-------------

SELECT plan_table_output from table(dbms_xplan.display('plan_table',null,'typical'));


select * from A connect by prior 