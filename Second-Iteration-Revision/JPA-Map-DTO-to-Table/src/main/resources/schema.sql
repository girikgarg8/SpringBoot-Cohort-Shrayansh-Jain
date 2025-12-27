-- Drop and recreate sequence with custom increment and max value
DROP SEQUENCE IF EXISTS employee_seq;
CREATE SEQUENCE employee_seq 
INCREMENT BY 25 
START WITH 100 
MAXVALUE 9999;

