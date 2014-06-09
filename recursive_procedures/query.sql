CREATE OR REPLACE PROCEDURE find_all_a(p_a_id NUMBER,p_tier NUMBER DEFAULT 1 ) IS
v_a_id NUMBER,v_a_name VARCHAR(20),v_a_details VARCHAR(20);

CURSOR c1 IS
SELECT a_id,a_name,a_details FROM A WHERE c_key in 
	(SELECT c_id FROM C WHERE b_key in 
		(SELECT b_id from B where a_key = p_a_id));

BEGIN

SELECT a_id INTO v_a_id,a_name INTO v_a_name,a_details INTO v_a_details FROM
where a_id = v_a_id;

IF p_tier = 1 THEN
	dbms_output.put_line(TO_CHAR(v_a_id) || v_a_name 
		|| v_a_details || ' is at the top level')
END IF;

FOR ee IN c1 LOOP

	dbms_output.put_line(TO_CHAR(v_a_id) || v_a_name 	
			|| v_a_details || ' on tier ' || TO_CHAR(p_tier));

find_all_a(ee.a_id,p_tier+1);
END LOOP;
END;